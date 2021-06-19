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

public class EquipWatcher extends Watcher {

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
        selectToWatch();
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

    }

    private void selectToWatch() {
        SelectController selectController = new SelectController(ZoneName.getZoneNamesByZone(Zone.MONSTER, WhoToWatch.ALL), roundController, ownerOfWatcher.getOwnerOfCard());
        selectController.setMonsterTypes(affected);
        selectController.setCardType(CardType.MONSTER);
        guardedCard = (MonsterCardInUse) selectController.getTheCardInUse();
        addWatcherToCardInUse(guardedCard);
    }

    @Override
    public void disableWatcher(CardInUse cardInUse) {
        deleteWatcher();
    }
}
