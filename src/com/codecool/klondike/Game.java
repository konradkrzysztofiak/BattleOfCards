package com.codecool.klondike;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Game extends Pane {

    private List deck;
    private Card player1TopCard;
    private Card player2TopCard;
    private Player player1;
    private Player player2;
    private List<Pile> playersPiles = FXCollections.observableArrayList();
    private List<Pile> wonCardsPiles = FXCollections.observableArrayList();

    private Scene gameScene;
    private Label labelPlayer1;
    private Label labelPlayer2;
    private Label labelColorPlayer1;
    private Label labelColorPlayer2;

    public List<String> cardInfoPlayer1 = new ArrayList<>();
    public List<String> cardInfoPlayer2 = new ArrayList<>();

    private String stats = null;
    private int lastTurnPlayerId;


    public Game() {
        deck = Card.createNewDeck();
        String[] names = {"Eugeniusz", "Mieczyslaw"};
        initPiles(2, names);
        dealCards();
        player1.setTurn(true);
    }


    public void fillCardInfo()
    {
        if(player1.hasTurn()) {
            cardInfoPlayer1.add(String.valueOf(player1TopCard.getStrength()));
            cardInfoPlayer1.add(String.valueOf(player1TopCard.getSkills()));
            cardInfoPlayer1.add(String.valueOf(player1TopCard.getMoney()));
            cardInfoPlayer1.add(String.valueOf(player1TopCard.getInfluence()));
            cardInfoPlayer1.add(String.valueOf(player1TopCard.getCompanion()));
        } else {
            cardInfoPlayer2.add(String.valueOf(player2TopCard.getStrength()));
            cardInfoPlayer2.add(String.valueOf(player2TopCard.getSkills()));
            cardInfoPlayer2.add(String.valueOf(player2TopCard.getMoney()));
            cardInfoPlayer2.add(String.valueOf(player2TopCard.getInfluence()));
            cardInfoPlayer2.add(String.valueOf(player2TopCard.getCompanion()));
        }
    }

    public void setGameScene(Scene gameScene)
    {
        this.gameScene = gameScene;
    }

    public void addMouseEventHandlers(Card card) {
        card.setOnMouseClicked(onMouseClickedHandler);
    }

    private void changeColor(){
        if(player1.hasTurn())
        {
            System.out.println("change color");
            Main.playerTurnColor(labelColorPlayer2, "#FFFFFF");
            Main.playerTurnColor(labelColorPlayer1, "#24b600");

        } else if (player2.hasTurn()) {
            System.out.println("change color");
            Main.playerTurnColor(labelColorPlayer1, "#FFFFFF");
            Main.playerTurnColor(labelColorPlayer2, "#24b600");
        }
    }

    private EventHandler<KeyEvent> handler = event -> {

        switch (event.getCode()) {
            case Q:
                if(!player1.hasTurn() && !player2.hasTurn()) {
                    System.out.println("Strength");
                    stats = "Strength";
                    if (lastTurnPlayerId == player1.id()) {
                        player2.setTurn(true);
                        System.out.println("elo");
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

                List zeroStats = getList();
                if (stats != null) {
                    if (!player1TopCard.isFaceDown() && !player2TopCard.isFaceDown()){
                        checkWinnerOfRound(player1TopCard, player2TopCard, stats);
                        stats = null;
                        System.out.println("po win");
                    }
                    Main.player1Label(zeroStats, labelPlayer1);
                    Main.player2Label(zeroStats, labelPlayer2);
                }
                break;
        }};

    private List getList() {
        List zeroStats = new ArrayList<>();
        zeroStats.add(0);
        zeroStats.add(0);
        zeroStats.add(0);
        zeroStats.add(0);
        zeroStats.add(0);
        return zeroStats;
    }

    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
        Card card = (Card) e.getSource();
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, handler);


        if(player1.hasTurn() && card.getContainingPile().getOwnerID() == player1.id() &&
                card.getContainingPile().getPileType() != Pile.PileType.WONCARDS) {
            player1TopCard = card.getContainingPile().getTopCard();
            cardInfoPlayer1.clear();

            fillCardInfo();
            //if(card == player1TopCard && card.isFaceDown()) {
            if (card == player1TopCard) {
                System.out.println(player1TopCard.getName());

                player1TopCard.flip();
                Main.player1Label(cardInfoPlayer1, labelPlayer1);
                player1.setTurn(false);
                player2.setTurn(false);
                this.lastTurnPlayerId = player1.id();
            }

        } else if (player2.hasTurn() && card.getContainingPile().getOwnerID() == player2.id() &&
                card.getContainingPile().getPileType() != Pile.PileType.WONCARDS) {

            player2TopCard = card.getContainingPile().getTopCard();
            cardInfoPlayer2.clear();
            fillCardInfo();
            //if(card == player2TopCard && card.isFaceDown()) {
            if (card == player2TopCard) {
                player2TopCard.flip();
                Main.player2Label(cardInfoPlayer2, labelPlayer2);
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

        Iterator<Card> cardIterator = previousPile.getCards().iterator();
        Collections.reverse(previousPile.getCards());
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
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


        for (int i = 0; i < howManyPlayers; i++) {
            int[] coordinates = {95, 985};
            int[] wonCardsPilesX = {320, 760};
            if (howManyPlayers == 2) {
                // PLAYER PILE
                double PLAYER_GAP = 1;
                Pile playerPile = new Pile(Pile.PileType.PLAYERS, names[i], PLAYER_GAP);
                playerPile.setOwnerID(i);
                playerPile.setBlurredBackground();
                playerPile.setLayoutX(coordinates[i]);
                playerPile.setLayoutY(35);
                //playerPile.setOnMouseClicked(stockReverseCardsHandler);
                playersPiles.add(playerPile);
                getChildren().add(playerPile);
                // PLAYER WON CARDS PILE
                double WONCARDS_GAP = 1;
                Pile playerWonCards = new Pile(Pile.PileType.WONCARDS, names[i], WONCARDS_GAP);
                //playerWonCards.setOwnerID(i);
                playerWonCards.setBlurredBackground();
                playerWonCards.setLayoutX(wonCardsPilesX[i]);
                playerWonCards.setLayoutY(35);
                wonCardsPiles.add(playerWonCards);
                getChildren().add(playerWonCards);
                /////////////////////////////////////////////////


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

    public void dealCards() {
        Collections.shuffle(deck);
        Iterator<Card> deckIterator = deck.iterator();
        dealCardsToPlayers(deckIterator);
    }

    private void dealCardsToPlayers(Iterator<Card> deckIterator) {
        int amountOfCards = 28;
        for (Pile playerPile : playersPiles) {
            for (int i = 0; i < amountOfCards / 2; i++) {
                Card card = deckIterator.next();
                addMouseEventHandlers(card);
                playerPile.addCard(card);
                getChildren().add(card);
            }
        }
    }

    public void setTableBackground(Image tableBackground) {
        setBackground(new Background(new BackgroundImage(tableBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

    public void setGameLabelPlayer1(Label labelPlayer1) {
        this.labelPlayer1 = labelPlayer1;
    }
    public void setGameLabelPlayer2(Label labelPlayer2) {
        this.labelPlayer2 = labelPlayer2;
    }

    public void setLabelColorPlayer1(Label labelColorPlayer1) {
        this.labelColorPlayer1 = labelColorPlayer1;
    }

    public void setLabelColorPlayer2(Label labelColorPlayer2) {
        this.labelColorPlayer2 = labelColorPlayer2;
    }
}