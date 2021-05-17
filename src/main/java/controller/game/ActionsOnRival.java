package controller.game;

import model.Player;
import model.card.Card;
import model.card.PreCard;
import view.messageviewing.Print;

class ActionsOnRival {
    private Player currentPlayer;
    private Player otherPlayer;


    ActionsOnRival(Player currentPlayer, Player otherPlayer) {

    }

    public void showGraveyard() {
        int i = 1;
        for (PreCard preCard : otherPlayer.getBoard().getGraveYard().getCardsInGraveYard()) {
            Print.print(String.format("%d. %s", i, preCard));
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
