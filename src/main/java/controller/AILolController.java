package controller;

import model.Card;
import model.GameBoard;
import model.Row;

import java.util.ArrayList;

public class AILolController {

    private ArrayList<Row> faceDownList;

    private GameBoard gameBoard;

    public AILolController(ArrayList<Row> faceDownList, GameBoard gameBoard) {
        this.faceDownList = faceDownList;
        this.gameBoard = gameBoard;
    }


    public void lookForMove() {
        Card c = new Card(4,0,true);
        Row chosenRow = new Row(69);
        ArrayList<String> movesToBeMade = new ArrayList<String>();
        for (Row r : faceDownList) {
            if (r.getCardList().isEmpty()) {
                continue;
            }

            //Gets the last face up card before the downcards
            c = r.getCardList().get(r.getCardList().size() - (r.getCardList().size() - r.getFaceDownCards()));
            chosenRow = findRowForMove(c);

            if (chosenRow.getRowLocation() != 69) {
                break;
            }

        }

        //Ses if theres a card in the card pile can be moved.
        if (chosenRow.getRowLocation() == 69) {
            gameBoard.getCardPileRow().addCard(gameBoard.getCardPileRow().getTop());
            for (int i = gameBoard.getCardPileRow().getCardList().size() - 1; i >= 0; i--) {
                while (chosenRow.getRowLocation() == 69) {
                    chosenRow = findRowForMove(gameBoard.getCardPileRow().getCardList().get(i));
                }
            }
        }

        movesToBeMade = isMovePossible(c,chosenRow);

    }


    public Row findRowForMove(Card c) {
        Row chosenRow = new Row(69);
        int cardsInWayTemp = 0, cardsInWay = 100;
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
                    if (c.getLevel() == c2.getLevel() - 1 && !c.getColour().equals(c2.getColour()) && cardsInWay > cardsInWayTemp) {
                        chosenRow = r;
                        cardsInWay = cardsInWayTemp;
                    }
                    cardsInWayTemp++;
                }
            }
        }
        return chosenRow;
    }



    public ArrayList<String> isMovePossible(Card cardToBeMoved, Row receiver) {
        ArrayList<String> movesToBeMade = new ArrayList<String>();


        return movesToBeMade;
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