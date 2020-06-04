import controller.GameController;
import model.GameBoard;

public class main {

    public static void main(String[] args) {
        boolean won;


            GameBoard gameBoard = new GameBoard();
            GameController gameController = new GameController(gameBoard);
            won = gameController.play();
            System.out.println(won);

        System.exit(0);
    }

}
