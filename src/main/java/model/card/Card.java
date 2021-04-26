package model.card;

import model.Player;

import java.util.ArrayList;

public class Card {
    String name;
    int price;
    CardAttribute attribute;
    String description;
    protected int cardNumber;
    protected String type;
    protected Player owner;
    private static ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    public Card(){

    }

    public static Card getCardByName(String cardName){
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

    protected void receiveAttack(String cardName){

    }
}
