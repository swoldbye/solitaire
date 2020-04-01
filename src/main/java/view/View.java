package view;

import model.GameBoard;
import model.Row;

public class View {

    private GameBoard gameBoard;

    private String topline, rows = "";

    public View(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        updateView();
    }

    private void updateView() {
        topline = gameBoard.getDiamondStack().getTop() + " " + gameBoard.getHeartStack().getTop() + " " +
                gameBoard.getSpadeStack().getTop() + " " + gameBoard.getClubStack().getTop() + "      " +
                gameBoard.getPile().getTopCard().getCard();

        for (int i = 0; i < 7; i++) {
            for (Row r : gameBoard.getRowList()) {
                if (r.getRowLocation() <= i) {
                    rows += "  ";
                } else {
                    rows += r.getRowList().get(i).getCard();
                }
                rows += "  ";
            }
            rows += "\n";
        }


        System.out.println(topline + "\n\n" + rows);
    }

}
