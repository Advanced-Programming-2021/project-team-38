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
    protected int numOfNeededTributes;
    protected int level;

    public Monster(PreCard preCard) {
        super(preCard);
        myPreCard = (PreMonsterCard) preCard;
        this.level = ((PreMonsterCard) preCard).getLevel();
    }

    public Monster setUpMonster() {    //after instance creation it will fill the fields
        setName(myPreCard.getName());
        setNumOfTributes(); //todo: fine? check with hasti
        instance = this;
        return this;
    }

    protected void setNumOfTributes() {
        int level = myPreCard.getLevel();
        if (level <= 4) numOfNeededTributes = 0;
        else if (level <= 6) numOfNeededTributes = 1;
        else numOfNeededTributes = 2;
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


    //during battle
//    public void destroyThis(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard, int LPDamage) {
//        //can be null every thing except thisCard and myBoard
//        thisCard.putInGraveYard();
//        myBoard.getOwner().decreaseLifePoint(LPDamage);
//    }

    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) { //TODO complete
        return true;
    }

    //returns
    public void receiveAttack(BattleController battleController) {

    }
    // end during battle

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Monster(preCardInGeneral);
    }

    public void spinCard() {

    }
}
