package model;

import model.card.CardInUse;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    GraveYard graveYard;
//    private Cell[] monsterZone;
//    private Cell[] spellAndTrapZone;

    private CardInUse[] monsterZone;
    private CardInUse[] spellTrapZone;
    private int additionalAttack;
    private int additionalDefense;

    private Phase onPhase;

    {
        this.monsterZone = new CardInUse[5];
        this.spellTrapZone = new CardInUse[5];
    }

    public Board() {
        graveYard = new GraveYard();
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    private void newCells() {
        for (int i = 0; i < 5; i++) {
            monsterZone[i] = new CardInUse();
            spellTrapZone[i] = new CardInUse();
        }
    }

    public boolean isMonsterZoneFull() {
        for (CardInUse cardInUse : monsterZone) {
            if (cardInUse.getThisCard() == null) return false;
        }
        return true;
    }
}
