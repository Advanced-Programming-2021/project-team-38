package model.watchers.traps;

import controller.game.DrawPhaseController;
import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;

public class TimeSealWatcher extends Watcher {
    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        //nothing
    }

    @Override
    public boolean canPutWatcher() {
        return false;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        //nothing
    }

    @Override
    public void update(Phase newPhase) {
        if (newPhase == Phase.DRAW_RIVAL) {
            if (handleChain()) {
                DrawPhaseController.canDraw = false;
            }
        }
        else {
            if (!DrawPhaseController.canDraw) {
                DrawPhaseController.canDraw = true;
                trapHasDoneItsEffect();
            }
        }
    }
}
