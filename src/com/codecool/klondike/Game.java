package com.codecool.klondike;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Game extends Pane {

    private List<Card> deck = new ArrayList<>();
    private Card player1TopCard;
    private Card player2TopCard;
    private Player player1;
    private Player player2;
    private String[] names = {"Eugeniusz", "Mieczyslaw"};
    private List<Pile> playersPiles = FXCollections.observableArrayList();
    private List<Pile> wonCardsPiles = FXCollections.observableArrayList();
    private static double PLAYER_GAP = 1;
    private static double WONCARDS_GAP = 1;

    public Game() {

        deck = Card.createNewDeck();
        initPiles(2, this.names);
        addEventHandler(KeyEvent.KEY_PRESSED, handler);
        dealCards();
        player1.setTurn(true);
    }

    public void addMouseEventHandlers(Card card) {

        card.setOnMouseClicked(onMouseClickedHandler);
        //card.setOnMouseMoved(onMouseMovedHandler);

    }

    private EventHandler<KeyEvent> handler = event -> {

        switch (event.getCode()) {
            case Q:
                System.out.println("up");
                break;
            case W:
                System.out.println("down");
                break;
            case E:
                System.out.println("left");
                break;
            case R:
                System.out.println("right");
                break;
            case ENTER:
                if (!player1TopCard.isFaceDown() && !player2TopCard.isFaceDown())
                checkWinnerOfRound(player1TopCard, player2TopCard);
                break;
        }};


    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
    //todo
        Card card = (Card) e.getSource();

        if(isMoveValid(card)) {
            //if(card == player1TopCard && card.isFaceDown()) {
            if (card == player1TopCard) {
                player1TopCard.flip();
                player1.setTurn(false);
                player2.setTurn(true);
            } else if (card == player2TopCard) {
                player2TopCard.flip();
                player2.setTurn(false);
                player1.setTurn(false);
            }
        }


    };
    private boolean isMoveValid(Card card){
        if(player1.hasTurn()) {
            player1TopCard = card.getContainingPile().getTopCard();
            return (card.getContainingPile().getOwnerID() == player1.id() &&
                    card.getContainingPile().getPileType() != Pile.PileType.WONCARDS);
        } else if (player2.hasTurn()){
            player2TopCard = card.getContainingPile().getTopCard();
            return (card.getContainingPile().getOwnerID() == player2.id() &&
                    card.getContainingPile().getPileType() != Pile.PileType.WONCARDS);
        }
        return false;

    }

    private void checkWinnerOfRound(Card player1Card, Card player2Card){
        //todo
        //if(player1Card.getRank() > player2Card.getRank()){

            player1Card.moveToPile(wonCardsPiles.get(1));
            player2Card.moveToPile(wonCardsPiles.get(1));
            player1Card.flip();
            player2Card.flip();
            System.out.println("odwracam");
            player1.setTurn(true);
            player1Card.setContainingPile(wonCardsPiles.get(1));
            player2Card.setContainingPile(wonCardsPiles.get(1));
       // } else {
//            player1Card.moveToPile(playersPiles.get(2));
//            player2Card.moveToPile(playersPiles.get(2));
//            player1Card.flip();
//            player2Card.flip();

       // }

    }


    public void refillStockFromDiscard(Pile activePile, Pile previousPile) {
        //todo
        Iterator<Card> cardIterator = previousPile.getCards().iterator();
        Collections.reverse(previousPile.getCards());
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            //card.flip();
            activePile.addCard(card);
            System.out.println("Stock refilled from discard pile.");

        }
        previousPile.clear();


    }

    private void initPiles(int howManyPlayers, String[] names) {
        createPlayers(howManyPlayers,names);
        initPlayerPiles(howManyPlayers, names);
        }

    private void initPlayerPiles(int howManyPlayers, String[] names){
        //todo


        for (int i = 0; i < howManyPlayers; i++) {
            int[] coordinates = {95, 985};
            int[] wonCardsPilesX = {320, 760};
            if (howManyPlayers == 2) {
                // PLAYER PILE
                Pile playerPile = new Pile(Pile.PileType.PLAYERS, names[i], PLAYER_GAP);
                playerPile.setOwnerID(i);
                playerPile.setBlurredBackground();
                playerPile.setLayoutX(coordinates[i]);
                playerPile.setLayoutY(35);
                //playerPile.setOnMouseClicked(stockReverseCardsHandler);
                playersPiles.add(playerPile);
                getChildren().add(playerPile);
                // PLAYER WON CARDS PILE
                Pile playerWonCards = new Pile(Pile.PileType.WONCARDS, names[i], WONCARDS_GAP);
                //playerWonCards.setOwnerID(i);
                playerWonCards.setBlurredBackground();
                playerWonCards.setLayoutX(wonCardsPilesX[i]);
                playerWonCards.setLayoutY(35);
                wonCardsPiles.add(playerWonCards);
                getChildren().add(playerWonCards);
                /////////////////////////////////////////////////

            } else if (howManyPlayers == 3){
                //todo
            } else if (howManyPlayers == 4) {
                //todo
            } else {
                System.out.println("Something went wrong!");
            }
        }



    }
    private void createPlayers(int howManyPlayers, String[] names){
        player1 = new Player(0,0);
        player2 = new Player(1,0);

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
        int amountOfCards = 28;
        int howManyPlayers = 2;
        for (Pile playerPile : playersPiles) {
            for (int i = 0; i < amountOfCards / 2; i++) {
                Card card = deckIterator.next();
                addMouseEventHandlers(card);
                playerPile.addCard(card);
                //player1Pile.getTopCard().flip();
                getChildren().add(card);
            }




    }

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
