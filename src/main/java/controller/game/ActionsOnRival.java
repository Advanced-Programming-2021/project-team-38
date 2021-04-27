package controller.game;

import model.Player;
import model.card.Card;

class ActionsOnRival {
    private Player currentPlayer;
    private Player otherPlayer;


    ActionsOnRival(Player currentPlayer, Player otherPlayer) {

    }

    public String showGraveyard() {
        return null;
    }

    public Card selectedCard() {
        return null;
    }

    public void deselectedCard() {

    }

    public String activateTrapOrSpellAtRivalTurn() {
        return null;
    }

    private boolean canOtherPlayActivateEffect() {
        return true;
    }
}
