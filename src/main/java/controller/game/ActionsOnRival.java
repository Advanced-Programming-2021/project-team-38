package controller.game;

import model.Player;
import model.card.Card;
import view.messageviewing.Print;

class ActionsOnRival {
    private Player currentPlayer;
    private Player otherPlayer;


    ActionsOnRival(Player currentPlayer, Player otherPlayer) {

    }

    public void showGraveyard() {
        int i = 1;
        for (Card card : otherPlayer.getBoard().getGraveYard().getCardsInGraveYard()) {
            Print.print(String.format("%d. %s:%s\n", i, card.getName(), card.getDescription()));
            i++;
        }
        if (i==1)
            Print.print("graveyard empty");
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
