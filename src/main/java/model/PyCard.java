package model;

public class PyCard {
    String suitNumber;
    int x;
    int y;
    int picNumber;
    int suit;
    int value;
    int color;

    public PyCard(String suitNumber, int x, int y, int picNumber, int suit, int value, int color) {
        this.suitNumber = suitNumber;
        this.x = x;
        this.y = y;
        this.picNumber = picNumber;
        this.suit = suit;
        this.value = value;
        this.color = color;
    }

    public String getSuitNumber() {
        return suitNumber;
    }

    public void setSuitNumber(String suitNumber) {
        this.suitNumber = suitNumber;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPicNumber() {
        return picNumber;
    }

    public void setPicNumber(int picNumber) {
        this.picNumber = picNumber;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
