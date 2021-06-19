package model.Enums;

import exceptions.InvalidSelection;

public enum ZoneName {
    HAND,
    MONSTER,
    SPELL,
    FIELD,
    GRAVEYARD,
    SPELL_TRAP;

    public static ZoneName getZoneName(String field) throws InvalidSelection {
        ZoneName zoneName;
        switch (field) {
            case "hand":
                zoneName = ZoneName.HAND;
                break;
            case "monster":
                zoneName = ZoneName.MONSTER;
                break;
            case "spell":
                zoneName = ZoneName.SPELL;
                break;
            case "field":
                zoneName = ZoneName.FIELD;
                break;
            case "graveyard":
                zoneName = ZoneName.GRAVEYARD;
                break;
            default:
                throw new InvalidSelection();
        }
        return zoneName;
    }
}
