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
     * <p>
     * - Mark
     */

    public void updateView() {

        topline = gameBoard.getDiamondStack().getTop() + " " + gameBoard.getHeartStack().getTop() + " " +
                gameBoard.getSpadeStack().getTop() + " " + gameBoard.getClubStack().getTop() +
                "                 ";

        if (!gameBoard.getCardPileRow().getCardList().isEmpty()) {
            topline += gameBoard.getCardPileRow().getTop().getCard();
        } else {
            topline += "  ";
        }
        if (gameBoard.getPile().getPileList().isEmpty()) {
            topline += "  0";
        } else {
            topline += gameBoard.getPile().getTopCard().getCard();
        }


        rows = "";
        for (int i = 0; i < gameBoard.getMaxRowSize() + 1; i++) {
            for (Row r : gameBoard.getRowList()) {
                if (r.getCardList().isEmpty() && i == 0) {
                    rows += r.getTop().getCard();
                } else if (r.getCardList().size() <= i) {
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

    public void victoryFormationBaby(){
        System.out.println("VICTORY FORMATION BABY");
    }

}
