import controller.GameController;
import model.GameBoard;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class mainTest {

    GameBoard gameBoard;
    GameController gameController;

   /* @Test
     void main() {

             boolean won;
             gameBoard = new GameBoard();
             gameController = new GameController(gameBoard);
             won = gameController.play();
         assertTrue(won);


     }/*/
    @Test
    void main() {
        int number = 10000;
        int counter = 0;
        for (int i = 0; i < number; i++) {
            boolean won;
            gameBoard = new GameBoard();
            gameController = new GameController(gameBoard);
            won = gameController.play();


            if (won) {
                counter++;
                //      if (counter % 5 == 0) System.out.println(counter);
            }
        }

        System.out.println("Wins: " + counter);
        DecimalFormat percRounded = new DecimalFormat("#.##");
        String perc = percRounded.format((double)counter/(double)number * 100);
        System.out.println("Percentage: " + perc + "%");
        assertTrue(counter > (number * 0.3));


    }
}