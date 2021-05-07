package model;

import model.card.Card;

public class Cell {
    private Card card = null;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void removeCard() {
        this.card = null;
    }
}
