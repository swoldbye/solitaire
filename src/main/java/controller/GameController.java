package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import view.View;

public class GameController {

    private GameBoard gameBoard;

    private MoveController moveController;

    private View view;

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.moveController = new MoveController(gameBoard, this);
        setUpGame();
        initView();
        isGameWon();
    }

    public void initView() {
        this.view = new View(gameBoard);
    }

    public void setUpGame() {
        for (Row r : gameBoard.getRowList()) {
            for (int i = 0; i < r.getRowLocation(); i++) {
                r.addCard(gameBoard.getPile().takeTopCard());
                if (i + 1 == r.getRowLocation()) {
                    r.getCardList().get(i).setFaceUp(true);
                }
            }
        }
    }

    public void isGameWon() {
        if (gameBoard.getDiamondStack().getTop() == 13 && gameBoard.getHeartStack().getTop() == 13 &&
                gameBoard.getSpadeStack().getTop() == 13 && gameBoard.getClubStack().getTop() == 13){
            System.out.println("Cpu wins");
            System.exit(0);
        } else {
            moveController.makeMove();
            view.updateView();
            isGameWon();
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