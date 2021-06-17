package model.watchers;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.watchers.watchingexceptions.CancelBattle;

public class MagicCylinderWatcher extends Watcher{
    //trap

    public MagicCylinderWatcher() {
        whoToWatch = WhoToWatch.RIVALS;
    }

    @Override
    public void watch(CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.WANT_TO_ATTACK) {
            if (handleChain()) {
                BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                battle.canBattleHappen = false;
                int damage = battle.getAttacker().getAttack();
                battle.getAttackerBoard().getOwner().decreaseLifePoint(damage);
                trapHasDoneItsEffect();
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        for (ownerOfWatcher.)   //TODO put this watcher on all of rivals cards
    }
}
