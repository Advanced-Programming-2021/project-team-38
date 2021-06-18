package model.watchers.traps;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;

public class MirrorForceWatcher extends Watcher {
    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.WANT_TO_ATTACK) {
            if (handleChain()) {
                BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                battle.canBattleHappen = false;
                for (MonsterCardInUse monsterCardInUse : battle.getAttackerBoard().getMonsterZone()) {
                    if (!monsterCardInUse.isCellEmpty() && monsterCardInUse.isInAttackMode())
                        monsterCardInUse.sendToGraveYard();
                }
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
        //TODO for rooye rivals monster card
    }
}
