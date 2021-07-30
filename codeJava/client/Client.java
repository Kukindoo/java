package codeJava.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final int PORT = 20111;
    private Socket socket;
    private static ExecutorService pool = Executors.newFixedThreadPool(3);
    private boolean semWaitForRequestCompletion = false;

    public static void main(String[] args) throws IOException {
        Client client1 = new Client();
        client1.run("localHost");
    }
    private void run(String IP) throws IOException {
        socket = new Socket(IP,PORT);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        receiver();
        while (true){
            System.out.print("> ");
            String entry = keyboard.readLine();
            if( entry.equalsIgnoreCase("QUIT")){
                break;
            }
            sender(entry);
            receiver();
        }
        closeSocket();
    }

    private void closeSocket() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    private String receiver() throws IOException {
        String str;
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        str = bf.readLine();
        while (!str.equals("EOM")) {
            System.out.println(str);
            if(str.equals("BYE")) {
                socket.close();
                System.exit(55);
            }
            str = bf.readLine();
        }
        return str;
    }
    private String sender(String entry) throws IOException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println(entry);
        pr.flush();
        return "";
    }
}
