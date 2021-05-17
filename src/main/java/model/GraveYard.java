package model;

import model.card.Card;
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

    public void removeCard(Card card){
        preCardsInGraveYard.remove(card);
    }

    public void showMonsters(){

    }

    @Override
    public String toString()
    {
        String showGraveyard=new String();
        for (PreCard preCard : preCardsInGraveYard) {
            showGraveyard.concat(preCard.toString()) ;
        }
        return showGraveyard;
    }
}

