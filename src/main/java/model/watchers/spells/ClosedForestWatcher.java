package model.watchers.spells;

import controller.game.DuelMenuController;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.MonsterType;
import model.card.monster.PreMonsterCard;
import model.watchers.WhoToWatch;
import model.watchers.Zone;

public class ClosedForestWatcher extends FieldWatcher{
    int monstersInGraveyard;

    public ClosedForestWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, affected, attackAdded, defenseAdded, whoToWatch);
    }

    @Override
    public void watchTheFieldAffected() {
        CardInUse[] unionOfMonsters = theTargetCells(Zone.MONSTER);
        monstersInGraveyard = ownerOfWatcher.getBoard().getGraveYard().getNumOfMonsters();
        for (CardInUse cardInUse : unionOfMonsters) {
            if (!cardInUse.isCellEmpty()) {
                MonsterType theMonsterType = ((PreMonsterCard) cardInUse.thisCard.preCardInGeneral).getMonsterType();
                for (MonsterType monsterType : affected) {
                    if (theMonsterType == monsterType && !amWatching.contains(cardInUse)) {
                        addWatcherToCardInUse(cardInUse);
                        ((MonsterCardInUse) cardInUse).addToAttack(attackAdded * monstersInGraveyard);
                        ((MonsterCardInUse) cardInUse).addToDefense(defenseAdded * monstersInGraveyard);
                    }
                }
            }
        }
    }

    @Override
    public void deleteWatcher() {
        for (CardInUse cardInUse : amWatching) {
            cardInUse.watchersOfCardInUse.remove(this);
            ((MonsterCardInUse) cardInUse).addToAttack(monstersInGraveyard * attackAdded * -1);
            ((MonsterCardInUse) cardInUse).addToDefense(monstersInGraveyard * defenseAdded * -1);
        }
        super.deleteWatcher();
    }
}
