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

    private ArrayList<String> chosenMoves = new ArrayList<String>();

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
            cardsMoved = new ArrayList<Card>();
            ArrayList<String> moveDirectToStack = checkIfCardsForStack(c);
            if (moveDirectToStack.size() > 0) {
                AIpos.add(moveDirectToStack);
            }
        }

        //If there is a king check for free space
        boolean kingMove = false;
        for (Row r2 : faceDownList) {
            if (r2.getCardList().isEmpty() || r2.getFaceDownCards() == 0) {
                continue;
            }
            c = r2.getCardList().get(r2.getFaceDownCards());
            if (c.getLevel() == 13) {
                for (Row r3 : faceDownList) {
                    if (r3.getFaceDownCards() == 0 && r3.getTop().getLevel() != 0) {
                        cardsMoved = new ArrayList<Card>();
                        ArrayList<String> moveForKing = checkIfCardsForStack(r3.getCardList().get(0));
                        if (moveForKing.size() > 0) {
                            moveForKing.add("KR" + r2.getRowLocation() + "" + r3.getRowLocation());
                            AIpos.add(moveForKing);
                            kingMove = true;
                        }
                    }
                }
            }
        }
        //if king is in pile
        //start check top card
        if (!kingMove) {
            for (Card pileCard : gameBoard.getPile().getPileList()) {
                if (pileCard.getLevel() == 13) {
                    for (Row searchingRow : gameBoard.getRowList()) {
                        if (searchingRow.getFaceDownCards() == 0 && searchingRow.getTop().getLevel() != 0) {
                            cardsMoved = new ArrayList<Card>();
                            ArrayList<String> moveForKing = checkIfCardsForStack(searchingRow.getCardList().get(0));
                            if (moveForKing.size() > 0) {
                                moveForKing.add("KP" + searchingRow.getRowLocation());
                                AIpos.add(moveForKing);
                            }
                        }
                    }
                }
            }
        }
        //If there are available moves, chosenmoves is set to the first one
        if (AIpos.size() > 0) {
            chosenMoves = AIpos.get(0);
        }

        //if there are more than one option, it chooses the shortest one
        for (ArrayList<String> possibleMoves : AIpos) {
            if (possibleMoves.size() < chosenMoves.size()) {
                chosenMoves = possibleMoves;
            }
        }
        return chosenMoves;
    }

    /**
     * Checks if the missing cards are face up or in the pile / can be used
     *
     * @param c
     * @return
     */


    public ArrayList<String> checkIfCardsForStack(Card c) {
        ArrayList<String> extraMoves = new ArrayList<String>();
        //Iterates to the top card on the row of the card wished to move
        Row tempRow = gameBoard.getRowList().get(c.getLocation() - 1);
        int cardPlacementCounter1 = 1;
        for (Card tempCard : tempRow.getCardList()) {
            if (tempCard.isFaceUp() && tempCard.getLevel() == c.getLevel()) {
                if (cardPlacementCounter1 < tempRow.getCardList().size()) {
                    extraMoves = checkIfCardsForStack(tempRow.getCardList().get(cardPlacementCounter1));
                    if (extraMoves.size() == 0) {
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
            //Checks if the card they are looking for has already been moved
            for (Card movedCard : cardsMoved) {
                if (movedCard.getSuit() == c.getSuit() && movedCard.getLevel() == cardToFind) {
                    extraMoves.add("0 " + movedCard.getCard() + "Already Moved");
                    cardsMissing--;
                    available = true;
                    break;
                }
            }
            //if the card is free it can be moved
            if (cardsMissing == 0 && !available) {
                extraMoves.add(c.getCard() + " to stack");
                cardsMoved.add(c);
                return extraMoves;
            }
            //Checks if card missing is the face up pile card
            if (!available && gameBoard.getCardPileRow().getTop().getSuit() == c.getSuit() && gameBoard.getCardPileRow().getTop().getLevel() == cardToFind) {
                cardsMissing--;
                extraMoves.add("PILE " + gameBoard.getCardPileRow().getTop().getCard() + " to stack");
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
                            if (rowMoves.size() == 0) {
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
                    card3.setFaceUp(true);
                    if (card3.getSuit() == c.getSuit() && card3.getLevel() == cardToFind) {
                        cardsMissing--;
                        available = true;
                        card3.setFaceUp(true);
                        extraMoves.add("PILE " + card3.getCard() + " to stack");
                        cardsMoved.add(card3);
                        card3.setFaceUp(false);
                        break;
                    }
                    card3.setFaceUp(false);
                }
            }
            if (!available) {
                return new ArrayList<String>();
            }
        }
        return extraMoves;
    }
}