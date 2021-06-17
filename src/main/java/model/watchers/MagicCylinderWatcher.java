package model.watchers;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;

public class MagicCylinderWatcher extends Watcher{

    @Override
    public void watch(CardState cardState, DuelMenuController duelMenuController) {

    }

    @Override
    public void reNewWatch() {

    }

    @Override
    public boolean canPutWatcher() {
        return ownerOfWatcher.isFaceUp();
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        switch (whoToWatch) {
            case ALL:

        }
    }

    @Override
    public void update(Phase newPhase) {

    }

    @Override
    public void deleteWatcher() {
        for (CardInUse cardInUse : amWatching) {
            cardInUse.getWatchersOfCardInUse().remove(this);
        }
    }
}
