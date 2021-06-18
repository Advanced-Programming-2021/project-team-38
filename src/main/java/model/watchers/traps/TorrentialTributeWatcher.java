package model.watchers.traps;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;

public class TorrentialTributeWatcher extends Watcher {
    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (handleChain()) {
                for (MonsterCardInUse monsterCardInUse : theCard.ownerOfCard.getBoard().getMonsterZone()) {
                    monsterCardInUse.sendToGraveYard();
                }

                //for rooye male harif
                trapHasDoneItsEffect();
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        //put on every monster card
    }
}
