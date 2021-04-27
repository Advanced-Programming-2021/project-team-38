package model.card.monster;

import model.card.Card;

public class Monster extends Card {
    protected int defensePoint;
    protected int attackPoint;
    protected boolean doesHaveEffect;
    protected int power;
    protected int additionalPower;
    protected MonsterManner manner;

    public Monster() {

    }

    public int getDefensePoint() {
        return defensePoint;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public boolean isDoesHaveEffect() {
        return doesHaveEffect;
    }

    public int getPower() {
        return power;
    }

    public MonsterManner getManner() {
        return manner;
    }

    public void setAdditionalPower(int additionalPower) {
        this.additionalPower = additionalPower;
    }

    public void changeManner(MonsterManner manner) {
        this.manner = manner;
    }

    public void attack(Card cardToAttack) {

    }

    public int getNumOfTributesNeeded() {
        return 0;
    }

    public void summon() {

    }

    @Override
    public String toString() {
        return "Monster{}";
    }
}
