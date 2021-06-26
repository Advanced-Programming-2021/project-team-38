package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public abstract class SpellsWithActivation extends Watcher {
    ActivationWatcher activationWatcher;

    public SpellsWithActivation(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        putMyActivationWatcher();
    }

    public void putMyActivationWatcher() {
        activationWatcher = new ActivationWatcher(ownerOfWatcher, WhoToWatch.MINE, this);
    }

    @Override
    public void deleteWatcher() {
        super.deleteWatcher();
        if (!activationWatcher.isDeleted) activationWatcher.deleteWatcher();
    }
}
