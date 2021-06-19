package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.MonsterType;
import model.watchers.Watcher;

import java.lang.reflect.Array;

public class FieldWatcher extends Watcher {
    //TODO
    public FieldWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAttack) {
        super(ownerOfWatcher);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {

    }

    @Override
    public boolean canPutWatcher() {
        return false;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {

    }
}
