package model.card.monster;

import lombok.Getter;
import model.card.Card;
import model.card.CardType;
import model.card.PreCard;

import java.util.ArrayList;

@Getter
public class PreMonsterCard extends PreCard {
    private int level;  //for number of tributes when summoned,
    private int defense;
    private int attack;
    private MonsterCardType type;   //nemidoonam
    private MonsterType monsterType;    //nemidoonam
    private CardAttribute attribute;    //property of card that is sometimes important for it's effects

    public PreMonsterCard(String[] cardData) {
        //Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price
        name = cardData[0];
        level = Integer.parseInt(cardData[1]);
        attribute = CardAttribute.valueOf(cardData[2].toUpperCase());
        monsterType = MonsterType.getEnum(cardData[3]);
        type = MonsterCardType.valueOf(cardData[4].toUpperCase());
        attack = Integer.parseInt(cardData[5]);
        defense = Integer.parseInt(cardData[6]);
        description = cardData[7];
        price = Integer.parseInt(cardData[8]);
        cardType = CardType.MONSTER;

        allPreCardsInstances.put(this, null);
    }

    public String getName() {
        return name;
    }


    @Override
    public Card newCard(String name) {
        return null;
    }
}
