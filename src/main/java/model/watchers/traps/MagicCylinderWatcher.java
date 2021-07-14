//finish
package model.watchers.traps;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import model.watchers.Zone;

public class MagicCylinderWatcher extends Watcher {

    public MagicCylinderWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }
    //trap


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
        int damage = battle.getAttacker().getAttack();
        battle.getAttackerBoard().getOwner().decreaseLifePoint(damage);
        spellTrapHasDoneItsEffect();
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
}
