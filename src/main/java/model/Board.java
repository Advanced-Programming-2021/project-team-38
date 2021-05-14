package model;

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
        return getFirstEmptyCell(true) == null;
    }

    public boolean isSpellTrapZoneFull() {
        return getFirstEmptyCell(false) == null;
    }

    public Cell getFirstEmptyCell(boolean isMonster) {
        if (isMonster) {
            for (Cell cell : monsterZone) {
                if (cell.getCard() == null) return cell;
            }
        } else {
            for (Cell cell : spellAndTrapZone) {
                if (cell.getCard() == null) return cell;
            }
        }
        return null;
    }

    public int getNumOfAvailableTributes() {
        int tributes = 0;
        for (Cell cell : monsterZone) {
            if (cell.getCard() != null) tributes++;
        }
        return tributes;
    } //todo: Any monster can be tribute and spells and traps can't be tribute. Right?
}
