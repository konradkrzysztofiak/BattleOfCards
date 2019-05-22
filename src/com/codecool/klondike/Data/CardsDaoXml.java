package com.codecool.klondike.Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class CardsDaoXml implements CardsDao  {
    private ArrayList<Card> cards;

    public CardsDaoXml() {
        cards = new ArrayList<>();
    }

    @Override
    public List getCards() {
        return cards;
    }

    @Override
    public Card getCard(int index) {
        Card card;
        card =cards.get(index);
        return card;
    }

    @Override
    public void addCards() {
        NodeList nodeList = getCardParameters();
        for(int i= 0 ; i<nodeList.getLength();i++){
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                Card card = new Card(
                        Integer.parseInt(element.getElementsByTagName("Army").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Lands").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Money").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("Influence").item(0).getTextContent()),
                        element.getElementsByTagName("CardName").item(0).getTextContent()
                );
                cards.add(card);
            }
        }
    }

    @Override
    public NodeList getCardParameters(){
        XMLParserer xmlParserer = new XMLParserer();
        xmlParserer.loadXmlDoc("/home/michal/IdeaProjects/BattleOfCards/src/com/codecool/klondike/Data/XmlCards.xml");
        Document document = xmlParserer.getDocument();
        return document.getElementsByTagName("Card");

    }
}
