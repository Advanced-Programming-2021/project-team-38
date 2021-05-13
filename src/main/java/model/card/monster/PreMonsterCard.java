package model.card.monster;

import lombok.Getter;
import model.card.Card;
import model.card.PreCard;

import java.util.ArrayList;

@Getter
public class PreMonsterCard extends PreCard {
    private static ArrayList<PreMonsterCard> allMonsterCards;
    private int level;  //for number of tributes when summoned,
    private int defence;
    private int attack;
    private MonsterCardType type;   //nemidoonam
    private MonsterType monsterType;    //nemidoonam
    private CardAttribute attribute;    //property of card that is sometimes important for it's effects

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
