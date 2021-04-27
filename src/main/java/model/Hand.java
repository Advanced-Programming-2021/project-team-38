package model;

import model.card.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cardsInHand;

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
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
