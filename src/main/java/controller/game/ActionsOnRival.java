package controller.game;

import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import view.messageviewing.Print;

class ActionsOnRival {
    private RoundController gamePlay;
    private CardInUse selectedCellRival;

    public ActionsOnRival(RoundController gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void showGraveyard() {
        int i = 1;
        for (PreCard preCard : gamePlay.getRival().getBoard().getGraveYard().getCardsInGraveYard()) {
            Print.print(String.format("%d. %s", i, preCard));
            i++;
        }
        if (i==1)
            Print.print("graveyard empty");
    }

    public MonsterCardInUse getRivalMonsterCell(int cellNo) {
        return gamePlay.getRivalBoard().getMonsterZone()[cellNo];
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
