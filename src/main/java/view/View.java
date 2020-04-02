package view;

import model.GameBoard;
import model.Row;

public class View {

    private GameBoard gameBoard;

    private String topline, rows;

    public View(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        updateView();
    }

    /**
     * To help visualise everything so we can work on logic while the legend huge eggshead and crazybigpussybeard face
     * make the sexy opencv
     *
     * - Mark
     */

    public void updateView() {

        topline = gameBoard.getDiamondStack().getTop() + " " + gameBoard.getHeartStack().getTop() + " " +
                gameBoard.getSpadeStack().getTop() + " " + gameBoard.getClubStack().getTop() + "              " +
                gameBoard.getCardPile().getTop().getCard();

        rows = "";
        for (int i = 0; i < gameBoard.getMaxRowSize(); i++) {
            for (Row r : gameBoard.getRowList()) {
                if (r.getCardList().size() <= i) {
                    rows += "   ";
                } else {
                    rows += r.getCardList().get(i).getCard();
                }
                rows += "   ";
            }
            rows += "\n";
        }

        System.out.println(topline + "\n\n" + rows);
    }

}
