import controller.GameController;
import model.GameBoard;
import model.Pile;

public class main {

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GameController gameController = new GameController(gameBoard);
    }

}
