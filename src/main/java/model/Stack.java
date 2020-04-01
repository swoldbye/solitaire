package model;

import java.util.ArrayList;

public class Stack {

    int suit;
    int stackLocation;
    ArrayList<Card> stackList = new ArrayList<Card>();

    public Stack(int suit, int stackLocation){
        this.suit = suit;
        this.stackLocation = stackLocation;
    }

    public int getSuit() {
        return suit;
    }

    public int getStackLocation() {
        return stackLocation;
    }

    public ArrayList<Card> getStackList() {
        return stackList;
    }

    public int getTop(){
        if(stackList.isEmpty()){
            return 0;
        }
        return stackList.get(stackList.size()-1).getLevel();
    }

    public void addCard(Card c){
        stackList.add(c);
        c.setLocation(stackLocation);
    }

}

 /*public int getCurrent() {
        Card[] deckList = Deck.getDeck().getCardList()[suit];

        for(int i = 12; i >= 0; i--){
            if(deckList[i].getLocation() == stackLocation){
                return i+1;
            }
        }
        return 0;
    }*/
