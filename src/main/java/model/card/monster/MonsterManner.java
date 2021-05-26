package model.card.monster;

public enum MonsterManner {
    DEFENSIVE_OCCUPIED,
    DEFENSIVE_HIDDEN,
    OFFENSIVE_OCCUPIED;

    public static MonsterManner getMonsterManner(String mannerString) {
        switch (mannerString) {
            case "OO":
                return OFFENSIVE_OCCUPIED;
            case "DO":
                return DEFENSIVE_OCCUPIED;
            case "DH":
                return DEFENSIVE_HIDDEN;
            default:
                return null;
        }
    }
}
