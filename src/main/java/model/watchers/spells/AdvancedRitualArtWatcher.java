package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public class AdvancedRitualArtWatcher extends Watcher {
    public AdvancedRitualArtWatcher(CardInUse ownerOfWatcher) {
        super(ownerOfWatcher);
        whoToWatch = WhoToWatch.MINE;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {

    }

    @Override
    public boolean canPutWatcher() {
        return false;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {

    }
}
