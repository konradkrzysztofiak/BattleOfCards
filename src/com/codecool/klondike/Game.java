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
    private Pile player2Pile;
    private Pile player1Fight;
    private Pile player2Fight;


    private double dragStartX, dragStartY;
    private List<Card> draggedCards = FXCollections.observableArrayList();

    private static double PLAYER_GAP = 1;
    private static double FOUNDATION_GAP = 1;
    private static double FIGHT_GAP = 1;


    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
        Card card = (Card) e.getSource();
        if (card.getContainingPile().getPileType() == Pile.PileType.PLAYER1) {

            card.moveToPile(player1Fight);
            card.flip();
            System.out.println(card);
            System.out.println(player2Pile.numOfCards());
            System.out.println(player1Fight.numOfCards());
            System.out.println(Pile.PileType.PLAYER2);

        }else if (card.getContainingPile().getPileType() == Pile.PileType.PLAYER2) {
            System.out.println("ELO");
            card.moveToPile(player2Fight);
            card.flip();
            System.out.println(card);
        }
    };

    private EventHandler<MouseEvent> onMouseMovedHandler = e -> {
        Card card = (Card) e.getSource();
//            if (card.getContainingPile().getPileType() == Pile.PileType.PLAYER2) {
//                System.out.println("elo2222");Found
//            } else if (card.getContainingPile().getPileType() == Pile.PileType.PLAYER1) {
//                System.out.println("elo1111");
//            }

    };


    private EventHandler<MouseEvent> stockReverseCardsHandler = e -> {
        refillStockFromDiscard();
    };





    private void updatePiles(Pile activePile, int amount) {
        System.out.println(activePile.getCards().size());
        if (activePile.getCards().size() > 1 && activePile.getCard(amount - 1).isFaceDown()) {
            activePile.getCard(amount - 1).flip();
            System.out.println("elo");
        } else {
            System.out.println("brak kart");
        }
    }

    private void moveCard(Card card, double offsetX, double offsetY) {
        draggedCards.add(card);

        card.getDropShadow().setRadius(20);
        card.getDropShadow().setOffsetX(10);
        card.getDropShadow().setOffsetY(10);

        card.toFront();
        card.setTranslateX(offsetX);
        card.setTranslateY(offsetY);
    }





    public Game() {
        deck = Card.createNewDeck();
        initPiles();
        dealCards();
    }

    public void addMouseEventHandlers(Card card) {
        card.setOnMouseClicked(onMouseClickedHandler);
        card.setOnMouseMoved(onMouseMovedHandler);


    }



    public void refillStockFromDiscard() {
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

    private boolean isMoveValidInFoundation(Card card, Card topCard, Pile destPile) {
        if (card.getRank() == 1 && destPile.numOfCards() == 0) {
            return true;
        } else if (card.getRank() != 1 && destPile.numOfCards() == 0) {
            return false;
        }
        return (Card.checkIsCardLower(topCard, card) && Card.isSameSuit(card, topCard));
    }

    private boolean isMoveValidInTableau(Card card, Card topCard, Pile destPile) {
        if (card.getRank() == 13 && destPile.numOfCards() == 0) {
            return true;
        } else if (card.getRank() != 13 && destPile.numOfCards() == 0) {
            return false;
        }
        return (Card.checkIsCardLower(card, topCard) && Card.isOppositeColor(card, topCard));
    }



    private boolean isOverPile(Card card, Pile pile) {

        if (pile.isEmpty())
            return card.getBoundsInParent().intersects(pile.getBoundsInParent());
        else
            return card.getBoundsInParent().intersects(pile.getTopCard().getBoundsInParent());
    }


    private void initPiles() {
        //todo
        //automatic init piles for 2 or more players
        //refactor
        //pile for cards for players
        player1Pile = new Pile(Pile.PileType.PLAYER1, "Player1", PLAYER_GAP);
        player1Pile.setBlurredBackground();
        player1Pile.setLayoutX(95);
        player1Pile.setLayoutY(20);
        //player1Pile.setOnMouseClicked(stockReverseCardsHandler);
        getChildren().add(player1Pile);

        player2Pile = new Pile(Pile.PileType.PLAYER2, "Player2", PLAYER_GAP);
        player2Pile.setBlurredBackground();
        player2Pile.setLayoutX(1150);
        player2Pile.setLayoutY(20);
        //player2Pile.setOnMouseClicked(stockReverseCardsHandler);
        getChildren().add(player2Pile);

        player1Fight = new Pile(Pile.PileType.FIGHTPLAYER1, "Tableau ", FIGHT_GAP);
        player1Fight.setBlurredBackground();
        player1Fight.setLayoutX(450);
        player1Fight.setLayoutY(275);
        getChildren().add(player1Fight);

        player2Fight = new Pile(Pile.PileType.FIGHTPLAYER2, "Tableau ", FIGHT_GAP);
        player2Fight.setBlurredBackground();
        player2Fight.setLayoutX(800);
        player2Fight.setLayoutY(275);
        getChildren().add(player2Fight);

        }
    //}

    public void dealCards() {
        Collections.shuffle(deck);
        Iterator<Card> deckIterator = deck.iterator();
        dealCardsToPlayer1(deckIterator);
        dealCardsToPlayer2(deckIterator);

        //TODO


    }

    private void dealCardsToPlayer1(Iterator<Card> deckIterator) {
        int amountOfCards = 26;
        //for (Pile tableauPile : tableauPiles) {
            for (int i = 0; i < amountOfCards; i++) {
                Card card = deckIterator.next();
                addMouseEventHandlers(card);
                player1Pile.addCard(card);
                getChildren().add(card);
            }
            //player2Pile.getTopCard().flip();
            //amountOfCards++;
        }
    //}

    private void dealCardsToPlayer2(Iterator<Card> deckIterator) {
        deckIterator.forEachRemaining(card -> {
            player2Pile.addCard(card);
            addMouseEventHandlers(card);
            getChildren().add(card);
        });
    }


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
