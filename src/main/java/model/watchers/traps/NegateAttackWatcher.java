package model.watchers.traps;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import model.watchers.Zone;

public class NegateAttackWatcher extends Watcher {
    public NegateAttackWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 3;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.WANT_TO_ATTACK) {
            if (handleChain()) {
                this.duelMenuController = duelMenuController;
                this.theCard = theCard;
            }
        }
    }

    @Override
    public void doWhatYouShould() {
        BattleController battle = duelMenuController.getBattlePhaseController().battleController;
        battle.canBattleHappen = false;
        spellTrapHasDoneItsEffect();
        duelMenuController.nextPhase();
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        CardInUse[] rivalMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse rivalMonster : rivalMonsters) {
            addWatcherToCardInUse(rivalMonster);
        }
    }
}
