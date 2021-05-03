package model;

import model.card.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cardsInHand;

    public Hand() {
        this.cardsInHand = new ArrayList<>();
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(Card card) {
        return cardsInHand.contains(card);
    }

    public void addCard(Card card) {

    }

    public void removeCard(Card card) {

    }

    @Override
    public String toString() {
        return "Hand{}";
    }
}
