package model;

import model.card.Card;
import model.card.monster.Monster;
import view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class GraveYard {
    private final ArrayList<Card> cardsInGraveYard;

    public ArrayList<Card> getCardsInGraveYard() {
        return cardsInGraveYard;
    }

    {
        cardsInGraveYard = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            cardsInGraveYard.add(card);
            //todo : show that it has gone to graveyard
        }
    }

    public int getNumOfMonsters() {
        int count = 0;
        for (Card card : cardsInGraveYard) {
            if (card instanceof Monster)
                count++;
        }
        return count;
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

