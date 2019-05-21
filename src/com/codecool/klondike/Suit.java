package com.codecool.klondike;

public enum Suit
{
    HEARTS(1, "hearts", "red"),
    DIAMONDS(2, "diamonds", "red"),
    SPADES(3, "spades", "black"),
    CLUBS(4, "clubs", "black");

    private final int suit;
    private final String name;
    private final String color;

    Suit(int suit, String name, String color) {
        this.suit = suit;
        this.name = name;
        this.color = color;
    }

    public int getSuit() {
        return suit;
    }

    public String getName() {
        return name;
    }

    public String getColor() { return color; }
}