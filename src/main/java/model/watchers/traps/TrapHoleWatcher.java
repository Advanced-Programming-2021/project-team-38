package model.watchers.traps;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;
import model.watchers.Zone;

public class TrapHoleWatcher extends Watcher {
    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (cardInUseHasWatcherCondition(theCard)) {
                if (handleChain()) {
                    theCard.sendToGraveYard();
                    trapHasDoneItsEffect();
                }
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        CardInUse[] rivalMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse rivalMonster : rivalMonsters) {
            addWatcherToCardInUse(rivalMonster);
        }
    }

    public boolean cardInUseHasWatcherCondition(CardInUse cardInUse) {
        return ((MonsterCardInUse) cardInUse).getAttack() >= 1000;
    }
}
