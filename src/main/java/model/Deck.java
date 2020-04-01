package model;


public class Deck {
    private static Card[][] cardList = new Card[4][13];

    private static Deck deck = new Deck();

    private Deck(){
        createDeck();
    }

    private static void createDeck(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                cardList[i][j] = new Card(i, j+1, false);
            }
        }
    }

    public static Deck getDeck() {
        return deck;
    }

    public static Card[][] getCardList() {
        return cardList;
    }
}
