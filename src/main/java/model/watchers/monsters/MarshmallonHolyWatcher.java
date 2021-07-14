package model.watchers.monsters;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public class MarshmallonHolyWatcher extends Watcher {

    public MarshmallonHolyWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.DESTROY) {
            if (handleChain()) {
                this.duelMenuController = duelMenuController;
                this.theCard = theCard;
            }
        }
    }

    @Override
    public void doWhatYouShould() {
        ((MonsterCardInUse) ownerOfWatcher).canBeDestroyed = false;
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }
}