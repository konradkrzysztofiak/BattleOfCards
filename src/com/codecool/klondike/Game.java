package com.codecool.klondike;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Game extends Pane {

    private List<Card> deck = new ArrayList<>();

    private Pile player1Pile;
    //private List<playersPiles> playersPiles = new ArrayList<>();
    private Pile player2Pile;
    private Pile player1Fight;
    private Pile player2Fight;
    private String[] names = {"Eugeniusz", "Mieczyslaw"};


    private double dragStartX, dragStartY;
    private List<Card> draggedCards = FXCollections.observableArrayList();

    private static double PLAYER_GAP = 1;
    private static double FOUNDATION_GAP = 1;
    private static double FIGHT_GAP = 30;


    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
        int i = 0;


        Card card = (Card) e.getSource();
        Card player1TopCard = player1Pile.getTopCard();
        Card player2TopCard = player2Pile.getTopCard();
        //todo
        //jedna karta odkryta/ kolejny gracz ma ruch iterator albo najlepiej klase playera
        if (card.getContainingPile().getPileType() == Pile.PileType.PLAYER1) {
//            if(isMoveValid(player1Pile, player2Pile)) {
//                player1TopCard.moveToPile(player1Fight);
//                player2TopCard.moveToPile(player1Fight);
//            } else {
//                player1TopCard.moveToPile(player2Fight);
//                player2TopCard.moveToPile(player2Fight);
//            }
            if(card.isFaceDown()) {
                card.flip();
            }

        }else if (card.getContainingPile().getPileType() == Pile.PileType.PLAYER2) {
//            if(isMoveValid(player1Pile, player2Pile)) {
//                player1TopCard.moveToPile(player1Fight);
//                player2TopCard.moveToPile(player1Fight);
//            } else {
//                player1TopCard.moveToPile(player2Fight);
//                player2TopCard.moveToPile(player2Fight);
//            }
            if(card.isFaceDown()) {
                card.flip();
            }
        }

    };
    


    private EventHandler<MouseEvent> stockReverseCardsHandler = e -> {
        refillStockFromDiscard();
    };


    private boolean isMoveValid(Pile player1Pile, Pile player2Pile) {
        Card player1TopCard = player1Pile.getTopCard();
        Card player2TopCard = player2Pile.getTopCard();

        if (!player2TopCard.isFaceDown() && player1TopCard.getRank() > player2TopCard.getRank()
                && !player2TopCard.isFaceDown()  ) {

            return true;
        } else if (player2TopCard.getRank() > player1TopCard.getRank() && !player1TopCard.isFaceDown()
                && !player2TopCard.isFaceDown()   ) {


            return false;

        }
        return false;
        //return true;
    }




    public Game() {
        deck = Card.createNewDeck();
        initPiles(2, this.names);
        dealCards();
    }

    public void addMouseEventHandlers(Card card) {
        card.setOnMouseClicked(onMouseClickedHandler);
        card.setOnMouseMoved(onMouseMovedHandler);


    }



    public void refillStockFromDiscard() {
        //todo
        //Iterator<Card> cardIterator = discardPile.getCards().iterator();
        //Collections.reverse(discardPile.getCards());
//        while (cardIterator.hasNext()) {
//            Card card = cardIterator.next();
//            card.flip();
//            player1Pile.addCard(card);
//
//        }
        //discardPile.clear();
        System.out.println("Stock refilled from discard pile.");

    }

//    private boolean isMoveValid(Card card, Pile destPile) {

//        Card topCard = destPile.getTopCard();
//
//        if (fightPiles.contains(destPile)) {
//            return isMoveValidInTableau(card, topCard, destPile);
//        } else if (foundationPiles.contains(destPile)) {
//            return isMoveValidInFoundation(card, topCard, destPile);
//        }
//        return false;
//        //return true;
//    }



    private void initPiles(int howManyPlayers, String[] names) {
        //todo
        initPlayerPiles(howManyPlayers, names);

        }

    private void initPlayerPiles(int howManyPlayers, String[] names){
        //todo

        for (int i = 0; i < howManyPlayers; i++) {
            String player = "PLAYER" + i;
            if (howManyPlayers == 2) {
                player1Pile = new Pile(Pile.PileType.player, names[i], PLAYER_GAP);
                player1Pile.setBlurredBackground();
                player1Pile.setLayoutX(95);
                player1Pile.setLayoutY(20);
                //player1Pile.setOnMouseClicked(stockReverseCardsHandler);
                getChildren().add(player1Pile);

                player2Pile = new Pile(Pile.PileType.PLAYER2, names[i], PLAYER_GAP);
                player2Pile.setBlurredBackground();
                player2Pile.setLayoutX(1150);
                player2Pile.setLayoutY(20);
                //player2Pile.setOnMouseClicked(stockReverseCardsHandler);
                getChildren().add(player2Pile);

                player1Fight = new Pile(Pile.PileType.FIGHTPLAYER1, names[i] + " Fight pile", FIGHT_GAP);
                player1Fight.setBlurredBackground();
                player1Fight.setLayoutX(450);
                player1Fight.setLayoutY(275);
                getChildren().add(player1Fight);

                player2Fight = new Pile(Pile.PileType.FIGHTPLAYER2, names[i] + " Fight pile", FIGHT_GAP);
                player2Fight.setBlurredBackground();
                player2Fight.setLayoutX(800);
                player2Fight.setLayoutY(275);
                getChildren().add(player2Fight);
            } else if (howManyPlayers == 3){
                //todo
            } else if (howManyPlayers == 4) {
                //todo
            } else {
                System.out.println("Something went wrong!");
            }
        }



    }
    //}

    public void dealCards() {
        Collections.shuffle(deck);
        Iterator<Card> deckIterator = deck.iterator();
        dealCardsToPlayers(deckIterator);
        //dealCardsToPlayer2(deckIterator);

        //TODO


    }

    private void dealCardsToPlayers(Iterator<Card> deckIterator) {
        int amountOfCards = 52;
        int howManyPlayers = 2;
            for (int i = 0; i < amountOfCards / howManyPlayers; i++) {
                Card card = deckIterator.next();
                addMouseEventHandlers(card);
                player1Pile.addCard(card);
                //player1Pile.getTopCard().flip();
                getChildren().add(card);
            }

        deckIterator.forEachRemaining(card -> {
            player2Pile.addCard(card);
            //player2Pile.getTopCard().flip();
            addMouseEventHandlers(card);
            getChildren().add(card);
        });


    }
    //}



    public void setTableBackground(Image tableBackground) {
        setBackground(new Background(new BackgroundImage(tableBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

        }
    };

}
