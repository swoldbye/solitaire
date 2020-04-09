package model;

public class GameBoard {

    //Setting up entities for game.
    Pile pile = new Pile(0);

    Stack diamondStack = new Stack(1, 1);
    Stack heartStack = new Stack(2, 2);
    Stack spadeStack = new Stack(3, 3);
    Stack clubStack = new Stack(4, 4);

    Row row1 = new Row(5);
    Row row2 = new Row(6);
    Row row3 = new Row(7);
    Row row4 = new Row(8);
    Row row5 = new Row(9);
    Row row6 = new Row(10);
    Row row7 = new Row(11);

    public GameBoard(){}
}
