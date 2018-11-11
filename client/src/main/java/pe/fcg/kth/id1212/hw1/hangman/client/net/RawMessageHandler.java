package pe.fcg.kth.id1212.hw1.hangman.client.net;

public interface RawMessageHandler {
    void handleIncoming(String rawMessage);
    void handleLostConnection();
}
