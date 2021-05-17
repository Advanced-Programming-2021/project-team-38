package model.card.monster;

import controller.game.BattleController;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;

@Getter
@Setter
public class Monster extends Card {
    protected PreMonsterCard myPreCard;
    protected boolean canBeNormalSummoned;

    public Monster(PreCard preCard) {
        super(preCard);
        myPreCard = (PreMonsterCard) preCard;
        //TODO set canBeNormalSummoned
    }

    public Monster setUpMonster() {    //after instance creation it will fill the fields
        setName(myPreCard.getName());
        instance = this;
        return this;
    }

    @Override
    public void setName(String name) {
        if (getName() == null)
            super.setName(name);
    }

    public Monster getCard() {
        return this;
    }


    public int getRawAttack() {
        return myPreCard.getAttack();
    }

    public int getRawDefense() {
        return myPreCard.getDefense();
    }

    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {

    }

    public boolean canActiveEffect(Board rivalBoard, Board myBoard, MonsterCardInUse rivalCard, MonsterCardInUse thisCard) {
        return false;
    }

    public void deactiveEffect(Board myBoard) {

    }

    public void sendToGraveYard() {

    }

    //during battle
    public void destroyThis(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard, int LPDamage) {
        //can be null every thing except thisCard and myBoard
        thisCard.putInGraveYard();
        myBoard.getOwner().decreaseLifePoint(LPDamage);
    }

    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) { //TODO complete
        return true;
    }

    //returns
    public void receiveAttack(BattleController battleController) {

    }
    // end during battle

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Monster(preCardInGeneral);
    }

    public void spinCard() {

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
//        return defensePoint;
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

//    }
//
//    @Override
//    public String toString() {
//        return "Monster{}";
//    }
}
