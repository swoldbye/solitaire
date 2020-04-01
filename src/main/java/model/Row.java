package model;

import java.util.ArrayList;
import java.util.List;

public class Row {
    int rowLocation;
    ArrayList<Card> cards = new ArrayList<Card>();

    public Row(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public ArrayList<Card> getCardList() {
        return cards;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public Card getTop(){
        return cards.get(cards.size()-1);
    }

    public void addCard(Card c){
        cards.add(c);
        c.setLocation(rowLocation);
    }

    public int getFaceDownCards(){
        int faceDown = 0;
        for(Card c: cards){
            if (!c.isFaceUp){
                faceDown++;
            }
        }
        return faceDown;
    }

}


/*public ArrayList<Card> getRow() {
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
    }*/