package pe.fcg.kth.id1212.hw1.hangman.server.model;

public class Guess {
    private String content;

    public Guess(String content) {
        this.content = content;
    }

    String getContent() {
        return content;
    }
}
