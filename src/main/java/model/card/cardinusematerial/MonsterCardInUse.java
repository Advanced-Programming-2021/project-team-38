//TODO complete putInGraveYard

package model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.card.Card;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;

@Getter
@Setter
public class MonsterCardInUse extends CardInUse {
    private int attack, defense;
    private boolean hasBeenAttacker; //if it has attacked another card in this turn
    private boolean isAttacked; //if it has been attacked in this phase
    private boolean isAttacking;    //if it has attacked a rival card in this phase
    private boolean cameOnThisTurn; //if it was drawn in this phase
    private boolean isInAttackMode;

    {
        resetCell();
        defense = 0;
        attack = 0;
    }


    public void addToAttack(int amount) {   //amount can be negative
        this.attack += amount;
    }

    @Override
    public Card getThisCard() {
        return ((Monster)super.getThisCard());
    }

    public void setAttack(Board playerBoard, int monsterCardAttack) {
//        attack = playerBoard.getAdditionalAttack() + monsterCardAttack;
        attack += monsterCardAttack;
    }

    public void setACardInThisCell(PreMonsterCard preMonsterCard) { //TODO
        resetCell();
        //continue
    }

    /*
    * if the card is in attack point returns attack point
    * else returns defense point
    * */
    public int appropriatePointAtBattle() {
        if (isInAttackMode)
            return attack;

        return defense;
    }

    private void resetCell() {  //** watch out the come on this turn
        cameOnThisTurn = false;
        isAttacked = false;
        isAttacking = false;
        hasBeenAttacker = false;
        isInAttackMode = false;
    }

    @Override
    public void putInGraveYard() {

    }

    @Override
    public void changePosition() {
        super.changePosition();
        if (isFaceUp())
            ((Monster)thisCard).spinCard();
    }

    public void changeIsAttacked() {    //when the card is attacked
        isAttacked = true;
    }

    public void changeIsAttacking() {   //when the card is attacking
        //TODO think what to do
        isAttacking = true;
        hasBeenAttacker = true;
    }
}
