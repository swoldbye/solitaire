package controller;

import model.GameBoard;
import model.Row;

import java.util.ArrayList;

public class MoveController {

    private ArrayList<Row> faceDownList = new ArrayList<Row>();

    private GameBoard gameBoard;

    private GameController gameController;

    private int aceCounter = 0;

    public MoveController(GameBoard gameBoard, GameController gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
    }

    public void makeMove(){
        if(aceCounter < 4) {
            checkForAce();
        }
        faceDownList = gameController.faceDownList();
        for(Row r: faceDownList){
            System.out.println(r.getTop().getCard());
        }


    }

    private void checkForAce() {
        for(Row r: gameBoard.getRowList()){
            if(r.getTop().getLevel() == 1){
                gameController.moveToStack(r.getTop(),r);
                aceCounter++;
                break;
            }
        }
    }


}
