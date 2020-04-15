package model;


public class Deck {

    private Card[][] cardList = new Card[4][13];

//    private Deck deck = new Deck();

    public Deck(){
        createDeck();
    }

    private void createDeck(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                cardList[i][j] = new Card(i, j+1, false);
            }
        }
    }

    /*
    public Deck getDeck() {
        return deck;
    }*/

    public Card[][] getCardList() {
        return cardList;
    }
}
