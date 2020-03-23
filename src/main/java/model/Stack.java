package model;

public class Stack {
    int suit;
    int stackLocation;

    public Stack(int suit, int stackLocation){
        this.suit = suit;
        this.stackLocation = stackLocation;
    }

    public int getSuit() {
        return suit;
    }

    public int getCurrent() {
        Card[] deckList = Deck.getDeck().getCardList()[suit];

        for(int i = 12; i >= 0; i--){
            if(deckList[i].getLocation() == stackLocation){
                return i+1;
            }
        }
        return 0;
    }
}
