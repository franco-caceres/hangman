package pe.fcg.kth.id1212.hw1.hangman.server.net;

import pe.fcg.kth.id1212.hw1.hangman.server.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    private final Controller controller = new Controller();

    public static void main(String[] args) {
        if(args.length == 1) {
            Server server = new Server();
            server.start(Integer.parseInt(args[0]));
        } else {
            System.out.println("Port argument missing.");
        }
    }

    private void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port + "...");
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[" + new Date().toString() + "] New client connection.");
                startNewClientSession(clientSocket);
            }
        } catch(IOException e) {
            System.err.println("Failure to establish server socket (port=" + port + ").");
        }
    }

    private void startNewClientSession(Socket clientSocket) {
        ClientEndpoint clientEndpoint = new ClientEndpoint(clientSocket, controller);
        Thread thread = new Thread(clientEndpoint);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
}
