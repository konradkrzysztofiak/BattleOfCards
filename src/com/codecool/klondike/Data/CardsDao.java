package com.codecool.klondike.Data;

import java.util.List;

public interface CardsDao {
    public List getCards();
    public Card getCard(int index);
    public void addCards();
}
