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

    private boolean isKingAvailable = false, gameWon = false;

    private int kingCounter = 0, usedPileCardCounter = 0;

    private AILolController aiLolController;

    private ArrayList<String> aiMoves = new ArrayList<String>();

    int moveCounter;

    public MoveController(GameBoard gameBoard, GameController gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
        int moveCounter = 0;

    }

    /**
     * Main make move method that calls other methods in this class in the order we want to check
     */

    public void makeMove() {
        //gets list with rows with most face down cards in descending order
        faceDownList = gameController.getFaceDownList();

        //is the game won no need to continue
        checkForWin();

        if (gameWon && gameBoard.getCardPileRow().getCardList().isEmpty()) {
            gameController.victoryFormation();
        }
        //Here starts the 'regular' moves
        //Step 1
        checkForAceDeuce();

        //If a move is made it needs to return to start algorithm again with new gameboard
        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }
        //Step 2-6
        checkForAvailableKing();

        if (isKingAvailable && kingCounter < 4) {
            moveKingToEmpty();
        }

        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }

        checkForMoveCard();

        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }

        if (!gameBoard.getCardPileRow().getCardList().isEmpty()) {
            useFlipCard();
        }

        if (gameController.isMoveMade()) {
            usedPileCardCounter = 0;
            return;
        }

        //step 7-8
        //after all the cards in the pile have been around once, and no move has been made. Then we need
        //to make a multiple moves to free a downcard.
        if (usedPileCardCounter > (gameBoard.getCardPileRow().getCardList().size() + gameBoard.getPile().getPileList().size())) {
            aiLolController = new AILolController(faceDownList, gameBoard);
            aiMoves = aiLolController.lookForMove();
            if (!aiMoves.isEmpty()) {
                gameController.doAIMoves(aiMoves);
                usedPileCardCounter = 0;
                return;
            }
        }
        //No move can be made with cards on the table, so another card is flipped.
        gameController.flipCardPile();
        usedPileCardCounter++;
        //When all the cards in the deck have been at least once and no move has been made we are whats called
        //a loss
        if (usedPileCardCounter > (gameBoard.getCardPileRow().getCardList().size() + gameBoard.getPile().getPileList().size())+1) {
            gameController.gameLost();
        }
    }

    /**
     * First check to see if there are any aces or deuces face up, first it searches all rows for aces
     * then deuce
     * <p>
     * - Alex
     */

    private void checkForAceDeuce() {
        for (Row r : gameBoard.getRowList()) {
            if (r.getTop().getLevel() == 1) {
                gameController.moveToStack(r.getTop(), r);
                if (gameController.isMoveMade()) {
                    return;
                }
            }
        }
        for (Row r : gameBoard.getRowList()) {
            if (r.getTop().getLevel() == 2) {
                gameController.moveToStack(r.getTop(), r);
                if (gameController.isMoveMade()) {
                    return;
                }
            }
        }
    }

    /**
     * Second, check to see if there can be freed a downcard, if there are multiple possibilites, move the card
     * that frees the row with most downcards
     * <p>
     * - Alex
     */
    public void checkForMoveCard() {

        for (Row r : faceDownList) {
            if (r.getCardList().isEmpty()) {
                continue;
            }

            //Gets the last face up card before the downcards
            Card c = r.getCardList().get(r.getCardList().size() - (r.getCardList().size() - r.getFaceDownCards()));

            //If card to be moved will leave empty stack, it is only moved if there is a king ready to take it
            if (r.getFaceDownCards() == 0 && !isKingAvailable) {
                continue;
            } else {
                for (Row r2 : faceDownList) {

                    //now looks to see if the above found card c can be moved
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
     * - Alex
     */
    private void checkForAvailableKing() {
        if (kingCounter == 4) {
            isKingAvailable = true;
            return;
        }
        for (Row r : gameBoard.getRowList()) {
            for (Card c : r.getCardList()) {
                if (c.isFaceUp() && c.getLevel() == 13 && r.getFaceDownCards() > 0) {
                    isKingAvailable = true;
                    return;
                }
            }
        }
        //Checks card pile
        if (!gameBoard.getCardPileRow().getCardList().isEmpty()) {
            if (gameBoard.getCardPileRow().getTop().getLevel() == 13) {
                isKingAvailable = true;
                return;
            }
        }
        isKingAvailable = false;
        return;
    }

    private void moveKingToEmpty() {
        if (gameBoard.getCardPileRow().getTop().getLevel() == 13) {
            return;
        }
        for (Row r : gameBoard.getRowList()) {
            if (r.getTop().getLevel() == 0) {
                for (Row r2 : gameBoard.getRowList()) {
                    if (r2 != r && !r2.getCardList().isEmpty()) {
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
                        break;
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
        if (!gameBoard.getPile().getPileList().isEmpty() || !gameBoard.getCardPileRow().getCardList().isEmpty()) {
            return;
        }

        gameWon = true;
        return;
    }

}
