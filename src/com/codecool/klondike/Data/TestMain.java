package com.codecool.klondike.Data;

public class TestMain {
    public static void main(String[] args){
        CardsDaoImpl cardsDao = new CardsDaoImpl();
        cardsDao.addCards();

        //System.out.println(cardsDao.getCards().get(0));
        System.out.println(cardsDao.getCard(0));
    }
}
