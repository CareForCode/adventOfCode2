public enum Outcome {
    WIN(6),
    DRAW(3),
    LOSE(0);

    private final int score;

    Outcome(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
