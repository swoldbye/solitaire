package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import model.Stack;

import java.util.ArrayList;

/**
 * Controller that searches for card in row with most downcards can be moved and if it is possible
 */

public class AILolController {

    private ArrayList<Row> faceDownList;

    private GameBoard gameBoard;

    public AILolController(ArrayList<Row> faceDownList, GameBoard gameBoard) {
        this.faceDownList = faceDownList;
        this.gameBoard = gameBoard;
    }


    public ArrayList<String> lookForMove() {
        Card c;
        ArrayList<Row> availableRows;
        ArrayList<String> movesToBeMade = new ArrayList<String>();


        for (Row r : faceDownList) {
            if (r.getCardList().isEmpty() || r.getFaceDownCards() == 0) {
                continue;
            }

            //Gets the last face up card before the downcards
            c = r.getCardList().get(r.getCardList().size() - (r.getCardList().size() - r.getFaceDownCards()));
            availableRows = findRowsForMove(c);

            //goes through the rows to see if they can be moved
            if (!availableRows.isEmpty()) {
                for (Row r2 : availableRows) {
                    movesToBeMade = isMovePossible(c, r2);
                    if (!movesToBeMade.isEmpty()) {
                        System.out.println("Move Can be done!");
                        return movesToBeMade;
                    }
                }
            }
        }

        for (Row r : faceDownList) {
            if (r.getCardList().isEmpty() || r.getFaceDownCards() == 0) {
                continue;
            }
            c = r.getCardList().get(r.getFaceDownCards());
            if (checkIfCardsForStack(c)) {
                System.out.println(c.getCard() + " can be moved to stack");
                return movesToBeMade;
            } else {
                System.out.println(c.getCard() + " cant be moved to stack");
            }
        }
        return movesToBeMade;
    }


    /**
     * Takes a card c, which is the top face up card in the row with most downcards that can be moved, finds which row
     * it can be moved to
     *
     * @param c
     * @return
     */
    public ArrayList<Row> findRowsForMove(Card c) {
        ArrayList<Row> chosenRows = new ArrayList<Row>();
        int cardsInWayTemp = 0, cardsInWay = 0;
        for (Row r : faceDownList) {
            if (r.getRowLocation() != c.getLocation() && !r.getCardList().isEmpty()) {
                cardsInWayTemp = 0;
                for (int i = r.getCardList().size() - 1; i >= 0; i--) {
                    Card c2 = r.getCardList().get(i);
                    if (!c2.isFaceUp()) {
                        break;
                    }
                    if (c2.getLevel() > c.getLevel() + 1) {
                        break;
                    }
                    if (c.getLevel() == c2.getLevel() - 1 && !c.getColour().equals(c2.getColour())) {
                        if (cardsInWay <= cardsInWayTemp) {
                            chosenRows.add(r);
                            cardsInWay = cardsInWayTemp;
                        } else {
                            chosenRows.add(0, r);
                            cardsInWay = cardsInWayTemp;
                        }
                    }
                    cardsInWayTemp++;
                }
            }
        }
        return chosenRows;
    }

    /**
     * After finding which rows would be possible to move to, it sees if the cards can be moved to stack.
     *
     * @param cardToBeMoved
     * @param receiver
     * @return
     */

    public ArrayList<String> isMovePossible(Card cardToBeMoved, Row receiver) {

        ArrayList<String> movesToBeMade = new ArrayList<String>();
        int i;

        System.out.println("ORIGIN " + cardToBeMoved.getCard() + " to " + receiver.getRowLocation());
        for (i = receiver.getCardList().size() - 1; i > 0; i--) {
            Card c = receiver.getCardList().get(i);
            if (c.getLevel() > cardToBeMoved.getLevel()) {
                return movesToBeMade;
            }


            System.out.println(cardToBeMoved.getCard() + " to " + receiver.getRowLocation());

            if (checkIfCardsForStack(c)) {
                System.out.println("True" + c.getCard());
                movesToBeMade.add(c.getCard() + " to stack");
            } else {
                movesToBeMade = new ArrayList<String>();
                System.out.println(c.getCard() + " cannot be moved");
                break;
            }
        }
        return movesToBeMade;
    }

    /**
     * Checks if the missing cards are face up or in the pile / can be used
     *
     * @param c
     * @return
     */


    public boolean checkIfCardsForStack(Card c) {
        int cardsMissing = 0, cardToFind;
        boolean available = false;
        switch (c.getSuit()) {
            case 0:
                cardsMissing = c.getLevel() - gameBoard.getDiamondStack().getTop() - 1;
                break;
            case 1:
                cardsMissing = c.getLevel() - gameBoard.getHeartStack().getTop() - 1;
                break;
            case 2:
                cardsMissing = c.getLevel() - gameBoard.getSpadeStack().getTop() - 1;
                break;
            case 3:
                cardsMissing = c.getLevel() - gameBoard.getClubStack().getTop() - 1;
                break;
        }
        if (cardsMissing == 0) {
            return true;
        }
        while (cardsMissing > 0) {
            available = false;
            cardToFind = c.getLevel() - cardsMissing;
            //Checks if cardmissing is the face up pile card
            if (gameBoard.getCardPileRow().getTop().getSuit() == c.getSuit() && gameBoard.getCardPileRow().getTop().getLevel() == cardToFind) {
                cardsMissing--;
                available = true;
            }
            //if not, checks the rows if the missing card is there
            if (!available) {
                for (Row r : gameBoard.getRowList()) {
                    for (Card card2 : r.getCardList()) {
                        if (card2.getSuit() == c.getSuit() && card2.getLevel() == cardToFind && card2.isFaceUp()) {
                            cardsMissing--;
                            available = true;
                            break;
                        }
                    }
                }
            }
            //Checks down pile
            if (!available) {
                for (Card card3 : gameBoard.getPile().getPileList()) {
                    if (card3.getSuit() == c.getSuit() && card3.getLevel() == cardToFind) {
                        cardsMissing--;
                        available = true;
                        break;
                    }
                }
            }
            if (!available) {
                return false;
            }
        }
        return available;
    }

    /*
    public ArrayList<String> getMoves(Card c, Stack s){

    }
*/

}