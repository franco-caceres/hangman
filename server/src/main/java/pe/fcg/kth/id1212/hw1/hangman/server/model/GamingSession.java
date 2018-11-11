package pe.fcg.kth.id1212.hw1.hangman.server.model;

public class GamingSession {
    private int score = 0;
    private Game currentGame;
    private RandomWordProvider randomWordProvider;

    public GamingSession(RandomWordProvider randomWordProvider) {
        this.randomWordProvider = randomWordProvider;
    }

    public Result startNewGame() {
        if(currentGame != null && currentGame.getStatus() != Game.Status.WIN && currentGame.getStatus() != Game.Status.LOSS) {
            throw new IllegalStateException("Failure to start a new game because the current one is still in process.");
        }
        currentGame = new Game();
        Result result = currentGame.start(randomWordProvider.get());
        result.setScore(score);
        return result;
    }

    public Result makeGuess(Guess guess) {
        Result result = currentGame.makeGuess(guess);
        if(result.getStatus() == Game.Status.WIN) {
            score++;
        } else if(result.getStatus() == Game.Status.LOSS) {
            score--;
        }
        result.setScore(score);
        return result;
    }
}
