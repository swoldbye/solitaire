import controller.GameController;
import model.GameBoard;

public class main {

    public static void main(String[] args) {
        boolean won;

        for (int i = 0; i < 2; i++) {
            if (i == 1){
                System.out.println("TEST");
            }
            GameBoard gameBoard = new GameBoard();
            GameController gameController = new GameController(gameBoard);
            won = gameController.play();
            System.out.println(won);
        }
        System.exit(0);
    }

}
