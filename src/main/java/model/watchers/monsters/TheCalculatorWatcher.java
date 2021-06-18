package model.watchers.monsters;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.PreMonsterCard;
import model.watchers.Watcher;

public class TheCalculatorWatcher extends Watcher {
    int increasingRatio = 300;
    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        //nothing
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
        optionalUpdate();
    }

    public void optionalUpdate() {
        int sumOfLevels = 0;
        for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.ownerOfCard.getBoard().getMonsterZone()) {
            if (monsterCardInUse.isFaceUp()) {
                sumOfLevels = ((PreMonsterCard) monsterCardInUse.thisCard.getPreCardInGeneral()).getLevel();
            }
        }

        ((MonsterCardInUse)ownerOfWatcher).setAttack(sumOfLevels * increasingRatio);
    }
}
