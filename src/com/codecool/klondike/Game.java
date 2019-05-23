package com.codecool.klondike;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.*;

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
    private String stats = null;
    private int lastTurnPlayerId;

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
                if(!player1.hasTurn() && !player2.hasTurn()) {
                    System.out.println("Strength");
                    stats = "Strength";
                    if (lastTurnPlayerId == player1.id()) {
                        player2.setTurn(true);
                    } else {
                        player1.setTurn(true);
                    }
                }
                break;
            case W:
                if(!player1.hasTurn() && !player2.hasTurn()) {
                System.out.println("Skills");
                stats = "Skills";
                    if (lastTurnPlayerId == player1.id()) {
                        player2.setTurn(true);
                    } else {
                        player1.setTurn(true);
                    }
                }
                break;
            case E:
                if(!player1.hasTurn() && !player2.hasTurn()) {
                System.out.println("Money");
                stats = "Money";
                    if (lastTurnPlayerId == player1.id()) {
                        player2.setTurn(true);
                    } else {
                        player1.setTurn(true);
                    }
                }
                break;
            case R:
                if(!player1.hasTurn() && !player2.hasTurn()) {
                System.out.println("Influence");
                stats = "Influence";
                    if (lastTurnPlayerId == player1.id()) {
                        player2.setTurn(true);
                    } else {
                        player1.setTurn(true);
                    }
                }
                break;
            case ENTER:

                if (stats != null) {
                    if (!player1TopCard.isFaceDown() && !player2TopCard.isFaceDown()){
                        checkWinnerOfRound(player1TopCard, player2TopCard, stats);
                        stats = null;
                        System.out.println("po win");
                    }
                }
                break;
        }
        System.out.println("po event");
    };


    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
        //todo
        Card card = (Card) e.getSource();

