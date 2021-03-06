package controller.game;

import model.Deck;
import model.Enums.RoundResult;
import model.Hand;
import model.card.Card;
import model.card.PreCard;

import java.util.Collections;

public class DrawPhaseController {
    public static boolean canDraw = true;
    private final RoundController roundController;
    private final int numOfCardsToAdd;
    private final Deck deck;
    private final boolean isBeginningOfGame;
    private final Hand hand;

    public DrawPhaseController(RoundController gamePlay, boolean isBeginningOfGame) {
        this.roundController = gamePlay;
        this.deck = gamePlay.getCurrentPlayer().getDeck();
        this.isBeginningOfGame = isBeginningOfGame;
        if (isBeginningOfGame) this.numOfCardsToAdd = 5;
        else this.numOfCardsToAdd = 1;
        this.hand = gamePlay.getCurrentPlayer().getHand();
    }

    public void run() {
        if (isBeginningOfGame) {
            Collections.shuffle(this.deck.getMainCards());
        }
        boolean isLost = checkLoss();
        if (isLost) {
            roundController.setRoundWinner(RoundResult.RIVAL_WON);
        } else {
            addCardsFromDeckToHand();
            activeDrawEffects();
        }
    }

    private void addCardsFromDeckToHand() {
        if (canDraw) {
            for (int i = 0; i < numOfCardsToAdd; i++) {
                if (checkLoss()) {
                    roundController.setRoundWinner(RoundResult.RIVAL_WON);
                    return;
                }
                PreCard preCard = deck.getMainCards().remove(0);
                Card card = preCard.newCard();
                this.hand.addCard(card);
            }
            roundController.updateBoards();
        }
    }

    public boolean checkLoss() {
        return deck.getMainCards().isEmpty();
    }


    private void activeDrawEffects() {
        //todo: what do I do with that?
    }


}
