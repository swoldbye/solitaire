package model;

import java.util.ArrayList;
import java.util.List;

public class Row {
    int rowLocation;

    public Row(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public ArrayList<Card> getRow() {
        Card[][] deckList = Deck.getDeck().getCardList();
        ArrayList<Card> rowList = new ArrayList<Card>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (deckList[i][j].getLocation() == rowLocation) {
                    rowList.add(deckList[i][j]);
                }
            }
        }
        return rowList;
    }
}
