package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pile {


    Deck deck;
    int pileLocation;
    ArrayList<Card> pileList = new ArrayList<Card>();


    public Pile(int pileLocation){
      //  this.deck = new Deck();
        this.pileLocation = pileLocation;
     //   initPile();
       // Collections.shuffle(pileList);

    }

    public ArrayList<Card> getPileList() {
        return pileList;
    }

    public Card getTopCard(){
        if(pileList.isEmpty()){
            return new Card(4,0,true);
        }
        Card c = pileList.get(pileList.size()-1);
        return c;
    }

    public Card takeTopCard(){
        Card c = pileList.get(pileList.size()-1);
        pileList.remove(c);
        return c;
    }

    public int getPileLocation() {
        return pileLocation;
    }

    public void setPileLocation(int pileLocation) {
        this.pileLocation = pileLocation;
    }

    public void addCard(Card c){
        pileList.add(c);
        c.setLocation(pileLocation);
    }

    public void initPile(){
        Card[][] deckList = deck.getCardList();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                if(deckList[i][j].getLocation() == pileLocation){
                    pileList.add(deckList[i][j]);
                }
            }
        }
    }
}