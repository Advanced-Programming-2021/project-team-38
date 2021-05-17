package model;

import lombok.Getter;
import lombok.Setter;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;

@Getter
@Setter
public class Board {
    GraveYard graveYard;

    private MonsterCardInUse[] monsterZone;
    private SpellTrapCardInUse[] spellTrapZone;
    private int additionalAttack;
    private int additionalDefense;
    private Player owner;
    private Phase myPhase;

    private Phase onPhase;

    {
        this.monsterZone = new MonsterCardInUse[5];
        this.spellTrapZone = new SpellTrapCardInUse[5];
        newCells();
    }

    public Board() {
        graveYard = new GraveYard();
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    private void newCells() {
        for (int i = 0; i < 5; i++) {
            monsterZone[i] = new MonsterCardInUse();
            spellTrapZone[i] = new SpellTrapCardInUse();
        }
    }

    public void addToAllMonsterCellsAttack(int amount) { //amount can be negative too
        for (MonsterCardInUse monsterCardInUse : monsterZone) {
            monsterCardInUse.addToAttack(amount);
        }
    }

    public boolean isMonsterZoneFull() {
        return getFirstEmptyCardInUse(true) == null;
    }

    public boolean isSpellTrapZoneFull() {
        return getFirstEmptyCardInUse(false) == null;
    }

    public void addToAdditionalAttack(int amount) {     //maybe not useful
        additionalAttack += amount;
    }

    public CardInUse getFirstEmptyCardInUse(boolean isMonster) {
        CardInUse[] zone;
        if (isMonster) zone = this.monsterZone;
        else zone = this.spellTrapZone;
        for (CardInUse cardInUse : zone) {
            if (cardInUse.isCellEmpty())
                return cardInUse;
        }
        return null;
    }

    public int getNumOfAvailableTributes() {
        int counter = 0;
        for (MonsterCardInUse monsterCardInUse : this.monsterZone) {
            if (monsterCardInUse.getThisCard() != null) counter++;
        }
        return counter;
    }
}
