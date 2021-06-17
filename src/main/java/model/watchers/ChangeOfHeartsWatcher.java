package model.watchers;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.watchers.watchingexceptions.CancelBattle;

public class ChangeOfHeartsWatcher extends Watcher{
    @Override
    public void watch(CardState cardState, DuelMenuController duelMenuController) throws CancelBattle {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (Watcher.addToStack(this)) { //TODO remove
                ownerOfWatcher.watchByState(CardState.ACTIVE_EFFECT);
                emptyStack();
                //do my effect
                //throw exception don't continue my for on watchers
            }
        }
    }

    @Override
    public void reNewWatch() {

    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {

    }

    @Override
    public void update(Phase newPhase) {

    }

    @Override
    public void deleteWatcher() {

    }
}
