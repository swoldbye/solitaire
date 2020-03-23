package model;

import java.util.ArrayList;
import java.util.List;

public class Pile {
    int pileLocation;

    public Pile(int pileLocation){
        this.pileLocation = pileLocation;
    }

    public int getPileLocation() {
        return pileLocation;
    }

    public void setPileLocation(int pileLocation) {
        this.pileLocation = pileLocation;
    }

    public List<Card> getPile(){
        Card[][] deckList = Deck.getDeck().getCardList();
        ArrayList<Card> pileList = new ArrayList<Card>();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                if(deckList[i][j].getLocation() == pileLocation){
                    pileList.add(deckList[i][j]);
                }
            }
        }
        return pileList;
    }
}
