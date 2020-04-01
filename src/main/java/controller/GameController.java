package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import model.Stack;
import view.View;

import java.util.ArrayList;
import java.util.Scanner;

public class GameController {

    private GameBoard gameBoard;

    private MoveController moveController;

    private View view;

    private Scanner scan = new Scanner(System.in);

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
                gameBoard.getSpadeStack().getTop() == 13 && gameBoard.getClubStack().getTop() == 13) {
            System.out.println("Cpu wins");
            System.exit(0);
        } else {
            scan.nextLine();
            moveController.makeMove();
            view.updateView();
            isGameWon();
        }
    }

    public void moveToStack(Card c, Row r) {
        switch (c.getSuit()) {
            case 0:
                gameBoard.getDiamondStack().addCard(c);
                break;
            case 1:
                gameBoard.getHeartStack().addCard(c);
                break;
            case 2:
                gameBoard.getSpadeStack().addCard(c);
                break;
            case 3:
                gameBoard.getClubStack().addCard(c);
                break;
        }
        r.getCardList().remove(c);
        flipCard(r);
    }

    public void flipCard(Row r) {
        if (r.getCardList().isEmpty()) {
            r.getCardList().add(new Card(4, 0, true));
        } else if (!r.getTop().isFaceUp()) {
            r.getTop().setFaceUp(true);
        }
    }

    public ArrayList<Row> faceDownList() {
        ArrayList<Row> faceDownList = new ArrayList<Row>();

        return faceDownList;
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