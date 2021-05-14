package model;

import model.card.PreCard;

import java.util.ArrayList;

public class Hand {
    private ArrayList<PreCard> cardsInHand;

    public Hand() {
        this.cardsInHand = new ArrayList<>();
    }

    public ArrayList<PreCard> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(PreCard preCard) {
        return cardsInHand.contains(preCard);
    }

    public void addCard(PreCard preCard) {
        this.cardsInHand.add(preCard);
    }

    public void removeCard(PreCard preCard) {
        this.cardsInHand.remove(preCard);
    }

    @Override
    public String toString() {
        return "Hand{}";
    }
}
