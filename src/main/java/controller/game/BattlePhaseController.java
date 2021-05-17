package controller.game;

import model.card.*;

import java.util.ArrayList;

class BattlePhaseController {

    private Card selectedCard;
    private ArrayList<Card> chain;


    private void battleMade() {
        //new a BattleController and ...
    }

    public String run() {
        return null;
    }

    public void setChain(ArrayList<Card> chain) {
        this.chain = chain;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public ArrayList<Card> getChain() {
        return chain;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }


    private void controlDamage(Card attackReciever) {

    }

    private String declareEnd() {
        return null;
    }

    public void attackToLifePoint(int reducingScore) {

    }

    private boolean isPossibleToAttack() {
        return true;
    }

    public boolean canAddCardToChain(Card card) {
        return true;
    }

    public void addToChain(Card card) {
        chain.add(card);
    }


}
