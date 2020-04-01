package controller;

import model.Card;
import model.GameBoard;
import model.Row;

import java.util.ArrayList;

/**
 * Controller that decides what to move next, based on the ChessandPoker.com solitare Stategy guide
 * This class does NOT move any cards, but tells the GameController Which cards to move.
 */

public class MoveController {

    private ArrayList<Row> faceDownList = new ArrayList<Row>();

    private GameBoard gameBoard;

    private GameController gameController;

    private int aceCounter = 0;

    public MoveController(GameBoard gameBoard, GameController gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
    }

    /**
     * Main make move method that calls other methods in this class in the order we want to check
     */

    public void makeMove() {
        if (aceCounter < 4) {
            int foundAce = aceCounter;
            checkForAce();
            if(aceCounter > foundAce){
                return;
            }
        }
        checkForMoveCard();


    }

    /**
     * First check to see if there are any aces face up
     */

    private void checkForAce() {
        for (Row r : gameBoard.getRowList()) {
            if (r.getTop().getLevel() == 1) {
                gameController.moveToStack(r.getTop(), r);
                aceCounter++;
                break;
            }
        }
    }

    /**
     * Second, check to see if there can be freed a downcard, if there are multiple possibilites, move the card
     * that frees the row with most downcards
     */
    public void checkForMoveCard() {
        faceDownList = gameController.getFaceDownList();
        for (Row r : faceDownList) {
            Card c = r.getCardList().get(r.getCardList().size() - (r.getCardList().size() - r.getFaceDownCards()));
            for (Row r2 : faceDownList) {
                if (!r2.getTop().getColour().equals(c.getColour()) && r2.getTop().getLevel() == c.getLevel() + 1) {
                    gameController.moveCardRowToRow(r, r2);
                    return;
                }
            }
        }
    }
}
