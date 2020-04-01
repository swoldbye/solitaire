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
    boolean isFaceDown;

    public Card() {
    }

    public Card(int suit, int level, boolean isFaceDown) {
        this.suit = suit;
        this.level = level;
        this.isFaceDown = isFaceDown;
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

    public boolean isFaceDown() {
        return isFaceDown;
    }

    public void setFaceDown(boolean faceDown) {
        isFaceDown = faceDown;
    }
}
