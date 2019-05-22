package com.codecool.klondike.Data;

import org.w3c.dom.NodeList;

import java.util.List;

public interface CardsDao {
    public List getCards();
    public Card getCard(int index);
    public void addCards();
    public NodeList getCardParameters();
}
