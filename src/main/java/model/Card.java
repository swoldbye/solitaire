package model;

public class Card {

    int suit;
    int level;
    int location;

    public Card(){}

    public Card(int suit, int level){
        this.suit = suit;
        this.level = level;
    }

    public int getSuit() {
        return suit;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
