package model.Enums;

import exceptions.InvalidSelection;

public enum ZoneName {
    HAND,
    MY_MONSTER_ZONE,
    MY_SPELL_ZONE,
    MY_FIELD,
    MY_GRAVEYARD,
    MY_DECK,
    RIVAL_MONSTER_ZONE,
    RIVAL_SPELL_ZONE,
    RIVAL_FIELD,
    RIVAL_GRAVEYARD;

    public static ZoneName getZoneName(String field, boolean isForRival) throws InvalidSelection {
        ZoneName zoneName;
        switch (field) {
            case "hand":
                if (!isForRival) zoneName = ZoneName.HAND;
                else throw new InvalidSelection();
                break;
            case "monster":
                if (!isForRival) zoneName = ZoneName.MY_MONSTER_ZONE;
                else zoneName = ZoneName.RIVAL_MONSTER_ZONE;
                break;
            case "spell":
                if (!isForRival) zoneName = ZoneName.MY_SPELL_ZONE;
                else zoneName = ZoneName.RIVAL_SPELL_ZONE;
                break;
            case "field":
                if (!isForRival) zoneName = ZoneName.MY_FIELD;
                else zoneName = ZoneName.RIVAL_FIELD;
                break;
            case "graveyard":
                if (!isForRival) zoneName = ZoneName.MY_GRAVEYARD;
                else zoneName = ZoneName.RIVAL_GRAVEYARD;
                break;
            default:
                throw new InvalidSelection();
        }
        return zoneName;
    }
}
