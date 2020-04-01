package controller;

import model.GameBoard;
import model.Row;

public class MoveController {

    private GameBoard gameBoard;

    private GameController gameController;

    private int aceCounter = 0;

    public MoveController(GameBoard gameBoard, GameController gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
    }

    public void makeMove(){
        if(aceCounter < 4) {
            checkforAce();
        }

    }

    private void checkforAce() {
        for(Row r: gameBoard.getRowList()){
            if(r.getTop().getLevel() == 1){

            }

        }
    }


}
