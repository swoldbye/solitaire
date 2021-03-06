package controller;

import model.Card;
import model.GameBoard;
import model.Row;
import view.View;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 'Main' Controller in charge of setting up the game and moving the cards. This class is only usefull for testing
 * purposes that can move the cards so the move logic can make the next move.
 * - Alex
 */

public class GameController {

    private GameBoard gameBoard;

    private MoveController moveController;

    private View view;

    private int rowCardNeeded;

    private boolean isGameWon = false, moveMade, isGameLost = false;

    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.moveController = new MoveController(gameBoard, this);
    }

    public String play() {
        String move = "";
        //setUpGame();
        initView();
        move = startTurn(new Card(4, 0, true));
        return move;
       /* if (isGameWon) {
            return true;
        } else {
            return false;
        }*/
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

    public String startTurn(Card c) {

        String move = "";
        if (c.getLevel() != 0) {
            if (rowCardNeeded == 0) {
                gameBoard.getCardPileRow().addCard(c);
            } else {
                boolean cardInStack = false;
                if (!gameBoard.getCardPileRow().getCardList().isEmpty()) {
                    for (Card pileCard : gameBoard.getCardPileRow().getCardList()) {
                        if (pileCard.getCard().equalsIgnoreCase(c.getCard())) {
                            cardInStack = true;
                        }
                    }
                }
                if (!cardInStack) {
                    gameBoard.getRowList().get(rowCardNeeded - 1).addCard(c);
                }
            }
        }
        view.updateView();
        /*Scanner scan = new Scanner(System.in);
        scan.nextLine();*/
        moveMade = false;
        move = moveController.makeMove();
        if (isGameWon) {
            return "WIN";
        }
        if (isGameLost) {
            return "LOSE";
        }

        return move;

        // startTurn();
    }


    /**
     * Method that moves a card c from row r into a stack
     *
     * @param c card to be moved
     * @param r row the card is in
     *          <p>
     *          - Alex
     */

    public String moveToStack(Card c, Row r) {
        String move = "";
        switch (c.getSuit()) {
            case 0:
                if (gameBoard.getDiamondStack().getTop() == c.getLevel() - 1) {
                    gameBoard.getDiamondStack().addCard(c);
                    moveMade = true;
                    move = "*-* Move " + c.getCard() + " to stack *-*";
                }
                break;
            case 1:
                if (gameBoard.getHeartStack().getTop() == c.getLevel() - 1) {
                    gameBoard.getHeartStack().addCard(c);
                    move = "*-* Move" + c.getCard() + " to stack *-*";
                    moveMade = true;
                }
                break;
            case 2:
                if (gameBoard.getSpadeStack().getTop() == c.getLevel() - 1) {
                    gameBoard.getSpadeStack().addCard(c);
                    move = "*-* Move " + c.getCard() + " to stack *-*";
                    moveMade = true;
                }
                break;
            case 3:
                if (gameBoard.getClubStack().getTop() == c.getLevel() - 1) {
                    gameBoard.getClubStack().addCard(c);
                    move = "*-* Move " + c.getCard() + " to stack *-*";
                    moveMade = true;
                }
                break;
        }
        if (moveMade) {
            r.getCardList().remove(c);
            flipCard(r);
            return move;
        }

        return "";
    }


    /**
     * Used to move a card or multiple cars in a row to another.
     *
     * @param sender
     * @param receiver
     */
    public String moveCardRowToRow(Row sender, Row receiver) {
        String move = "";
        if (sender.getRowLocation() == 0) {
            move = "*-* Move pilecard to " + receiver.getRowLocation() + "*-*";
        } else {
            move = "*-* Move all cards in row " + sender.getRowLocation() + " to " + receiver.getRowLocation() + " *-*";
        }
        for (int i = 0; i < sender.getCardList().size(); i++) {
            if (sender.getCardList().get(i).isFaceUp()) {
                receiver.addCard(sender.getCardList().get(i));
                sender.getCardList().remove(sender.getCardList().get(i));
                moveMade = true;
                i--;
                if (sender.getCardList().size() == sender.getFaceDownCards()) {
                    flipCard(sender);
                    return move;
                }
            }
        }
        return move;
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
        if (!r.getTop().isFaceUp()) {
            rowCardNeeded = r.getRowLocation();
            r.getCardList().remove(r.getCardList().size() - 1);
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

    public String flipCardPile() {
        String move = "*-*Flip Card Pile*-*";
        if (gameBoard.getPile().getPileList().isEmpty()) {
            if (gameBoard.getCardPileRow().getCardList().size() == 1) {
                return move;
            }
            for (int i = gameBoard.getCardPileRow().getCardList().size(); i > 0; i--) {
                Card c = gameBoard.getCardPileRow().getTop();
                c.setFaceUp(false);
                gameBoard.getPile().getPileList().add(c);
                gameBoard.getCardPileRow().getCardList().remove(c);
            }
            return move;
        }
        if (gameBoard.getPile().getTopCard().getLevel() == 0) {
            if (gameBoard.getCardPileRow().getCardList().size() > 0) {
                gameBoard.getCardPileRow().getTop().setFaceUp(false);
            }
            gameBoard.getPile().takeTopCard();
            rowCardNeeded = 0;
            return move;
        }
        gameBoard.getCardPileRow().getTop().setFaceUp(false);
        gameBoard.getCardPileRow().addCard(gameBoard.getPile().takeTopCard());
        gameBoard.getCardPileRow().getTop().setFaceUp(true);
        moveMade = true;
        return move;
    }


    public void victoryFormation() {
       /* view.victoryFormationBaby();
        while (!isGameWon) {
            for (Row r : gameBoard.getRowList()) {
                if (moveMade) {
                    view.updateView();
                }
                if (gameBoard.getDiamondStack().getTop() == 13 && gameBoard.getHeartStack().getTop() == 13
                        && gameBoard.getSpadeStack().getTop() == 13 && gameBoard.getClubStack().getTop() == 13) {
                    isGameWon = true;
                    return;
                }
                moveMade = false;
                moveToStack(r.getTop(), r);
            }
        }
*/
        isGameWon = true;
    }

    public boolean isMoveMade() {
        return moveMade;
    }

    public void gameLost() {
        //   System.out.println("Computer loses cause it's not smoking a fat one");
        isGameLost = true;
    }

    public void doAIMoves(ArrayList<String> moves) {
        //    System.out.println("** DO AI MOVES **");
        for (String s : moves) {
            System.out.println(s);
            /*
            if (s.substring(0, 1).equalsIgnoreCase("0")) {
                //    System.out.println("this card has already been moved");
            } else if (s.substring(0, 1).equalsIgnoreCase("K")) {
                if (s.substring(1, 2).equalsIgnoreCase("R")) {
                    int sender = Integer.valueOf(s.substring(2, 3));
                    int receiver = Integer.valueOf(s.substring(3, 4));
                    moveCardRowToRow(gameBoard.getRowList().get(sender - 1), gameBoard.getRowList().get(receiver - 1));

                } else if (s.substring(1, 2).equalsIgnoreCase("P")) {
                    int rowReciever = Integer.valueOf(s.substring(2, 3));
                    while (true) {
                        if (gameBoard.getCardPileRow().getCardList().isEmpty()) {
                            flipCardPile();
                        } else if (gameBoard.getCardPileRow().getTop().getLevel() == 13) {
                            moveCardRowToRow(gameBoard.getCardPileRow(), gameBoard.getRowList().get(rowReciever - 1));
                            break;
                        } else {
                            flipCardPile();
                        }
                    }
                }
            } else if (s.substring(0, 4).equalsIgnoreCase("PILE")) {
                String pileCardToBeMoved = s.substring(5, 8);
                //   System.out.println("PileCard To find " + pileCardToBeMoved);
                while (true) {
                    if (gameBoard.getCardPileRow().getCardList().isEmpty()) {
                        flipCardPile();
                    } else if (gameBoard.getCardPileRow().getTop().getCard().equalsIgnoreCase(pileCardToBeMoved)) {
                        moveToStack(gameBoard.getCardPileRow().getTop(), gameBoard.getCardPileRow());
                        break;
                    } else {
                        flipCardPile();
                    }
                }
            } else {
                    //System.out.println(s);
                String cardToBeMoved = s.substring(0, 3);
                //    System.out.println(cardToBeMoved);
                //    System.out.println("moving card " + cardToBeMoved);
                for (Row r : gameBoard.getRowList()) {
                    if (r.getTop().getCard().equals(cardToBeMoved)) {
                        moveToStack(r.getTop(), r);
                        break;
                    }
                }
            }
        */
        }
        //      System.out.println("** Finished AI moves **");
        //else if (s.substring(0, 8).equalsIgnoreCase("PileCard")) {
        //                moveToStack(gameBoard.getCardPileRow().getTop(), gameBoard.getCardPileRow());
        //            }
    }
}

