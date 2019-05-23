package com.codecool.klondike;

import com.codecool.klondike.Dao.CardsDaoXml;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.*;

public class Card extends ImageView {

    private Suit suit;
    private Rank rank;
    private boolean faceDown;

    private int army;
    private int land;
    private int money;
    private int influence;
    private String name;

    private Image backFace;
    private Image frontFace;
    private Pile containingPile;
    private DropShadow dropShadow;

    static Image cardBackImage;
    private static final Map<String, Image> cardFaceImages = new HashMap<>();
    public static final int WIDTH = 200;
    public static final int HEIGHT = 280;

    private CardsDaoXml cardsDaoXml = new CardsDaoXml();

    public Card(int army, int land, int money, int influence, String name) {
        this.army = army;
        this.land = land;
        this.money = money;
        this.influence = influence;
        this.name = name;
        this.faceDown = true;
        this.dropShadow = new DropShadow(2, Color.gray(0, 0.75));
        backFace = cardBackImage;
        frontFace = cardFaceImages.get(name);
        setImage(faceDown ? backFace : frontFace);
        setEffect(dropShadow);
    }

    public Card() {
        cardsDaoXml.addCards();

    }

    public int getSuit() {
        return suit.getSuit();
    }

    public int getRank() {
        return rank.getValue();
    }

    public int getArmy() {
        return army;
    }

    public int getLand() {
        return land;
    }

    public int getMoney() {
        return money;
    }

    public int getInfluence() {
        return influence;
    }

    public String getName() {
        return name;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public String getShortName(int index) {
        return cardsDaoXml.getCard(index).getName();
    }

    public DropShadow getDropShadow() {
        return dropShadow;
    }

    public Pile getContainingPile() {
        return containingPile;
    }

    public void setContainingPile(Pile containingPile) {
        this.containingPile = containingPile;
    }

    public void moveToPile(Pile destPile) {
        this.getContainingPile().getCards().remove(this);
        destPile.addCard(this);
    }

    public void flip() {
        faceDown = !faceDown;
        setImage(faceDown ? backFace : frontFace);
    }

    @Override
    public String toString() {
        return "The " + "Rank " + rank + " of " + "Suit " + suit;
    }

    public static boolean isOppositeColor(Card card1, Card card2) {
        //TODO
        if (card1.suit.getColor() != card2.suit.getColor())
            return true;
        else
            return false;
    }

    public static boolean isSameSuit(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit();
    }

    public static List createNewDeck() {
        CardsDaoXml cardsDaoXml = new CardsDaoXml();
        cardsDaoXml.addCards();
        return cardsDaoXml.getCards();
    }

    public static boolean checkIsCardLower(Card lower, Card higher) {
        return (higher.getRank() == lower.getRank() + 1);
    }

    public void loadCardImages() {
        cardBackImage = new Image("card_images/card_back.png");
        System.out.println(cardsDaoXml.getCards().size());
        for (int i = 0; i < cardsDaoXml.getCards().size(); i++) {
            String cardName = cardsDaoXml.getCard(i).getName();
            String cardId = cardsDaoXml.getCard(i).getName();
            String imageFileName = "card_images/" + cardName + ".png";
            cardFaceImages.put(cardId, new Image(imageFileName));
            System.out.println(cardName);
        }
    }
}
