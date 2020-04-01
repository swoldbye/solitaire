package model;

/**
 * Suit Numbers:
 * 0 = Hearts
 * 1 = Spades
 * 2 = Diamonds
 * 3 = Clubs
 */

public class Card {

    int suit;
    int level;
    int location;
    boolean isFaceUp;

    public Card() {
    }

    public Card(int suit, int level, boolean isFaceUp) {
        this.suit = suit;
        this.level = level;
        this.isFaceUp = isFaceUp;
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

    public String getCard() {
        String s = "";
        switch (suit) {
            case 0:
                s += "H";
                break;
            case 1:
                s += "S";
                break;
            case 2:
                s += "D";
                break;
            case 3:
                s += "C";
                break;
        }

        s += " " + level;

        return s;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }
}
