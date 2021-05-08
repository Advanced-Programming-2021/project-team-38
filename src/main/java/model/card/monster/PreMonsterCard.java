package model.card.monster;

import model.card.Card;
import model.card.CardType;
import model.card.Pre;

import java.util.HashMap;

public class PreMonsterCard implements Pre {
    private String name;
    private int level;
    private int defence;
    private int attack;
    private int price;
    private String description;
    private CardType cardType;
    private MonsterCardType type;
    private MonsterType monsterType;
    private CardAttribute attribute;
    private boolean hasEffect;


    @Override
    public Card makeCard() {
        return new Monster(defence, attack, hasEffect);
    }

    @Override
    public int getPrice() {
        return price;
    }
}
