package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    GraveYard graveYard;
    private Cell[] monsterZone;
    private Cell[] spellAndTrapZone;

    public Board() {
        this.spellAndTrapZone = new Cell[5];
        this.monsterZone = new Cell[5];
        graveYard = new GraveYard();
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public boolean isMonsterZoneFull() {
        for (Cell cell : monsterZone) {
            if (cell.getCard() == null) return false;
        }
        return true;
    }
}
