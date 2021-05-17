package controller.game;

import model.Deck;
import model.Hand;
import model.card.PreCard;
import view.Print;

import java.util.Collections;

public class DrawPhaseController {
    private final GamePlayController gamePlayController;
    private int numOfCardsToAdd;
    private Deck deck;
    private boolean isBeginningOfGame;
    private Hand hand;

    public DrawPhaseController(GamePlayController gamePlay, boolean isBeginningOfGame) {
        this.gamePlayController = gamePlay;
        this.deck = gamePlay.getCurrentPlayer().getDeck();
        this.isBeginningOfGame = isBeginningOfGame;
        if (isBeginningOfGame) this.numOfCardsToAdd = 5;
        else this.numOfCardsToAdd = 1;
        this.hand = gamePlay.getCurrentPlayer().getHand();
    }

    public void run() {
        Print.print("phase: draw phase"); //todo: fine?
        if (isBeginningOfGame) {
            Collections.shuffle(this.deck.getMainCards());
        }
        boolean isLost = checkLoss();
        if (isLost) {
            gamePlayController.announceWinner(true);
        } else {
            addCardsFromDeckToHand();
            activeDrawEffects();
        }

    }

    private void addCardsFromDeckToHand() {
        for (int i = 0; i < numOfCardsToAdd; i++) {
            PreCard preCard = deck.getMainCards().get(0);
            deck.getMainCards().remove(preCard);
            this.hand.addCard(preCard);
        }
    }

    public boolean checkLoss() {
        return deck.getMainCards().isEmpty();
    }


    private void activeDrawEffects() {
        //todo: what do I do with that?
    }


}
