package model.card.monster;

import java.lang.reflect.Array;

public enum MonsterType {
    BEAST_WARRIOR,
    WARRIOR,
    AQUA,
    FIEND,
    BEAST,
    PYRO,
    SPELLCASTER,
    THUNDER,
    DRAGON,
    MACHINE,
    ROCK,
    INSECT,
    CYBERSE,
    FAIRY,
    SEA_SERPENT;


    public static MonsterType getEnum(String name) {
        return MonsterType.valueOf(name.replaceAll(" ", "_")
                .replaceAll("-", "_").toUpperCase());

    }
}
