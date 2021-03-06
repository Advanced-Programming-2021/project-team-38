package model.watchers.traps;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import model.watchers.Zone;
import model.watchers.spells.DestroyAllWatcher;

public class TorrentialTributeWatcher extends Watcher {
    public TorrentialTributeWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (handleChain()) {
                this.duelMenuController = duelMenuController;
                this.theCard = theCard;
            }
        }
    }

    @Override
    public void doWhatYouShould() {
        new DestroyAllWatcher(ownerOfWatcher, WhoToWatch.ALL, Zone.MONSTER).watch(
                theCard, CardState.TRIGGERED, null);
        spellTrapHasDoneItsEffect();
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        CardInUse[] allMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse monster : allMonsters) {
            addWatcherToCardInUseGeneral(monster);
        }
    }
}
