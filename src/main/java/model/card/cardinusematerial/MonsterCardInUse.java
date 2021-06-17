//TODO complete putInGraveYard

package model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.CardState;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import model.watchers.watchingexceptions.CancelBattle;

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
        isInAttackMode = true; //it's needed for the first use of "setInAttackMode"
        //todo: if the monster is flip summoned, should we be able to change its attackMode after that?
    }

    public MonsterCardInUse(Board board) {
        super(board);
    }


    public void addToAttack(int amount) {   //amount can be negative
        this.attack += amount;
    }

    public void addToDefense(int amount) {   //amount can be negative
        this.defense += amount;
    }

    public void setInAttackMode(boolean inAttackMode) {
        if (isInAttackMode != inAttackMode) {
            isInAttackMode = inAttackMode;
            this.isPositionChanged = true;
        }
    }

    public boolean hasBeenAttacker() {
        return hasBeenAttacker;
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

    public void resetCell() {  //** watch out the come on this turn
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

    public void destroyThis() throws CancelBattle {
        //throw exception TODO
        watchByState(CardState.DESTROY);
        sendToGraveYard();
    }

}
