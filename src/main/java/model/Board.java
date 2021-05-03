package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    private Cell[] monsterZone;
    private Cell[] spellAndTrapZone;

    public Board() {
        this.monsterZone = new Cell[5];
        this.spellAndTrapZone = new Cell[5];
    }

    public boolean isMonsterZoneFull() {
        for (Cell cell : monsterZone) {
            if (cell.getCard() == null) return false;
        }
        return true;
    }
}
