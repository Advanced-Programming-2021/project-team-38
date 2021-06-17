package model.watchers;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;

public class TrapHoleWatcher extends Watcher{
    @Override
    public void watch(CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (handleChain()) {

            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return false;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {

    }

    public boolean cardInUseHasWatcherCondition(CardInUse cardInUse) {

    }
}
