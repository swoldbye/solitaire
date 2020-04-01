import controller.GameControllerTemp;
import model.GameBoard;

public class main {

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GameControllerTemp gameControllerTemp = new GameControllerTemp(gameBoard);
    }

}
