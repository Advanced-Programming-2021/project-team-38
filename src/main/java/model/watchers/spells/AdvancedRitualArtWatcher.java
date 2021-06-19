package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;

public class AdvancedRitualArtWatcher extends Watcher {
//    public AdvancedRitualArtWatcher(CardInUse ownerOfWatcher) {
//        super(ownerOfWatcher);
//        whoToWatch = WhoToWatch.MINE;
//    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                //
                isWatcherActivated = true;
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }
}
