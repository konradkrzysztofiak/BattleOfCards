package com.codecool.klondike.Dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import com.codecool.klondike.Card;

public class CardsDaoXml implements CardsDao {

    private List<Card> cards;

    public CardsDaoXml() {
        cards = new ArrayList<>();
    }

    @Override
    public List getCards() {
        return cards;
    }

    @Override
    public Card getCard(int index) {
        return cards.get(index);
    }

    @Override
    public void addCards() {
        NodeList nodeList = getCardParameters();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Card card = new Card(
                        Integer.parseInt(element.getElementsByTagName("Strength").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Skills").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Money").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Influence").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Companion").item(0).getTextContent()),
                        element.getElementsByTagName("CardName").item(0).getTextContent()
                );
                cards.add(card);
            }
        }
    }

    @Override
    public NodeList getCardParameters() {
        XMLParserer xmlParserer = new XMLParserer();
        xmlParserer.loadXmlDoc("resources/Xml/XmlCards.xml");
        Document document = xmlParserer.getDocument();
        return document.getElementsByTagName("Card");

    }
}
