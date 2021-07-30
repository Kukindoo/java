package codeJava.server;

import codeJava.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Locale;

public class ClientHandler implements Runnable{
    private final SocketAddress ip;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean semValidRequest = false, semValidHeader = true, semProtocolReceived;


    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
        ip = clientSocket.getLocalSocketAddress();
        sendMessageToClient("[SERVER]: Hello, welcome to polite messaging server!  What can I do for you?", true);
    }
    @Override
    public void run() {
        try {
            while (true){
                String request = in.readLine();
                System.out.println("[" + clientSocket.getLocalSocketAddress() + "]: Received " + request.toUpperCase() + " request!");

                semValidRequest = requestValidation(request);
                if (semValidRequest){
                    processRequesta(request);
                    semValidRequest = false;
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("[" + ip + "] Connection terminated!");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    private void processRequesta(String request) throws IOException {
        String[] splitRequest = request.split(" ");
        if (semProtocolReceived || splitRequest[0].equalsIgnoreCase("PROTOCOL?")) {
            switch (splitRequest[0].toUpperCase()) {
                case "GET?":
                    processRequestGet(splitRequest[1], splitRequest[2]);
                    break;
                case "PROTOCOL?":
                    processRequestProtocol(splitRequest[1], splitRequest[2]);
                    break;
                case "TIME?":
                    processRequestTime();
                    break;
                case "BYE!":
                    processRequestBye();
                    break;
                case "LIST?":
                    processRequestList(splitRequest[1], splitRequest[2]);
            }
        } else {
            sendMessageToClient("Please, could you possibly send protocol first?");
        }

        myFlush();
    }

    private void processRequestList(String time, String headers) throws IOException {
        LinkedList<String> searchCriteria = new LinkedList<>();
        searchCriteria.add("");
        if(Integer.parseInt(headers) < 0){
            sendMessageToClient("Second argument in LIST? must be positive number!", true);
        } else {
            if (Long.parseLong(time) > Instant.now().getEpochSecond()){
                sendMessageToClient("You requested time in future", true);
            } else {
                //sendMessageToClient("I will wait for your headers input!", true);

                searchCriteria = acceptHeaders(Integer.parseInt(headers));
                searchCriteria.add("TIME-SENT: " + time);
                if(searchCriteria != null) {
                    //searchCriteria.add("TIME-SENT: " + time);
                    searchMessagesWithCriteria(searchCriteria);
                }
            }
        }
    }

    private void searchMessagesWithCriteria(LinkedList<String> searchCriteria) {
        LinkedList<Message> messagesTobeSent = new LinkedList<>();
        for(Message message : Server.MESSAGES){
            boolean passFlag = true;
            if (searchCriteria.size() > 0)
            for (String criterion : searchCriteria) {
                String[] splitCriterion = criterion.split(":", 2);
                passFlag = doCriterionCheck(message,splitCriterion[0],splitCriterion[1].trim());
                if (!passFlag) break;
            }
            if (passFlag){
                messagesTobeSent.add(message);
            }
        }
        prepareListSend(messagesTobeSent);
        myFlush();
    }

    private void prepareListSend(LinkedList<Message> messagesTobeSent) {
        sendMessageToClient("MESSAGE " + messagesTobeSent.size());
        for (Message message : messagesTobeSent){
            sendMessageToClient(message.getMessageID());
        }
    }

    private boolean doCriterionCheck(Message message, String type, String tobeCompared) {
        if (type.equalsIgnoreCase("TOPIC"))     return message.getTopic().equalsIgnoreCase(tobeCompared.toUpperCase());
        if (type.equalsIgnoreCase("SUBJECT"))   return message.getSubject().equalsIgnoreCase(tobeCompared);
        if (type.equalsIgnoreCase("TIME-SENT")) return message.getTime() >= Long.parseLong( tobeCompared );
        if (type.equalsIgnoreCase("TO"))        return  message.getSentTo().equalsIgnoreCase(tobeCompared.toUpperCase());
        if (type.equalsIgnoreCase("FROM"))      return message.getReceivedFrom().equalsIgnoreCase(tobeCompared.toUpperCase());
        if (type.equalsIgnoreCase("CONTENTS"))  return message.getContents() == Integer.parseInt(tobeCompared);
        return false;
    }

    private LinkedList<String> acceptHeaders(int headersNo) throws IOException {
        System.out.println("Number of headers in acceptHeders: " + headersNo);
        LinkedList<String> searchCriteria = new LinkedList<>();
        if(headersNo != 0) myFlush();

        for (int i = 0; i < headersNo; i++ ) {
            String header = in.readLine();
            String[] splitHeader= header.split(":", 2);
            System.out.println("[" + clientSocket.getLocalSocketAddress() + "]: Sent " + header.toUpperCase() + " header!");
            semValidHeader = headersValidation(splitHeader[0]);
            System.out.println();
            if (semValidHeader) {
                if(i != headersNo-1) myFlush();
                searchCriteria.add(header);
            } else {
                if(i != headersNo-1) myFlush();
            }
        }
        return searchCriteria;
    }

    private boolean headersValidation(String header) {
        for(String word : Server.VALID_HEADERS){
            if (word.equalsIgnoreCase(header)){
                return true;
            }
        }
        //sendMessageToClient("Header "+ header + " is not valid in this protocol version and has been ignored!" );
        return false;
    }


    private void processRequestBye() throws IOException {
        sendMessageToClient("BYE", true);
        clientSocket.close();
    }

    private void processRequestTime() {
        sendMessageToClient("NOW " + Instant.now().getEpochSecond());
    }

    private void processRequestProtocol(String version, String identifier) {
        semProtocolReceived = true;
        sendMessageToClient("PROTOCOL? " + version + " accepted");
    }

    private void processRequestGet(String hashFunction, String hash) {
        Message message = findMessage(hash);

        if (message != null){
            prepareMessage(message);
        }
    }

    private void prepareMessage(Message message) {
        sendMessageToClient("Mesage-id: SHA-256 " + message.getMessageID());
        sendMessageToClient("Time-sent: " + message.getTime());
        sendMessageToClient("From: " + message.getReceivedFrom());
        if (!message.getSentTo().equals(""))    sendMessageToClient("To: "+message.getSentTo());
        if (!message.getTopic().equals(""))     sendMessageToClient("Topic: " + message.getTopic());
        if (!message.getSubject().equals(""))   sendMessageToClient("Subject: " + message.getSubject());
        sendMessageToClient("Contents: " + message.getContents());
        for (String line : message.getBody()){
            sendMessageToClient(line);
        }
    }

    private Message findMessage(String hash) {
        for (Message message : Server.MESSAGES){
            if(message.getMessageID().equals(hash) ){
                sendMessageToClient("FOUND");
                return message;
            }
        }
        sendMessageToClient("SORRY");
        return null;
    }

    private boolean requestValidation(String request) {
        for (Token token : Server.VALID_REQUESTS_OBJ) {
            String[] splitRequest = request.split(" ");
            if (splitRequest[0].toUpperCase().equals(token.getName())){
                if((splitRequest.length - 1) == token.getArguments()) {
                    //sendMessageToClient("Request " + request.toUpperCase() + " accepted!");
                     return true;
                } else {
                    sendMessageToClient("Request " + request.toUpperCase() + " is valid, but accept different number of arguments! Type HELP to see valid requests!", true);
                    return false;
                }
            }
        }
        sendMessageToClient("Request " + request.toUpperCase() + " is not valid! Type HELP to see valid requests!", true);
        return false;
    }



    private void sendMessageToClient(String message){
            out.println(message);

    }
    private void sendMessageToClient(String message, Boolean send){
        out.println(message);
        if(send) {
            sendMessageToClient("EOM");
            out.flush();
        }
    }

    private void myFlush(){
        out.println("EOM");
        out.flush();
    }
}
