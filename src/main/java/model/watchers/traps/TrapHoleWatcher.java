package model.watchers.traps;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;

public class TrapHoleWatcher extends Watcher {
    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (cardInUseHasWatcherCondition(theCard)) {
                if (handleChain()) {
                    theCard.sendToGraveYard();
                    trapHasDoneItsEffect();
                }
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        //put on all of rival's monster cards
    }

    public boolean cardInUseHasWatcherCondition(CardInUse cardInUse) {
        return ((MonsterCardInUse) cardInUse).getAttack() >= 1000;
    }
}
