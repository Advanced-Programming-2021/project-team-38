package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.ZoneName;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

//disposable
public class DestroyAllWatcher extends Watcher {

    ZoneName zoneAffected;

    public DestroyAllWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch, ZoneName zone) {
        super(ownerOfWatcher, whoToWatch);
        zoneAffected = zone;
        isDisposable = true;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                watchTheFieldAffected();
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

    public void watchTheFieldAffected() {
        CardInUse[] toBeDestroyed = theTargetCells(zoneAffected);
        for (CardInUse cardInUse : toBeDestroyed) {
            cardInUse.sendToGraveYard();
        }

        deleteWatcher();
    }
}
