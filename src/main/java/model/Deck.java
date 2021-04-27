package model;

import model.card.Card;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> mainCards;
    private ArrayList<Card> sideCards;
    private User owner;
    private boolean isNumOfCardsValid;

    public Deck(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.mainCards = new ArrayList<>();
        this.sideCards = new ArrayList<>();
        isNumOfCardsValid = false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getMainCards() {
        return mainCards;
    }

    public ArrayList<Card> getSideCards() {
        return sideCards;
    }

    public User getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMainCards(ArrayList<Card> mainCards) {
        this.mainCards = mainCards;
    }

    public void setSideCards(ArrayList<Card> sideCards) {
        this.sideCards = sideCards;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setNumOfCardsValid(boolean numOfCardsValid) {
        isNumOfCardsValid = numOfCardsValid;
    }

    public boolean isNumOfCardsValid() {
        return isNumOfCardsValid;
    }

    private boolean isPossibleToAddCard(Card card) {
        return false;
    }

    public void addCardToMain(Card card) {

    }

    public void addCardToSide(Card card) {

    }
}
