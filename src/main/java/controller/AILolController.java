package controller;

import model.Card;
import model.GameBoard;
import model.Row;

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

        for(Row r: faceDownList){
            if (r.getCardList().isEmpty() || r.getFaceDownCards() == 0) {
                continue;
            }
            c = r.getCardList().get(r.getFaceDownCards());
            if(checkIfCardsForStack(c)){
                System.out.println(c.getCard() + " can be moved to stack");
                return movesToBeMade;
            } else {
                System.out.println(c.getCard() + " cant be moved to stack");
            }
        }

        //Ses if theres a card in the card pile can be moved.

       /* gameBoard.getCardPileRow().addCard(gameBoard.getCardPileRow().getTop());

        for (int i = gameBoard.getCardPileRow().getCardList().size() - 1; i >= 0; i--) {
            availableRows = findRowsForMove(gameBoard.getCardPileRow().getCardList().get(i));
            for (Row r2 : availableRows) {
                movesToBeMade = isMovePossible(gameBoard.getCardPileRow().getCardList().get(i), r2);
                if (!movesToBeMade.isEmpty()) {
                    return movesToBeMade;
                }
            }
        }*/

        return movesToBeMade;
    }


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


    public ArrayList<String> isMovePossible(Card cardToBeMoved, Row receiver) {

        ArrayList<String> movesToBeMade = new ArrayList<String>();
        int i;


        for (i = receiver.getCardList().size() - 1; i > 0; i--) {
            Card c = receiver.getCardList().get(i);
            if (c.getLevel() > cardToBeMoved.getLevel()) {
                return movesToBeMade;
            }

            System.out.println(cardToBeMoved.getCard() + " to " + receiver.getRowLocation());

            if (checkIfCardsForStack(c)) {
                System.out.println("True fam init" + c.getCard());
                movesToBeMade.add(c.getCard() + " to stack");
            } else {
                movesToBeMade = new ArrayList<String>();
                System.out.println(c.getCard() + " cannot be moved");
                break;
            }
        }
        return movesToBeMade;
    }


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

        if (cardsMissing == 0){
            return true;
        }

        while (cardsMissing > 0) {
            available = false;
            cardToFind = c.getLevel() - cardsMissing;
            for (Row r : gameBoard.getRowList()) {
                for (Card card2 : r.getCardList()) {
                    if (card2.getSuit() == c.getSuit() && card2.getLevel() == cardToFind && card2.isFaceUp()) {
                        cardsMissing--;
                        available = true;
                        break;
                    }
                }
            }
            if(!available){
                return false;
            }
        }
        return available;
    }

}


//Ses if there is a card on the table that will free the downcard
             /*for (Row r2 : faceDownList) {
                if (r2 != r && !r2.getCardList().isEmpty()) {
                    cardsInWayTemp = 0;
                   for (int i = r2.getCardList().size() - 1; i >= 0; i--) {
                        Card c2 = r2.getCardList().get(i);
                        if (!c2.isFaceUp()) {
                            break;
                        }
                        if (c2.getLevel() > c.getLevel() + 1) {
                            break;
                        }
                        if (c.getLevel() == c2.getLevel() + 1 && !c.getColour().equals(c2.getColour()) && cardsInWay > cardsInWayTemp) {
                            chosenRow = r2;
                            cardsInWay = cardsInWayTemp;
                        }
                        cardsInWayTemp++;
                    }
                }
            }
        }*/