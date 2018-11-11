package pe.fcg.kth.id1212.hw1.hangman.server.net;

import pe.fcg.kth.id1212.hw1.hangman.common.controller.Message;
import pe.fcg.kth.id1212.hw1.hangman.common.net.NetUtils;
import pe.fcg.kth.id1212.hw1.hangman.server.controller.Controller;
import pe.fcg.kth.id1212.hw1.hangman.server.model.GamingSession;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ClientEndpoint implements Runnable {
    private Socket clientSocket;
    private Controller controller;
    private GamingSession playSession;
    private boolean connected;

    ClientEndpoint(Socket clientSocket, Controller controller) {
        this.clientSocket = clientSocket;
        this.controller = controller;
        this.playSession = controller.startNewGamingSession();
        connected = true;
    }

    private Message startNewGame() {
        return controller.startNewGame(playSession);
    }

    @Override
    public void run() {
        try {
            sendMessage(startNewGame());
            while(connected) {
                Message incoming = receiveMessage();
                switch(incoming.getType()) {
                    case GUESS:
                        sendMessage(controller.makeGuess(playSession, incoming));
                        break;
                    case STARTNEW:
                        sendMessage(startNewGame());
                        break;
                    case CONNECTIONLOST:
                        disconnect();
                        break;
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void disconnect() throws IOException {
        System.out.println("[" + new Date().toString() + "] Client disconnected.");
        connected = false;
        clientSocket.close();
    }

    private void sendMessage(Message message) throws IOException {
        NetUtils.sendMessage(clientSocket.getOutputStream(), message.toString());
    }

    private Message receiveMessage() throws IOException {
        String raw = NetUtils.receiveMessage(clientSocket.getInputStream());
        return new Message(raw);
    }
}
