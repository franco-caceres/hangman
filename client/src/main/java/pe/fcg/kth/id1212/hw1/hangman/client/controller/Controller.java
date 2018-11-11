package pe.fcg.kth.id1212.hw1.hangman.client.controller;

import pe.fcg.kth.id1212.hw1.hangman.client.net.RawMessageHandler;
import pe.fcg.kth.id1212.hw1.hangman.client.net.ServerEndpoint;
import pe.fcg.kth.id1212.hw1.hangman.common.controller.Message;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Controller {
    private final ServerEndpoint serverEndpoint = new ServerEndpoint();

    public void connect(String host, int port, RawMessageHandler rawMessageHandler) throws IOException {
        serverEndpoint.connect(host, port, rawMessageHandler);
    }

    public void disconnect() throws IOException {
        serverEndpoint.disconnect();
    }

    public void makeGuess(String guess) {
        Message message = new Message(Message.Type.GUESS, guess);
        sendMessage(message);
    }

    public void startNewGame() {
        Message message = new Message(Message.Type.STARTNEW);
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        CompletableFuture.runAsync(() -> {
            try {
                serverEndpoint.sendMessage(message.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
