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

    private Pile stockPile;
    private Pile discardPile;
    private List<Pile> foundationPiles = FXCollections.observableArrayList();
    private List<Pile> tableauPiles = FXCollections.observableArrayList();
    private  int pilecounter;

    private double dragStartX, dragStartY;
    private List<Card> draggedCards = FXCollections.observableArrayList();

    private static double STOCK_GAP = 1;
    private static double FOUNDATION_GAP = 0;
    private static double TABLEAU_GAP = 30;


    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
        Card card = (Card) e.getSource();
        if (card.getContainingPile().getPileType() == Pile.PileType.STOCK) {
            card.moveToPile(discardPile);
            card.flip();
            card.setMouseTransparent(false);
            System.out.println("Placed " + card + " to the waste.");
        } else if (card.getContainingPile().getPileType() == Pile.PileType.TABLEAU && card.getContainingPile().getTopCard() == card && card.isFaceDown()) {
            card.flip();
            card.setMouseTransparent(false);
        }
    };

    private EventHandler<MouseEvent> onMouseMovedHandler = e -> {

    };


    private EventHandler<MouseEvent> stockReverseCardsHandler = e -> {
        refillStockFromDiscard();
    };

    private EventHandler<MouseEvent> onMousePressedHandler = e -> {
        dragStartX = e.getSceneX();
        dragStartY = e.getSceneY();
    };

    private EventHandler<MouseEvent> onMouseDraggedHandler = e -> {
        Card card = (Card) e.getSource();
        Pile activePile = card.getContainingPile();
        if (activePile.getPileType() == Pile.PileType.STOCK)
            return;
        double offsetX = e.getSceneX() - dragStartX;
        double offsetY = e.getSceneY() - dragStartY;

        draggedCards.clear();
        int cardsAmount = activePile.getCards().indexOf(card);
        for (int i = cardsAmount; i < activePile.getCards().size(); i++) {
            Card cardd = activePile.getCards().get(i);
            if (!cardd.isFaceDown() && activePile.getPileType() == Pile.PileType.TABLEAU) {
                moveCard(cardd, offsetX, offsetY);
            } else if (activePile.getPileType() == Pile.PileType.DISCARD) {
                draggedCards.clear();
                moveCard(cardd, offsetX, offsetY);
                ;
            } else if (activePile.getPileType() == Pile.PileType.FOUNDATION) {
                draggedCards.clear();
                moveCard(cardd, offsetX, offsetY);

            }
        }
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

    private EventHandler<MouseEvent> onMouseReleasedHandler = e -> {
        if (draggedCards.isEmpty())
            return;

        Card card = (Card) e.getSource();
        Pile activePile = card.getContainingPile();
        int cardsAmount = activePile.getCards().indexOf(card);
        List<Pile> piles = new ArrayList<>();
        piles.addAll(tableauPiles);
        piles.addAll(foundationPiles);
        Pile pile = getValidIntersectingPile(card, piles);

        if (pile != null) {
            handleValidMove(card, pile);
            updatePiles(activePile, cardsAmount);
        } else {
            draggedCards.forEach(MouseUtil::slideBack);
            draggedCards.clear();
        }
    };

    private void updateTableauTopCard(Card card) {
        Pile activePile = card.getContainingPile();

        if (!activePile.isEmpty()) {
            if (activePile.getUnderTopCard(activePile).isFaceDown())
                activePile.getUnderTopCard(activePile).flip();
        } else {
            System.out.println("nie mozna odwrocic");
        }
    }

    public boolean isGameWon() {
        int counter = 0;
        for (Pile singlePile : foundationPiles)
            if (singlePile.isEmpty()) {
                System.out.println("pusty pile");
                counter += 0;
            } else {
                if (singlePile.getTopCard().getRank() == 13) {
                    counter += 1;
                    System.out.println("Full piles");
                }
                System.out.println("Pile counter: " + counter);
                if (counter == 4) {
                    return true;
                }
            }
        return false;
    }


    public Game() {
        deck = Card.createNewDeck();
        initPiles();
        dealCards();
    }

    public void addMouseEventHandlers(Card card) {
        card.setOnMousePressed(onMousePressedHandler);
        card.setOnMouseDragged(onMouseDraggedHandler);
        card.setOnMouseReleased(onMouseReleasedHandler);
        card.setOnMouseClicked(onMouseClickedHandler);
        //card.setOnMouseMoved(onMouseMovedHandler);
        card.setOnMouseMoved(OnMouseDragOver);

    }

    private EventHandler<MouseEvent> OnMouseDragOver = e->{
        if (isGameWon()) {
            System.out.println("Win");
        }else
            System.out.println("Notwin");

    };

    public void refillStockFromDiscard() {
        Iterator<Card> cardIterator = discardPile.getCards().iterator();
        Collections.reverse(discardPile.getCards());
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            card.flip();
            stockPile.addCard(card);

        }
        discardPile.clear();
        System.out.println("Stock refilled from discard pile.");

    }

    private boolean isMoveValid(Card card, Pile destPile) {
        Card topCard = destPile.getTopCard();

        if (tableauPiles.contains(destPile)) {
            return isMoveValidInTableau(card, topCard, destPile);
        } else if (foundationPiles.contains(destPile)) {
            return isMoveValidInFoundation(card, topCard, destPile);
        }
        return false;
        //return true;
    }

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


    private Pile getValidIntersectingPile(Card card, List<Pile> piles) {
        Pile result = null;
        for (Pile pile : piles) {
            if (!pile.equals(card.getContainingPile()) &&
                    isOverPile(card, pile) &&
                    isMoveValid(card, pile))
                result = pile;
        }
        return result;
    }

    private boolean isOverPile(Card card, Pile pile) {

        if (pile.isEmpty())
            return card.getBoundsInParent().intersects(pile.getBoundsInParent());
        else
            return card.getBoundsInParent().intersects(pile.getTopCard().getBoundsInParent());
    }

    private void handleValidMove(Card card, Pile destPile) {
        String msg = null;
        if (destPile.isEmpty()) {
            if (destPile.getPileType().equals(Pile.PileType.FOUNDATION)) {
                msg = String.format("Placed %s to the foundation.", card);
            }
            if (destPile.getPileType().equals(Pile.PileType.TABLEAU)) {
                msg = String.format("Placed %s to a new pile.", card);
            }
        } else {
            msg = String.format("Placed %s to %s.", card, destPile.getTopCard());
        }
        System.out.println(msg);
        MouseUtil.slideToDest(draggedCards, destPile);
        draggedCards.clear();
        updateTableauTopCard(card);
    }


    private void initPiles() {
        stockPile = new Pile(Pile.PileType.STOCK, "Stock", STOCK_GAP);
        stockPile.setBlurredBackground();
        stockPile.setLayoutX(95);
        stockPile.setLayoutY(20);
        stockPile.setOnMouseClicked(stockReverseCardsHandler);
        getChildren().add(stockPile);


        discardPile = new Pile(Pile.PileType.DISCARD, "Discard", STOCK_GAP);
        discardPile.setBlurredBackground();
        discardPile.setLayoutX(285);
        discardPile.setLayoutY(20);
        getChildren().add(discardPile);

        for (int i = 0; i < 4; i++) {
            Pile foundationPile = new Pile(Pile.PileType.FOUNDATION, "Foundation " + i, FOUNDATION_GAP);
            foundationPile.setBlurredBackground();
            foundationPile.setLayoutX(610 + i * 180);
            foundationPile.setLayoutY(20);
            foundationPiles.add(foundationPile);
            getChildren().add(foundationPile);
        }
        for (int i = 0; i < 7; i++) {
            Pile tableauPile = new Pile(Pile.PileType.TABLEAU, "Tableau " + i, TABLEAU_GAP);
            tableauPile.setBlurredBackground();
            tableauPile.setLayoutX(95 + i * 180);
            tableauPile.setLayoutY(275);
            tableauPiles.add(tableauPile);
            getChildren().add(tableauPile);
        }
    }

    public void dealCards() {
        Collections.shuffle(deck);
        Iterator<Card> deckIterator = deck.iterator();
        dealCardsToTableau(deckIterator);
        dealCardsToStock(deckIterator);
    }

    private void dealCardsToTableau(Iterator<Card> deckIterator) {
        int amountOfCards = 1;
        for (Pile tableauPile : tableauPiles) {
            for (int i = 0; i < amountOfCards; i++) {
                Card card = deckIterator.next();
                addMouseEventHandlers(card);
                tableauPile.addCard(card);
                getChildren().add(card);
            }
            tableauPile.getTopCard().flip();
            amountOfCards++;
        }
    }

    private void dealCardsToStock(Iterator<Card> deckIterator) {
        deckIterator.forEachRemaining(card -> {
            stockPile.addCard(card);
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
