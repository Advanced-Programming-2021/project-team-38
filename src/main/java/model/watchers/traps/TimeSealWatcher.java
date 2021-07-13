package model.watchers.traps;

import controller.game.DrawPhaseController;
import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public class TimeSealWatcher extends Watcher {
    public TimeSealWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }

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
                isWatcherActivated = true;
            }
        } else {
            if (!DrawPhaseController.canDraw) {
                DrawPhaseController.canDraw = true;
                trapHasDoneItsEffect();
            }
        }
    }
}
