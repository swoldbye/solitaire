import controller.GameController;
import model.GameBoard;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class mainTest {

    GameBoard gameBoard;
    GameController gameController;

    @Test
    void main() {
        boolean won;

        gameBoard = new GameBoard();
        gameController = new GameController(gameBoard);
        won = gameController.play();
        assertTrue(won);

    }
}