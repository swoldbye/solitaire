package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import model.Stack;

import javax.management.openmbean.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Controller that searches for card in row with most downcards can be moved and if it is possible
 */

public class AILolController {

    private ArrayList<Row> faceDownList;

    private GameBoard gameBoard;

    private ArrayList<String> movesToBeMade = new ArrayList<String>();

    private ArrayList<Card> cardsMoved = new ArrayList<Card>();

    public AILolController(ArrayList<Row> faceDownList, GameBoard gameBoard) {
        this.faceDownList = faceDownList;
        this.gameBoard = gameBoard;
    }


    public ArrayList<String> lookForMove() {
        ArrayList<ArrayList<String>> AIpos = new ArrayList<ArrayList<String>>();
        Card c;
        ArrayList<Row> availableRows;
        //Move directly to stack
        for (Row r : faceDownList) {
            if (r.getCardList().isEmpty() || r.getFaceDownCards() == 0) {
                continue;
            }
            c = r.getCardList().get(r.getFaceDownCards());
            System.out.println("********");
            System.out.println("Trying to move " + c.getCard() + " directly to stack");
            cardsMoved = new ArrayList<Card>();
            ArrayList<String> moveDirectToStack = checkIfCardsForStack(c);
            if (moveDirectToStack.size() > 0) {
                System.out.println(c.getCard() + " can directly be moved to stack");
                AIpos.add(moveDirectToStack);
            } else {
                System.out.println(c.getCard() + " can NOT directly be moved to stack");
            }
        }

        for (ArrayList<String> possibleMoves : AIpos) {
            System.out.println("START CALCULATION OF MOVES");
            int counter = 0;
            for (String move : possibleMoves) {
                System.out.println(move);
                counter++;
            }
            System.out.println(counter);
        }
        return new ArrayList<String>();
    }

    /**
     * Checks if the missing cards are face up or in the pile / can be used
     *
     * @param c
     * @return
     */


