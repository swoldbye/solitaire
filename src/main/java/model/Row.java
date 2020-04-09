package model;

import java.util.ArrayList;
import java.util.List;

public class Row {
    int rowLocation;

    public Row(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public List<Card> getRow() {
        Card[][] deckList = Deck.getDeck().getCardList();
        ArrayList<Card> rowList = new ArrayList<Card>();

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                if (deckList[j][i].getLocation() == rowLocation) {
                    rowList.add(deckList[i][j]);
                }
            }
        }
        return rowList;
    }
}
