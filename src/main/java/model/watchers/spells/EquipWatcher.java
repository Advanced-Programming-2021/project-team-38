package model.watchers.spells;

import controller.game.DuelMenuController;
import controller.game.SelectController;
import model.CardState;
import model.Enums.ZoneName;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.MonsterType;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import model.watchers.Zone;

//United - Magnum - Black - Sword-dark
public class EquipWatcher extends SpellsWithActivation {

    MonsterCardInUse guardedCard;
    MonsterType[] affected;
    int attackAdded;
    int defenseAdded;

    public EquipWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        this.affected = affected;
        this.attackAdded = attackAdded;
        this.defenseAdded = defenseAdded;
        this.whoToWatch = whoToWatch;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.TRIGGERED && guardedCard == null) {
                selectToWatch();
                isWatcherActivated = true;
        }
    }

    @Override
    public boolean canPutWatcher() {
        return isWatcherActivated && guardedCard == null;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {

    }

    private void selectToWatch() {
        SelectController selectController = new SelectController(ZoneName.getZoneNamesByZone(Zone.MONSTER, WhoToWatch.ALL), roundController, ownerOfWatcher.getOwnerOfCard());
        if (affected != null) selectController.setMonsterTypes(affected);
        selectController.setCardType(CardType.MONSTER);
        guardedCard = (MonsterCardInUse) selectController.getTheCardInUse();
        addWatcherToCardInUse(guardedCard);
        addTheEquipAmount();
    }

    public void addTheEquipAmount() {
        if (guardedCard != null) {
            guardedCard.addToAttack(attackAdded);
            guardedCard.addToDefense(defenseAdded);
        }
    }

    @Override
    public void disableWatcher(CardInUse cardInUse) {
        deleteWatcher();
    }

    @Override
    public void deleteWatcher() {
        if (guardedCard != null) {
            guardedCard.addToAttack(attackAdded * -1);
            guardedCard.addToDefense(defenseAdded * -1);
        }

        super.deleteWatcher();
    }
}
