package model.watchers.monsters;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;

public class TexChangerWatcher extends Watcher {
    MarshmallonHolyWatcher secondaryWatcher;

    public TexChangerWatcher(CardInUse ownerOfWatcher) {
        super(ownerOfWatcher);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (secondaryWatcher == null) {
                secondaryWatcher = new MarshmallonHolyWatcher(ownerOfWatcher);
                secondaryWatcher.watch(theCard, cardState, duelMenuController);
                //summon a without effect cyberse monster
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }

    @Override
    public void update(Phase newPhase) {
        if (newPhase == Phase.END_RIVAL) {
            secondaryWatcher = null;
        }
    }
}
