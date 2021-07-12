package model.watchers;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;

public class ChangeOfHeartsWatcher extends Watcher{
    public ChangeOfHeartsWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController){
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
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {

    }

    @Override
    public void update(Phase newPhase) {

    }
}
