package controller;

import model.Card;
import model.GameBoard;
import model.Row;

import java.util.ArrayList;

/**
 * Controller that decides what to move next, based on the ChessandPoker.com solitare Stategy guide
 * This class does NOT move any cards, but tells the GameController Which cards to move.
 * <p>
 * This logic in this is class is what mainly can be used for the final system as we wont need a controller to move
 * cards, that we will do ourselves :)
 * - Alex
 */

public class MoveController {

    private ArrayList<Row> faceDownList = new ArrayList<Row>();

    private GameBoard gameBoard;

    private GameController gameController;

    private boolean isKingAvailiable = false, gameWon = false;

    private int kingCounter = 0, usedPileCardCounter = 0;

    public MoveController(GameBoard gameBoard, GameController gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
    }

    /**
     * Main make move method that calls other methods in this class in the order we want to check
     */

    public void makeMove() {
        faceDownList = gameController.getFaceDownList();

        checkForAceDeuce();
        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }

        checkForAvailableKing();

        if (isKingAvailiable && kingCounter < 4) {
            moveKingToEmpty();
        }

        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }

        checkForMoveCard();

        if (!gameBoard.getCardPileRow().getCardList().isEmpty()) {
            useFlipCard();
        }

        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }


        gameController.flipCardPile();

        usedPileCardCounter++;

        checkForWin();

        if (gameWon && gameBoard.getCardPileRow().getCardList().isEmpty()) {
            gameController.victoryFormation();
        }


        if (usedPileCardCounter > gameBoard.getPile().getPileList().size() + 2) {
            gameController.gameLost();
        }


    }

    /**
     * First check to see if there are any aces face up
     */

    private void checkForAceDeuce() {
        for (Row r : faceDownList) {
            if (r.getTop().getLevel() == 1) {
                gameController.moveToStack(r.getTop(), r);
                return;
            }
        }
        for (Row r : faceDownList) {
            if (r.getTop().getLevel() == 2) {
                gameController.moveToStack(r.getTop(), r);
                return;
            }
        }
    }

    /**
     * Second, check to see if there can be freed a downcard, if there are multiple possibilites, move the card
     * that frees the row with most downcards
     */
    public void checkForMoveCard() {

        for (Row r : faceDownList) {
            if (r.getCardList().isEmpty()) {
                continue;
            }
            Card c = r.getCardList().get(r.getCardList().size() - (r.getCardList().size() - r.getFaceDownCards()));
            //If card to be moved will leave empty stack, it is only moved if there is a king ready to take it
            if (r.getFaceDownCards() == 0 && !isKingAvailiable && r.getRowLocation() != 0) {
                continue;
            } else {
                for (Row r2 : faceDownList) {
                    if (!r2.getTop().getColour().equals(c.getColour()) && r2.getTop().getLevel() == c.getLevel() + 1) {
                        gameController.moveCardRowToRow(r, r2);
                        return;
                    }
                }
            }
        }
    }

    /**
     * check face up cards if there is a king. This effects the decisions made.
     */
    private void checkForAvailableKing() {
        if (kingCounter == 4) {
            isKingAvailiable = true;
            return;
        }
        for (Row r : gameBoard.getRowList()) {
            for (Card c : r.getCardList()) {
                if (c.isFaceUp() && c.getLevel() == 13 && r.getFaceDownCards() > 0) {
                    isKingAvailiable = true;
                    return;
                }
            }
        }
        if (!gameBoard.getCardPileRow().getCardList().isEmpty()) {
            if (gameBoard.getCardPileRow().getTop().getLevel() == 13) {
                isKingAvailiable = true;
                return;
            }
        }
        isKingAvailiable = false;
        return;
    }

    private void moveKingToEmpty() {
        if(gameBoard.getCardPileRow().getTop().getLevel() == 13){
            return;
        }
        for (Row r : gameBoard.getRowList()) {
            if (r.getTop().getLevel() == 0) {
                for (Row r2 : gameBoard.getRowList()) {
                    if (r2 != r) {
                        Card c = r2.getCardList().get(r2.getCardList().size() - (r2.getCardList().size() - r2.getFaceDownCards()));
                        if (c.getLevel() == 13 && r2.getFaceDownCards() > 0) {
                            gameController.moveCardRowToRow(r2, r);
                            kingCounter++;
                            return;
                        }
                    }
                }
            }
        }
    }

    public void useFlipCard() {
        Card c = gameBoard.getCardPileRow().getTop();
        switch (c.getLevel()) {
            case 1:
                gameController.moveToStack(c, gameBoard.getCardPileRow());
                break;
            case 2:
                gameController.moveToStack(c, gameBoard.getCardPileRow());
                if (!gameController.isMoveMade()) {
                    for (Row r : gameBoard.getRowList()) {
                        if (!r.getTop().getColour().equals(c.getColour()) && r.getTop().getLevel() == c.getLevel() + 1) {
                            gameController.moveCardRowToRow(gameBoard.getCardPileRow(), r);
                            break;
                        }
                    }
                }
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                for (Row r : gameBoard.getRowList()) {
                    if (!r.getTop().getColour().equals(c.getColour()) && r.getTop().getLevel() == c.getLevel() + 1) {
                        gameController.moveCardRowToRow(gameBoard.getCardPileRow(), r);
                    }
                }
                break;
            case 13:
                for (Row r : gameBoard.getRowList()) {
                    if (r.getTop().getLevel() == 0) {
                        gameController.moveCardRowToRow(gameBoard.getCardPileRow(), r);
                        break;
                    }
                }
        }
    }

    public void checkForWin() {
        for (Row r : gameBoard.getRowList()) {
            if (r.getCardList().isEmpty()) {
                continue;
            } else if (!r.getCardList().get(0).isFaceUp()) {
                return;
            }
        }
        if (!gameBoard.getPile().getPileList().isEmpty() && !gameBoard.getCardPileRow().getCardList().isEmpty()) {
            return;
        }

        gameWon = true;
        return;
    }

}
