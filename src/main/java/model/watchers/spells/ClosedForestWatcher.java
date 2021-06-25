package model.watchers.spells;

import model.card.cardinusematerial.CardInUse;
import model.card.monster.MonsterType;
import model.watchers.WhoToWatch;

public class ClosedForestWatcher extends FieldWatcher{  //TODO complete
    public ClosedForestWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, affected, attackAdded, defenseAdded, whoToWatch);
    }
}
