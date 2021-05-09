package model.card.monster;

import model.card.Card;
import model.card.PreCard;

import java.util.ArrayList;

public class PreMonsterCard extends PreCard {
    private static ArrayList<PreMonsterCard> allMonsterCards;
    private int level;
    private int defence;
    private int attack;
    private int price;
//    private String description;
//    private CardType cardType;
    private MonsterCardType type;
    private MonsterType monsterType;
    private CardAttribute attribute;

    static {
        allMonsterCards = new ArrayList<>();
    }

    public PreMonsterCard(String name, String type) {
        super(name, type);
    }

    public String getName() {
        return name;
    }


    @Override
    public Card newCard(String name) {
        return null;
    }

//    @Override
//    public static PreMonsterCard findPreCardByName(String name) {
//        for (PreMonsterCard preMonsterCard : allMonsterCards) {
//            if (preMonsterCard.getName().equals(name))
//                return preMonsterCard;
//        }
//    }
}
