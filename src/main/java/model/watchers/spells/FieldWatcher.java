package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.MonsterType;
import model.card.monster.PreMonsterCard;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import model.watchers.Zone;

//YamiFirst - YamiSec - Forest - Closed Forest - Umiiruka -
public class FieldWatcher extends Watcher {
    MonsterType[] affected;
    int attackAdded;
    int defenseAdded;


    //continuous-FIELD
    public FieldWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        this.affected = affected;
        this.attackAdded = attackAdded;
        this.defenseAdded = defenseAdded;
        this.whoToWatch = whoToWatch;
        isDisposable = true;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                watchTheFieldAffected();
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
        optionalUpdate();
    }

    public void optionalUpdate() {
        watchTheFieldAffected();
    }

    public void watchTheFieldAffected() {
        CardInUse[] unionOfMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse cardInUse : unionOfMonsters) {
            if (!cardInUse.isCellEmpty()) {
                MonsterType theMonsterType = ((PreMonsterCard) cardInUse.thisCard.preCardInGeneral).getMonsterType();
                for (MonsterType monsterType : affected) {
                    if (theMonsterType == monsterType && !amWatching.contains(cardInUse)) {
                        addWatcherToCardInUse(cardInUse);
                        ((MonsterCardInUse) cardInUse).addToAttack(attackAdded);
                        ((MonsterCardInUse) cardInUse).addToDefense(defenseAdded);
                    }
                }
            }
        }
    }

    @Override
    public void deleteWatcher() {
        for (CardInUse cardInUse : amWatching) {
            cardInUse.watchersOfCardInUse.remove(this);
            amWatching.remove(cardInUse);
            ((MonsterCardInUse) cardInUse).addToAttack(attackAdded * -1);
            ((MonsterCardInUse) cardInUse).addToDefense(defenseAdded * -1);
        }
    }
}
