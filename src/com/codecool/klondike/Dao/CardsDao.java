package com.codecool.klondike.Dao;

import org.w3c.dom.NodeList;

import java.util.List;

import com.codecool.klondike.Card;

public interface CardsDao {
    List getCards();
    Card getCard(int index);
    void addCards();
    NodeList getCardParameters();
}
