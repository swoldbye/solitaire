package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import view.View;

public class GameController {

    private GameBoard gameBoard;

    private View view;

    public GameController(){
        gameBoard = new GameBoard();
    }

    public void initView(){
        this.view = new View(gameBoard);
    }

    public void moveCardRow(Card c, int moveLocation){
        for(Row r: rowList){
            if(r.rowLocation == c.getLocation()){
                r.getRowList().remove(c);
            }
            if(r.rowLocation == moveLocation){
                r.rowList.add(c);
            }
            c.setLocation(moveLocation);
        }
    }

}
