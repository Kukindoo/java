//https://stackoverflow.com/questions/50733861/implementing-a-simple-java-tcp-server
//https://www.youtube.com/watch?v=-xKgxqG411c
//https://www.youtube.com/watch?v=BWjGQlIkgT4
package codeJava.server;


import codeJava.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 20111;
    protected static final String[] VALID_REQUESTS = new String[] {"PROTOCOL? 2", "TIME? 0", "LIST? 2", "GET? 2", "BYE! 0"};
    protected static final LinkedList<Token> VALID_REQUESTS_OBJ = new LinkedList<>();
    protected static final String[] VALID_HEADERS = new String[] {"TOPIC", "SUBJECT", "TO", "TIME-SENT", "FROM", "CONTENTS"};
    protected static final LinkedList<Message> MESSAGES = new LinkedList<>();
    private final ServerSocket incomingSocket;

    private ArrayList<ClientHandler> connectedClients = new ArrayList<>();
    private final ExecutorService treadPool = Executors.newFixedThreadPool(254);

    public Server() throws IOException, NoSuchAlgorithmException {
        incomingSocket = new ServerSocket(PORT);
        setServerTokens();
        createMessages();
    }

    private void createMessages() throws NoSuchAlgorithmException {
        System.out.println("Creating messages: ");
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            MESSAGES.add(message.generateMessage());
            System.out.println(message.getMessageID() + " " + message.getTime() + "\tFrom: " + message.getReceivedFrom() + "\t\t\tTo: " + message.getSentTo() + "\t\tSubject: " + message.getSubject());
        }
        LinkedList<String> s = new LinkedList<>();
        s.add("Hello everyone!");
        s.add("This is the first message sent using PM.");
        MESSAGES.add(new Message("bc18ecb5316e029af586fdec9fd533f413b16652bafe079b23e021a6d8ed69aa",
                1614686400,
                "martin.brain@city.ac.uk",
                2,
                s,
                "Hello!",
                "#announcements"
                ));
    }

    private void setServerTokens() {
        for (String word: VALID_REQUESTS) {
            String splitWord[] = word.split(" ");
            Token myToken = new Token(splitWord[0],Integer.parseInt(splitWord[1]) );
            VALID_REQUESTS_OBJ.add(myToken);
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Server server = new Server();
        server.run();
    }

    private void run() throws IOException {
        System.out.println("Starting server service on port: " + PORT );
        while (true) {
            Socket socket = listen();
            System.out.println("[SERVER] Client connected from " + socket.getLocalSocketAddress());
            ClientHandler client = new ClientHandler(socket);
            connectedClients.add(client);
            treadPool.execute(client);
        }
    }

    private Socket listen() throws IOException {
        System.out.println("[SERVER] Waiting for more connections...\n ");
        return incomingSocket.accept();
    }

//    private String processRequest(String request){
//        if (request.equals("welcome")){
//            return "[SERVER]: Hello, welcome to polite messaging server!  What can I do for you?\n";
//        }
//            return "FALSE";
//    }

//    private String receiver(Socket socket) throws IOException {
//        InputStreamReader in = new InputStreamReader(socket.getInputStream());
//        BufferedReader bf = new BufferedReader(in);
//        String str = bf.readLine();
//        System.out.println("["+socket.getLocalSocketAddress() +"]: Requesting "+str.toUpperCase());
//        return "";
//    }
//    private String sender(Socket socket, String entry) throws IOException {
//        PrintWriter pr = new PrintWriter(socket.getOutputStream());
//        pr.println(processRequest(entry));
//        pr.flush();
//        return "";
//    }
}
