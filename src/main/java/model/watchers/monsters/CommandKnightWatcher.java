//finished
package model.watchers.monsters;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

public class CommandKnightWatcher extends Watcher {

    public CommandKnightWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController){
        //nothing
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.ownerOfCard.getBoard().getMonsterZone()) {
            if (!monsterCardInUse.isCellEmpty() && !amWatching.contains(monsterCardInUse)) {
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
