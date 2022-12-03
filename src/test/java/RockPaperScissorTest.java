import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RockPaperScissorTest {

    private static Stream<Arguments> shapeScoreValues() {
        return Stream.of(
                Arguments.of(Shape.ROCK, 1),
                Arguments.of(Shape.PAPER, 2),
                Arguments.of(Shape.SCISSOR, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("shapeScoreValues")
    void getScoreForRock(Shape shape, int expectedScore) {
        assertThat(shape.getScore()).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> outcomeValues() {
        return Stream.of(
                Arguments.of(Shape.ROCK, Shape.SCISSOR, Outcome.WIN),
                Arguments.of(Shape.ROCK, Shape.ROCK, Outcome.DRAW),
                Arguments.of(Shape.ROCK, Shape.PAPER, Outcome.LOSE),
                Arguments.of(Shape.PAPER, Shape.ROCK, Outcome.WIN),
                Arguments.of(Shape.PAPER, Shape.PAPER, Outcome.DRAW),
                Arguments.of(Shape.PAPER, Shape.SCISSOR, Outcome.LOSE),
                Arguments.of(Shape.SCISSOR, Shape.PAPER, Outcome.WIN),
                Arguments.of(Shape.SCISSOR, Shape.SCISSOR, Outcome.DRAW),
                Arguments.of(Shape.SCISSOR, Shape.ROCK, Outcome.LOSE)
        );
    }

    @ParameterizedTest
    @MethodSource("outcomeValues")
    void getOutcome(Shape shape1, Shape shape2, Outcome expectedOutcome) {
        Outcome outcome = shape1.getOutcome(shape2);

        assertThat(outcome).isEqualByComparingTo(expectedOutcome);
    }

    private static Stream<Arguments> outcomeScoreValues() {
        return Stream.of(
                Arguments.of(Outcome.WIN, 6),
                Arguments.of(Outcome.DRAW, 3),
                Arguments.of(Outcome.LOSE, 0)
        );
    }



    @ParameterizedTest
    @MethodSource("outcomeScoreValues")
    void getOutcomeScore(Outcome outcome, int expectedScore) {
        assertThat(outcome.getScore()).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> matchScoreValues() {
        return Stream.of(
                Arguments.of(Shape.ROCK, Shape.SCISSOR, 6+1),
                Arguments.of(Shape.ROCK, Shape.ROCK, 3+1),
                Arguments.of(Shape.ROCK, Shape.PAPER, 0+1),
                Arguments.of(Shape.PAPER, Shape.ROCK, 6+2),
                Arguments.of(Shape.PAPER, Shape.PAPER, 3+2),
                Arguments.of(Shape.PAPER, Shape.SCISSOR, 0+2),
                Arguments.of(Shape.SCISSOR, Shape.PAPER, 6+3),
                Arguments.of(Shape.SCISSOR, Shape.SCISSOR, 3+3),
                Arguments.of(Shape.SCISSOR, Shape.ROCK, 0+3)
        );
    }

    @ParameterizedTest
    @MethodSource("matchScoreValues")
    void getMatchScore(Shape shape1, Shape shape2, int expectedMatchScore) {
        assertThat(shape1.getMatchScore(shape2)).isEqualTo(expectedMatchScore);
    }

    private static Stream<Arguments> myShapeValues() {
        return Stream.of(
                Arguments.of(Shape.ROCK, Outcome.WIN, Shape.PAPER),
                Arguments.of(Shape.ROCK, Outcome.DRAW, Shape.ROCK),
                Arguments.of(Shape.ROCK, Outcome.LOSE, Shape.SCISSOR),
                Arguments.of(Shape.PAPER, Outcome.WIN, Shape.SCISSOR),
                Arguments.of(Shape.PAPER, Outcome.DRAW, Shape.PAPER),
                Arguments.of(Shape.PAPER, Outcome.LOSE, Shape.ROCK),
                Arguments.of(Shape.SCISSOR, Outcome.WIN, Shape.ROCK),
                Arguments.of(Shape.SCISSOR, Outcome.DRAW, Shape.SCISSOR),
                Arguments.of(Shape.SCISSOR, Outcome.LOSE, Shape.PAPER)
        );
    }

    @ParameterizedTest
    @MethodSource("myShapeValues")
    void getMyShapeForRockWin(Shape opponent, Outcome outcome, Shape expectedMyShape) {
        Shape myShape = opponent.getMyShape(outcome);

        assertThat(myShape).isEqualByComparingTo(expectedMyShape);
    }

    @Test
    void todo() {

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/test/resources/adventOfCode2Input.txt"));
            String line = reader.readLine();
            int totalScore = 0;
            while (line != null) {
                String[] split = line.split(" ");
                String opponent = split[0];
                String outcomeString = split[1];
                Shape opponentShape = getOpponentShape(opponent);
                Outcome outcome = getOutcomeFromString(outcomeString);
                Shape myShape = opponentShape.getMyShape(outcome);
                totalScore += myShape.getMatchScore(opponentShape);
                // read next line
                line = reader.readLine();
            }
            assertThat(totalScore).isEqualTo(2);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Outcome getOutcomeFromString(String outcome) {
        if (outcome.equals("X")) {
            return Outcome.LOSE;
        } else if (outcome.equals("Y")) {
            return Outcome.DRAW;
        } else {
            return Outcome.WIN;
        }
    }

    private Shape getMyShape(String me) {
        if (me.equals("X")) {
            return Shape.ROCK;
        } else if (me.equals("Y")) {
            return Shape.PAPER;
        } else {
            return Shape.SCISSOR;
        }
    }

    private Shape getOpponentShape(String opponent) {
        if (opponent.equals("A")) {
            return Shape.ROCK;
        } else if (opponent.equals("B")) {
            return Shape.PAPER;
        } else {
            return Shape.SCISSOR;
        }
    }


}
