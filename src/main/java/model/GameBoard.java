package model;

import java.util.Collection;
import java.util.Collections;

public class GameBoard {

    //Setting up entities for game.

    Pile pile = new Pile(0);

    Row row1 = new Row(1);
    Row row2 = new Row(2);
    Row row3 = new Row(3);
    Row row4 = new Row(4);
    Row row5 = new Row(5);
    Row row6 = new Row(6);
    Row row7 = new Row(7);

    Stack diamondStack = new Stack(1, 8);
    Stack heartStack = new Stack(2, 9);
    Stack spadeStack = new Stack(3, 10);
    Stack clubStack = new Stack(4, 11);

    public GameBoard(){
        for(Card c: pile.getPileList()){
            System.out.println(c.getLocation());

        }
    }

    
}
