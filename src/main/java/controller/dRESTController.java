package controller;

import model.Card;
import model.Pile;
import model.Row;

public class dRESTController {

    Pile pile = new Pile(0);
    Row onTableCards = new Row(0);
    Row extraCards = new Row(1);

    public dRESTController() {
        int cardCOunter = 0;
        for (int i = 0; i < 52; i++) {
            if (i < 28) {
                onTableCards.addCard(pile.takeTopCard());
            } else {
                extraCards.addCard(pile.takeTopCard());
            }
        }
       for(Card c: onTableCards.getCardList()){
            c.setFaceUp(true);
            System.out.println(c.getCard());
            cardCOunter++;
        }
        for(Card c: extraCards.getCardList()){
            c.setFaceUp(true);
            System.out.println(c.getCard());
            cardCOunter++;
        }
        System.out.println(cardCOunter);
    }

    public Card getTopTableCard() {
        Card c = onTableCards.getTop();
        c.setFaceUp(true);
        onTableCards.getCardList().remove(onTableCards.getCardList().size() - 1);
        return c;
    }

    public Row startCards() {
        Row startingCards = new Row(0);
        for (int i = 0; i <= 7; i++){
            startingCards.addCard(getTopTableCard());
        }
            return startingCards;
    }

    public Card getExtraCard() {
        if(extraCards.getCardList().isEmpty()){
        }
        Card c = extraCards.getTop();
        extraCards.getCardList().remove(onTableCards.getCardList().size() - 1);
        c.setFaceUp(true);
        return c;
    }

    public void returnExtraCard(Card c){
        c.setFaceUp(false);
        extraCards.getCardList().add(0, c);
    }


}
