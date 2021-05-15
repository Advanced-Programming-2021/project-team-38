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

    }

    public void showMonsters(){

    }

    public void selectCard(String cardAddress){

    }

    public void deselectCard(String cardAddress){

    }

    @Override
    public String toString() {
        return "GraveYard{}";
    }
}

