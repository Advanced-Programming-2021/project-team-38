package model.watchers;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.watchingexceptions.CancelBattle;

public class CommandKnightWatcher extends Watcher {
    @Override
    public void watch(CardState cardState, DuelMenuController duelMenuController) throws CancelBattle {
        //nothing
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.ownerOfCard.getBoard().getMonsterZone()) {
            if (!amWatching.contains(monsterCardInUse)) {
                monsterCardInUse.addToAttack(400);
                monsterCardInUse.watchersOfCardInUse.add(this);
                amWatching.add(monsterCardInUse);
            }
        }
    }

    @Override
    public void deleteWatcher() {
        for (CardInUse cardInUse : amWatching) {
            ((MonsterCardInUse) cardInUse).addToAttack(-400);
        }
        super.deleteWatcher();
    }
}
