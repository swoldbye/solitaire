package model;

/**
 * Suit Numbers:
 * 0 = Diamonds
 * 1 = Heart
 * 2 = Spade
 * 3 = Clubs
 * 4 = Empty Field
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

    public String getColour() {
        if (suit < 2) {
            return "Red";
        } else if (suit < 4) {
            return "Black";
        }
        return "Empty";
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
        if (isFaceUp) {
            String s = "";
            switch (suit) {
                case 0:
                    s += "D";
                    break;
                case 1:
                    s += "H";
                    break;
                case 2:
                    s += "S";
                    break;
                case 3:
                    s += "C";
                    break;
                case 4:
                    s += "E";
                    break;
            }
            if (level >= 10) {
                s += level;
            } else {
                s = " " + s + level;
            }


            return s;
        } else {
            return "  x";
        }
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }
}
