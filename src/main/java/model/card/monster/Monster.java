package model.card.monster;

import model.card.Card;

public class Monster extends Card {
    protected PreMonsterCard myPreCard;

    public Monster getCard() {
        return this;
    }

    public void receiveAttack() {

    }

    public int getRawAttack() {
        return myPreCard.getAttack();
    }

    public int getRawDefense() {
        return myPreCard.getDefence();
    }

    public void activeEffect() {

    }

    public void deactiveEffect() {

    }

    public void sendToGraveYard() {

    }

//
//    private String name;
//    private int defensePoint;
//    private int attackPoint;
//    private boolean hasEffect;
//    private int power;
//    private int additionalPower;
//    private MonsterManner manner;
//
//    public Monster(int defensePoint, int attackPoint, boolean doesHaveEffect) {
//        this.defensePoint = defensePoint;
//        this.attackPoint = attackPoint;
//        this.hasEffect = doesHaveEffect;
//    }
//
//    public int getDefensePoint() {
//        return defensePoint; //todo : handle the additional power
//    }
//
//    public int getAttackPoint() {
//        return attackPoint;
//    }
//
//    public boolean isHasEffect() {
//        return hasEffect;
//    }
//
//    public int getPower() {
//        return power;
//    }
//
//    public MonsterManner getManner() {
//        return manner;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    public int getAdditionalPower() {
//        return additionalPower;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDefensePoint(int defensePoint) {
//        this.defensePoint = defensePoint;
//    }
//
//    public void setAttackPoint(int attackPoint) {
//        this.attackPoint = attackPoint;
//    }
//
//    public void setHasEffect(boolean hasEffect) {
//        this.hasEffect = hasEffect;
//    }
//
//    public void setPower(int power) {
//        this.power = power;
//    }
//
//    public void setManner(MonsterManner manner) {
//        this.manner = manner;
//    }
//
//    public void setAdditionalPower(int additionalPower) {
//        this.additionalPower = additionalPower;
//    }
//
//    public void changeManner(MonsterManner manner) {
//        this.manner = manner;
//    }
//
//    public void attack(Card cardToAttack) {
//
//    }
//
//    public int getNumOfTributesNeeded() {
//        return 0;
//    }
//
//    public void summon() {
//        //todo : what should be the input?
//    }
//
//    @Override
//    public String toString() {
//        return "Monster{}";
//    }
}