        public ArrayList<String> checkIfCardsForStack(Card c) {
            //For overblik
        System.out.println("START check for moving " + c.getCard());
        ArrayList<String> extraMoves = new ArrayList<String>();
        //Iterates to the top card on the row of the card wished to move
        Row tempRow = gameBoard.getRowList().get(c.getLocation() - 1);
        int cardPlacementCounter1 = 1;
        for (Card tempCard : tempRow.getCardList()) {
            if (tempCard.isFaceUp() && tempCard.getLevel() == c.getLevel()) {
                if (cardPlacementCounter1 < tempRow.getCardList().size()) {
                    extraMoves = checkIfCardsForStack(tempRow.getCardList().get(cardPlacementCounter1));
                    if(extraMoves.size() == 0) {
                        System.out.println("first step fail " + c.getCard());
                        return new ArrayList<String>();
                    }
                }
            }
            cardPlacementCounter1++;
        }
        //Finds out how many missing cards from stack
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
        //Start of searching algorithm
        while (cardsMissing >= 0) {
            available = false;
            cardToFind = c.getLevel() - cardsMissing;
            System.out.println("---" + cardToFind);
            //Checks if the card they are looking for has already been moved
            for(Card movedCard: cardsMoved){
                if(movedCard.getSuit() == c.getSuit() && movedCard.getLevel() == cardToFind){
                    System.out.println(movedCard.getCard() + " has already been moved");

                    cardsMissing--;
                    available = true;
                    break;
                }
            }
            //if the card is free it can be moved
            if (cardsMissing == 0 && !available) {
                System.out.println("EX STEP 1 " + c.getCard() + " TO STACK");
                extraMoves.add(c.getCard() + " to stack");
                cardsMoved.add(c);
                return extraMoves;
            }
            //Checks if card missing is the face up pile card
            if (!available && gameBoard.getCardPileRow().getTop().getSuit() == c.getSuit() && gameBoard.getCardPileRow().getTop().getLevel() == cardToFind) {
                cardsMissing--;
                System.out.println("EX STEP 2 PILE CARD TO STACK");
                extraMoves.add("PileCard to Stack");
                cardsMoved.add(gameBoard.getCardPileRow().getTop());
                available = true;
            }
            //if not, checks the rows if the missing card is there
            if (!available) {
                //goes through each row
                for (Row r : gameBoard.getRowList()) {
                    for (Card card2 : r.getCardList()) {
                        if (card2.getSuit() == c.getSuit() && card2.getLevel() == cardToFind && card2.isFaceUp()) {
                            cardsMissing--;
                            ArrayList<String> rowMoves = checkIfCardsForStack(card2);
                            if(rowMoves.size() == 0){
                                return new ArrayList<String>();
                            }
                            extraMoves.addAll(rowMoves);
                            available = true;
                            cardsMoved.add(card2);
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
                        card3.setFaceUp(true);
                        System.out.println("EX STEP 4 " + card3.getCard() + " TO STACK");
                        extraMoves.add("PILE " + card3.getCard() + " to stack");
                        cardsMoved.add(card3);
                        card3.setFaceUp(false);
                        break;
                    }
                }
            }
            if (!available) {
                System.out.println("FAIL " + c.getCard());
                return new ArrayList<String>();
            }
        }
        return extraMoves;
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


        System.out.println("ORIGIN card moving " + cardToBeMoved.getCard() + " to " + receiver.getRowLocation());

        for (i = receiver.getCardList().size() - 1; i > 0; i--) {
            Card c = receiver.getCardList().get(i);
            if (c.getLevel() > cardToBeMoved.getLevel()) {
                movesToBeMade.add(cardToBeMoved.getCard() + " to " + receiver.getRowLocation());
                return movesToBeMade;
            }


            System.out.println(cardToBeMoved.getCard() + " to " + receiver.getRowLocation());

            ArrayList<String> checkForCards = checkIfCardsForStack(c);

            if (checkForCards.size() > 0) {
                System.out.println("True" + c.getCard());
                movesToBeMade.addAll(checkForCards);
            } else {
                movesToBeMade = new ArrayList<String>();
                System.out.println(cardToBeMoved.getCard() + " move cannot be done");
                break;
            }
        }
        System.out.println("AI MOVES");
        for (String s : movesToBeMade) {
            System.out.println(s);
        }
        return movesToBeMade;
    }

    public void getMoves(Card cardToBeMoved, Row receiver) {
        System.out.println("ORIGIN " + cardToBeMoved.getCard() + " to " + receiver.getRowLocation());
        Card c = receiver.getTop();
        while (c.getLevel() != cardToBeMoved.getLevel() + 1) {

        }


        return;
    }


}

/*
        //Move blocking card to row
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
                    //getMoves(c, r2);
                    movesToBeMade = isMovePossible(c, r2);
                    if (!movesToBeMade.isEmpty()) {
                        System.out.println("Move Can be done!");
                        AIpos.add(movesToBeMade);
                        //return movesToBeMade;
                    }
                }
            }
        }
*/

//this is used to check the cards before the available card
                    /*int cardPlacementCounter = -1;
                    for (Card card2 : r.getCardList()) {
                        cardPlacementCounter++;
                        if (card2.getSuit() == c.getSuit() && card2.getLevel() == cardToFind && card2.isFaceUp()) {
                            cardsMissing--;
                            available = true;
                            //next for loop check that there are no cards blocking 'card2'
                            //if there are, they are checked if they can be move
                            for (int i = r.getCardList().size() - 1; i > cardPlacementCounter; i--) {
                                Card tempCard = r.getCardList().get(i);
                                if (tempCard.getLevel() == card2.getLevel()) {
                                    System.out.println("EX STEP 3 " + card2.getCard() + " TO STACK");
                                    extraMoves.add(card2.getCard() + " to stack");
                                    break;
                                }
                                ArrayList nextCardMoves = checkIfCardsForStack(tempCard);
                                if (nextCardMoves.size() == 0) {
                                    return new ArrayList<String>();
                                }
                                extraMoves.addAll(nextCardMoves);
                            }
                            break;
                        }
                    }
                }*/