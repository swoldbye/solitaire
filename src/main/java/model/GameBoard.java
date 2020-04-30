package model;

import java.util.*;

public class GameBoard {

    //Setting up entities for game.

    Pile pile = new Pile(0);

    Row cardPileRow = new Row(0);

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

    int moveCOunter = 0;


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

    public Row getCardPileRow() {
        return cardPileRow;
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

    public int getMaxRowSize() {
        int max = row1.getCardList().size();
        if (row2.getCardList().size() > max) {
            max = row2.getCardList().size();
        }
        if (row3.getCardList().size() > max) {
            max = row3.getCardList().size();
        }
        if (row4.getCardList().size() > max) {
            max = row4.getCardList().size();
        }
        if (row5.getCardList().size() > max) {
            max = row5.getCardList().size();
        }
        if (row6.getCardList().size() > max) {
            max = row6.getCardList().size();
        }
        if (row7.getCardList().size() > max) {
            max = row7.getCardList().size();
        }

        return max;
    }

    public Stack getStack(int stackNumber) {
        switch (stackNumber) {
            case 0:
                return diamondStack;
            case 1:
                return heartStack;
            case 2:
                return spadeStack;
            case 3:
                return clubStack;
        }
        return diamondStack;
    }

    public void setPile(Pile pile) {
        this.pile = pile;
    }

    public void setCardPileRow(Row cardPileRow) {
        this.cardPileRow = cardPileRow;
    }

    public void setRow1(Row row1) {
        this.row1 = row1;
    }

    public void setRow2(Row row2) {
        this.row2 = row2;
    }

    public void setRow3(Row row3) {
        this.row3 = row3;
    }

    public void setRow4(Row row4) {
        this.row4 = row4;
    }

    public void setRow5(Row row5) {
        this.row5 = row5;
    }

    public void setRow6(Row row6) {
        this.row6 = row6;
    }

    public void setRow7(Row row7) {
        this.row7 = row7;
    }

    public void setRowList(ArrayList<Row> rowList) {
        this.rowList = rowList;
    }

    public void setDiamondStack(Stack diamondStack) {
        this.diamondStack = diamondStack;
    }

    public void setHeartStack(Stack heartStack) {
        this.heartStack = heartStack;
    }

    public void setSpadeStack(Stack spadeStack) {
        this.spadeStack = spadeStack;
    }

    public void setClubStack(Stack clubStack) {
        this.clubStack = clubStack;
    }

    public void setMoveCOunter(int moveCOunter) {
        this.moveCOunter = moveCOunter;
    }
}