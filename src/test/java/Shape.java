import java.util.Arrays;

public enum Shape {
    ROCK(1), PAPER(2), SCISSOR(3);

    private final int score;

    Shape(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Outcome getOutcome(Shape otherShape) {
        if (otherShape == this) {
            return Outcome.DRAW;
        } else if (isLoseAgainst(otherShape)) {
            return Outcome.LOSE;
        }
        return Outcome.WIN;
    }

    private boolean isLoseAgainst(Shape otherShape) {
        return (this == ROCK && otherShape == PAPER) || (this == PAPER && otherShape == SCISSOR) || (this == SCISSOR && otherShape == ROCK);
    }

    public int getMatchScore(Shape otherShape) {
        return getScore() + getOutcome(otherShape).getScore();
    }

    public Shape getMyShape(Outcome outcome) {
        return Arrays.stream(Shape.values())
                .filter(shape -> shape.getOutcome(this) == outcome)
                .findFirst()
                .orElse(null);
    }
}
