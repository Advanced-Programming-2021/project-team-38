package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.MonsterType;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

//Sword-dark - Black - United - Magnum
public class EquipActivationWatcher extends Watcher {

    EquipWatcher secondaryWatcher;
    MonsterType[] affected;
    int attackAdded;
    int defenseAdded;

    public EquipActivationWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        this.affected = affected;
        this.attackAdded = attackAdded;
        this.defenseAdded = defenseAdded;
        this.whoToWatch = whoToWatch;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                secondaryWatcher = new EquipWatcher(ownerOfWatcher, affected, attackAdded, defenseAdded, whoToWatch);
                isWatcherActivated = true;
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

    @Override
    public void deleteWatcher() {
        super.deleteWatcher();
        secondaryWatcher.deleteWatcher();
    }
}
