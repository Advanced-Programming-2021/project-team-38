package model.watchers.monsters;

import controller.game.BattleController;
import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;


public class MarshmallonWatcher extends Watcher {
    BattleController theBattleAgainstMe;
    boolean isFaceDownInBattle;

    public MarshmallonWatcher(CardInUse ownerOfWatcher) {
        super(ownerOfWatcher);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            theBattleAgainstMe = duelMenuController.getBattlePhaseController().battleController;
            isFaceDownInBattle = !theBattleAgainstMe.getPreyCard().isFaceUp;
        }

        if (cardState == CardState.DESTROY) {
            if (handleChain()) {
                if (theBattleAgainstMe == duelMenuController.getBattlePhaseController().battleController
                        && isFaceDownInBattle) {
                    theBattleAgainstMe.getAttackerBoard().getOwner().decreaseLifePoint(1000);
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
        addWatcherToCardInUse(cardInUse);
    }
}
