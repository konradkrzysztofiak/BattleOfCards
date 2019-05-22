package com.codecool.klondike.Data;

import org.w3c.dom.NodeList;

import java.util.List;

public interface CardsDao {
    List getCards();
    Card getCard(int index);
    void addCards();
    NodeList getCardParameters();
}
