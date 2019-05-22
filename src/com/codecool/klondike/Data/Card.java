package com.codecool.klondike.Data;

public class Card {
    private int army;
    private int land;
    private int money;
    private int influence;
    private String name;

    public Card(int army, int land, int money, int influence, String name) {
        this.army = army;
        this.land = land;
        this.money = money;
        this.influence = influence;
        this.name = name;
    }

    public int getArmy() {
        return army;
    }

    public int getLand() {
        return land;
    }

    public int getMoney() {
        return money;
    }

    public int getInfluence() {
        return influence;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " money: " + money +" land: " + land +" army: "+ army + " influence: " + influence;
    }
}
