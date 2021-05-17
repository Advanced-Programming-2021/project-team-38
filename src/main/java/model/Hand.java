package model;

import exceptions.InvalidSelection;
import model.card.PreCard;

import java.util.ArrayList;

public class Hand {
    private ArrayList<PreCard> cardsInHand;

    {
        this.cardsInHand = new ArrayList<>();
    }


    public ArrayList<PreCard> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(PreCard preCard) {
        return cardsInHand.contains(preCard);
    }

    public void addCard(PreCard preCard) {
        if (cardsInHand.size() <= 5)
            this.cardsInHand.add(preCard);
        //todo: else throw something
    }

    public void removeCard(PreCard preCard) {
        this.cardsInHand.remove(preCard);
    }

    public PreCard getCardWithNumber(int index) throws InvalidSelection {
        if (index > 0 && index <= cardsInHand.size())
            return cardsInHand.get(index + 1);
        else throw new InvalidSelection();
    }

    @Override
    public String toString() {
        return "c  ".repeat(cardsInHand.size());
    }
}
