package controller.game;

import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import view.messageviewing.Print;

class ActionsOnRival {
    private RoundController roundController;
    private CardInUse selectedCellRival;

    public ActionsOnRival(RoundController roundController) {
        this.roundController = roundController;
    }

    public void showGraveyard() {
        int i = 1;
        for (PreCard preCard : roundController.getRival().getBoard().getGraveYard().getCardsInGraveYard()) {
            Print.print(String.format("%d. %s", i, preCard));
            i++;
        }
        if (i == 1)
            Print.print("graveyard empty");
    }

    public MonsterCardInUse getRivalMonsterCell(int cellNo) {
        return roundController.getRivalBoard().getMonsterZone()[cellNo];
    }

    public Card selectCard() {
        return null;
    }

    public void deselectCard() {

    }

    public String activateTrapOrSpellAtRivalTurn() {
        return null;
    }

    private boolean canOtherPlayActivateEffect() {
        return true;
    }
}