//        if (stats == null)
//            return;

        if (player1.hasTurn() && card.getContainingPile().getOwnerID() == player1.id()) {
            player1TopCard = card.getContainingPile().getTopCard();
            System.out.println(player1TopCard.getName());
            System.out.println("elo");
            //if(card == player1TopCard && card.isFaceDown()) {
            if (card == player1TopCard) {
                player1TopCard.flip();
                player1.setTurn(false);
                player2.setTurn(false);
                this.lastTurnPlayerId = player1.id();
            }

        } else if (player2.hasTurn() && card.getContainingPile().getOwnerID() == player2.id()) {
            player2TopCard = card.getContainingPile().getTopCard();
            //if(card == player2TopCard && card.isFaceDown()) {
            if (card == player2TopCard) {
                player2TopCard.flip();
                player2.setTurn(false);
                this.lastTurnPlayerId = player2.id();
                //player1.setTurn(true);
            }
        }
    };

    private void checkWinnerOfRound(Card player1Card, Card player2Card, String stats) {
        switch (stats) {
            case "Strength":
                if(player1Card.getStrength() > player2Card.getStrength()){
                    player1Card.moveToPile(wonCardsPiles.get(0));
                    player2Card.moveToPile(wonCardsPiles.get(0));
                    player1Card.flip();
                    player2Card.flip();
                    player1.setTurn(true);
                } else {
                    player1Card.moveToPile(wonCardsPiles.get(1));
                    player2Card.moveToPile(wonCardsPiles.get(1));
                    player1Card.flip();
                    player2Card.flip();
                    player2.setTurn(true);
                }
                break;
            case "Skills":
                if(player1Card.getSkills() > player2Card.getSkills()){
                    player1Card.moveToPile(wonCardsPiles.get(0));
                    player2Card.moveToPile(wonCardsPiles.get(0));
                    player1Card.flip();
                    player2Card.flip();
                    player1.setTurn(true);
                } else {
                    player1Card.moveToPile(wonCardsPiles.get(1));
                    player2Card.moveToPile(wonCardsPiles.get(1));
                    player1Card.flip();
                    player2Card.flip();
                    player2.setTurn(true);
                }
                break;
            case "Money":
                if(player1Card.getMoney() > player2Card.getMoney()){
                    player1Card.moveToPile(wonCardsPiles.get(0));
                    player2Card.moveToPile(wonCardsPiles.get(0));
                    player1Card.flip();
                    player2Card.flip();
                    player1.setTurn(true);
                } else {
                    player1Card.moveToPile(wonCardsPiles.get(1));
                    player2Card.moveToPile(wonCardsPiles.get(1));
                    player1Card.flip();
                    player2Card.flip();
                    player2.setTurn(true);
                }
                break;

            case "Influence":
                if(player1Card.getInfluence() > player2Card.getInfluence()){
                    player1Card.moveToPile(wonCardsPiles.get(0));
                    player2Card.moveToPile(wonCardsPiles.get(0));
                    player1Card.flip();
                    player2Card.flip();
                    player1.setTurn(true);
                } else {
                    player1Card.moveToPile(wonCardsPiles.get(1));
                    player2Card.moveToPile(wonCardsPiles.get(1));
                    player1Card.flip();
                    player2Card.flip();
                    player2.setTurn(true);
                }
                break;

        }
        return;
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
        createPlayers(howManyPlayers, names);
        initPlayerPiles(howManyPlayers, names);
    }

    private void initPlayerPiles(int howManyPlayers, String[] names) {
        //todo


        for (int i = 0; i < howManyPlayers; i++) {
            int[] coordinates = {95, 1150};
            int[] wonCardsPilesX = {320, 930};
            if (howManyPlayers == 2) {
                // PLAYER PILE
                Pile playerPile = new Pile(Pile.PileType.PLAYERS, names[i], PLAYER_GAP);
                playerPile.setOwnerID(i);
                playerPile.setBlurredBackground();
                playerPile.setLayoutX(coordinates[i]);
                playerPile.setLayoutY(20);
                //playerPile.setOnMouseClicked(stockReverseCardsHandler);
                playersPiles.add(playerPile);
                getChildren().add(playerPile);
                // PLAYER WON CARDS PILE
                Pile playerWonCards = new Pile(Pile.PileType.WONCARDS, names[i], WONCARDS_GAP);
                //playerWonCards.setOwnerID(i);
                playerWonCards.setBlurredBackground();
                playerWonCards.setLayoutX(wonCardsPilesX[i]);
                playerWonCards.setLayoutY(20);
                wonCardsPiles.add(playerWonCards);
                getChildren().add(playerWonCards);
                /////////////////////////////////////////////////

//                player2Pile = new Pile(Pile.PileType.PLAYERS, names[i], PLAYER_GAP);
//                player2Pile.setBlurredBackground();
//                player2Pile.setLayoutX(coordinates[i]);
//                player2Pile.setLayoutY(20);
//                player2Pile.setOnMouseClicked(stockReverseCardsHandler);
//                getChildren().add(player2Pile);


//                player1Fight = new Pile(Pile.PileType.WINCARDS, names[i] + " Fight pile", FIGHT_GAP);
//                player1Fight.setBlurredBackground();
//                player1Fight.setLayoutX(320);
//                player1Fight.setLayoutY(20);
//                getChildren().add(player1Fight);
//
//                player2Fight = new Pile(Pile.PileType.FIGHTPLAYER2, names[i] + " Fight pile", FIGHT_GAP);
//                player2Fight.setBlurredBackground();
//                player2Fight.setLayoutX(930);
//                player2Fight.setLayoutY(20);
//                getChildren().add(player2Fight);
            } else if (howManyPlayers == 3) {
                //todo
            } else if (howManyPlayers == 4) {
                //todo
            } else {
                System.out.println("Something went wrong!");
            }
        }


    }

    private void createPlayers(int howManyPlayers, String[] names) {
        player1 = new Player(0, 0);
        player2 = new Player(1, 0);

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
