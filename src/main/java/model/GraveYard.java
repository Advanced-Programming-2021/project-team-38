package model;

import exceptions.InvalidSelection;
import model.card.PreCard;

import java.util.ArrayList;

public class GraveYard {
    private ArrayList<PreCard> preCardsInGraveYard;

    public ArrayList<PreCard> getCardsInGraveYard() {
        return preCardsInGraveYard;
    }

    {
        preCardsInGraveYard = new ArrayList<>();
    }

    public void addCard(PreCard preCard){
        if (preCard != null)
            preCardsInGraveYard.add(preCard);
    }

    public void removeCard(PreCard preCard) {
        preCardsInGraveYard.remove(preCard);
    }

    public void showMonsters() {

    }

    public PreCard getPreCard(int cardIndex) throws InvalidSelection {
        if (cardIndex < 1 || cardIndex > this.preCardsInGraveYard.size()) throw new InvalidSelection();
        return this.preCardsInGraveYard.get(cardIndex - 1);
    }

    @Override
    public String toString() {
        String showGraveyard = new String();
        for (PreCard preCard : preCardsInGraveYard) {
            showGraveyard.concat(preCard.toString());
        }
        return showGraveyard;
    }


}

