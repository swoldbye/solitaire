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

    public GameBoard() {
        initRowList();
    }

    private void initRowList() {
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        rowList.add(row4);
        rowList.add(row5);
        rowList.add(row6);
        rowList.add(row7);
    }

    public Pile getPile() {
        return pile;
    }

    public Row getRow1() {
        return row1;
    }

    public Row getRow2() {
        return row2;
    }

    public Row getRow3() {
        return row3;
    }

    public Row getRow4() {
        return row4;
    }

    public Row getRow5() {
        return row5;
    }

    public Row getRow6() {
        return row6;
    }

    public Row getRow7() {
        return row7;
    }

    public ArrayList<Row> getRowList() {
        return rowList;
    }

    public Stack getDiamondStack() {
        return diamondStack;
    }

    public Stack getHeartStack() {
        return heartStack;
    }

    public Stack getSpadeStack() {
        return spadeStack;
    }

    public Stack getClubStack() {
        return clubStack;
    }
}
