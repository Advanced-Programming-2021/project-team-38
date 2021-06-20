package model.watchers.monsters;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public class YomiShipWatcher extends Watcher {
    BattleController theBattleAgainstMe;

    public YomiShipWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            theBattleAgainstMe = duelMenuController.getBattlePhaseController().battleController;
        } else if (cardState == CardState.DESTROY) {
            if (theBattleAgainstMe != null && theBattleAgainstMe == duelMenuController.getBattlePhaseController().battleController) {
                theBattleAgainstMe.getAttacker().destroyThis();
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
}
