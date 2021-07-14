package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public class ActivationWatcher extends Watcher {
    Watcher upperWatcher;

    public ActivationWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch, Watcher upperWatcher) {
        super(ownerOfWatcher, whoToWatch);
        this.upperWatcher = upperWatcher;
        this.speed = upperWatcher.speed;
        putWatcher(ownerOfWatcher);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                this.duelMenuController = duelMenuController;
                this.theCard = theCard;
            }
        }
    }

    @Override
    public void doWhatYouShould() {
        upperWatcher.watch(theCard, CardState.TRIGGERED, duelMenuController);
        isWatcherActivated = true;
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }

    @Override
    public void deleteWatcher() {
        super.deleteWatcher();
        isDeleted = true;
        upperWatcher.deleteWatcher();
    }
}
