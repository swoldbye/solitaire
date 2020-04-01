package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import view.View;

public class GameController {

    private GameBoard gameBoard;

    private View view;

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setUpGame();
        initView();
        System.out.println("fuck you git");
    }

    public void initView() {
        this.view = new View(gameBoard);
    }

    public void setUpGame() {
        int counter = 0;
        for (Row r : gameBoard.getRowList()) {
            for (int i = 0; i < r.getRowLocation(); i++) {
                r.addCard(gameBoard.getPile().takeTopCard());
                counter++;
                if (i + 1 == r.getRowLocation()) {
                    r.getRowList().get(i).setFaceUp(true);
                }
            }
        }
    }
}



  /*public void moveCardRow (Card c,int moveLocation){
            for (Row r : gameBoard.getRowList()) {
                if (r.getRowLocation() == c.getLocation()) {
                    r.getRowList().remove(c);
                }
                if (r.getRowLocation() == moveLocation) {
                    r.getRowList().add(c);
                }
                c.setLocation(moveLocation);
            }
        }*/