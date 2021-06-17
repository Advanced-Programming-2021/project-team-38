//finished
package model.watchers;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.watchingexceptions.CancelBattle;

public class CommandKnightHolyWatcher extends Watcher{

    {
        whoToWatch = WhoToWatch.MINE;
    }
    @Override
    public void watch(CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (handleChain()) {
                for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.ownerOfCard.getBoard().getMonsterZone()) {
                    if (!monsterCardInUse.isCellEmpty() && monsterCardInUse != ownerOfWatcher) {
                        BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                        battle.canBattleHappen = false;
                        isWatcherActivated = true;
                    }
                }
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        if (ownerOfWatcher == null)     ownerOfWatcher = cardInUse;

        if (!amWatching.contains(cardInUse)) {
            cardInUse.watchersOfCardInUse.add(this);
            amWatching.add(cardInUse);
        }
    }

    @Override
    public void update(Phase newPhase) {

    }

    @Override
    public void deleteWatcher() {

    }
}
