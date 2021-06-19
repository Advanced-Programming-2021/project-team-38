package model;

import exceptions.InvalidSelection;
import model.card.Card;

import java.util.ArrayList;

public class GraveYard {
    private ArrayList<Card> cardsInGraveYard;

    public ArrayList<Card> getCardsInGraveYard() {
        return cardsInGraveYard;
    }

    {
        cardsInGraveYard = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null)
            cardsInGraveYard.add(card);
    }

    public void removeCard(Card card) {
        cardsInGraveYard.remove(card);
    }

    public void showMonsters() {

    }

    //card index is between 1 and the size!
    public Card getCard(int cardIndex) throws InvalidSelection {
        if (cardIndex < 1 || cardIndex > this.cardsInGraveYard.size()) throw new InvalidSelection();
        return this.cardsInGraveYard.get(cardIndex - 1);
    }

    @Override
    public String toString() {
        String showGraveyard = new String();
        for (Card card : cardsInGraveYard) {
            showGraveyard.concat(card.toString());
        }
        return showGraveyard;
    }

}

