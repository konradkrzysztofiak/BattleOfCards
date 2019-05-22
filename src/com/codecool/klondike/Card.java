package com.codecool.klondike;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.*;

public class Card extends ImageView {

    private Suit suit;
    private Rank rank;
    private boolean faceDown;

    private Image backFace;
    private Image frontFace;
    private Pile containingPile;
    private DropShadow dropShadow;

    static Image cardBackImage;
    private static final Map<String, Image> cardFaceImages = new HashMap<>();
    public static final int WIDTH = 200;
    public static final int HEIGHT = 280;

    public Card(Suit suit, Rank rank, boolean faceDown) {
        this.suit = suit;
        this.rank = rank;
        this.faceDown = faceDown;
        this.dropShadow = new DropShadow(2, Color.gray(0, 0.75));
        backFace = cardBackImage;
        System.out.println(getShortName());
        frontFace = cardFaceImages.get(getShortName());
        setImage(faceDown ? backFace : frontFace);
        setEffect(dropShadow);
    }

    public int getSuit() {
        return suit.getSuit();
    }

    public int getRank() {
        return rank.getValue();
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public String getShortName() {
        return "S" + suit.getName() + "R" + rank.getValue();
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
        if(card1.suit.getColor() != card2.suit.getColor())
            return true;
        else
            return false;
    }

    public static boolean isSameSuit(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit();
    }

    public static List<Card> createNewDeck() {
        List<Card> result = new ArrayList<>();

        for(Suit s : Suit.values())
        {
            for (Rank r : Rank.values())
            {
                result.add(new Card(s, r, true));
            }
        }
        return result;
    }
    public static boolean checkIsCardLower(Card lower, Card higher) {
        return (higher.getRank() == lower.getRank() + 1);
    }

    public static void loadCardImages() {
        cardBackImage = new Image("card_images/card_back.png");

        for(Suit s : Suit.values())
        {
            for (Rank r : Rank.values())
            {
                String cardName = s.getName() + r.getValue();
                String cardId = "S" + s.getName() + "R" + r.getValue();
                String imageFileName = "card_images/" + cardName + ".png";
                cardFaceImages.put(cardId, new Image(imageFileName));
            }
        }
    }

}
