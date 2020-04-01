package model;

import java.util.*;

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

    ArrayList<Row> rowList = new ArrayList<Row>();

    Stack diamondStack = new Stack(1, 8);
    Stack heartStack = new Stack(2, 9);
    Stack spadeStack = new Stack(3, 10);
    Stack clubStack = new Stack(4, 11);

    private Random randomGenerator = new Random();

    public GameBoard(){
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        rowList.add(row4);
        rowList.add(row5);
        rowList.add(row6);
        rowList.add(row7);
        for(Row r: rowList){
            for (int i = 0; i < r.rowLocation; i++){
                pile.getPile().get(randomGenerator.nextInt(pile.getPile().size())).setLocation(r.rowLocation);
                if(i +1 == r.rowLocation){
                }
            }
        }

    }
}
