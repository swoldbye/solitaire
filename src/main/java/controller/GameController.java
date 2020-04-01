package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import model.Stack;
import view.View;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 'Main' Controller in charge of setting up the game and moving the cards
 * - Alex
 */

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

    /**
     * As the cards are shuffled in the pile model class, to se up the game all we need to do is take from this pile
     * and put in the respective rows. The final card is then placed face up
     * <p>
     * Of course this will be replaced with our crazy lit opencv python greatness
     * <p>
     * - Alex
     */

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

    /**
     * The recursive call inside a method that first checks to see if the game is won, if not, asks the
     * MoveController what to do next. Im thinking of adding in the movecontroller something to say there are no
     * more available moves and to sout you have lost.
     * <p>
     * - Alex
     */

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

    /**
     * Method that moves a card c from row r into a stack
     *
     * @param c card to be moved
     * @param r row the card is in
     *          <p>
     *          - Alex
     */

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

    /**
     * A method that called whenever a move is made to know wether there is a card on the table that needs
     * to be flipped before going on the next turn
     *
     * @param r the row where a card has been moved
     *          <p>
     *          - Alex
     */

    public void flipCard(Row r) {
        if (r.getCardList().isEmpty()) {
            r.getCardList().add(new Card(4, 0, true));
        } else if (!r.getTop().isFaceUp()) {
            r.getTop().setFaceUp(true);
        }
    }

    /**
     * As many of the moves are dependent on which row has most downfacing cards, this method returns an
     * arraylist that has them in decending order, so in the moveController class we can do a foreach where it
     * checks the ones with highest amount downcards first
     *
     * @return arraylist of rows in decending order of how many downfacing cards they have
     * - Alex
     */

    public ArrayList<Row> getFaceDownList() {
        ArrayList<Row> faceDownList = new ArrayList<Row>();
        int position;

        for (int i = 0; i < gameBoard.getRowList().size(); i++) {
            position = i;
            if (faceDownList.isEmpty()) {
                faceDownList.add(0, gameBoard.getRowList().get(0));
            } else {
                while (gameBoard.getRowList().get(i).getFaceDownCards() > faceDownList.get(position - 1).getFaceDownCards()) {
                    position--;
                    if (position == 0) {
                        break;
                    }
                }
                faceDownList.add(position, gameBoard.getRowList().get(i));
            }
        }
        return faceDownList;
    }


    /**
     * Used to move a card or multiple cars in a row to another.
     * @param sender
     * @param receiver
     */
    public void moveCardRowToRow(Row sender, Row receiver) {
        for (int i = 0; i < sender.getCardList().size(); i++) {
            if (sender.getCardList().get(i).isFaceUp()) {
                receiver.addCard(sender.getCardList().get(i));
                sender.getCardList().remove(sender.getCardList().get(i));
                i--;
                if (sender.getCardList().size() == sender.getFaceDownCards()) {
                    flipCard(sender);
                    return;
                }
            }
        }
    }

}



