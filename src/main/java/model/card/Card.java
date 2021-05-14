package model.card;

import model.Player;
import model.card.monster.CardAttribute;

import java.util.ArrayList;

public class Card {
    protected String name;
    protected int price;
    protected CardAttribute attribute;
    protected String description;
    protected int cardNumber;
    protected String type;
    protected Player owner;
    protected int level;
    protected static ArrayList<Card> allCards;


    public static Card getCardByName(String cardName) {
        return null;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public CardAttribute getAttribute() {
        return attribute;
    }

    public String getDescription() {
        return description;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getType() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }

    protected void receiveAttack(Card attackerCard) {

    }

}
