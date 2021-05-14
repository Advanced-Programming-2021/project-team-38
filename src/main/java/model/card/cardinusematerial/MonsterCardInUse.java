package model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.card.monster.PreMonsterCard;

@Getter
@Setter
public class MonsterCardInUse extends CardInUse {
    private int attack, defense;
    private boolean hasBeenAttacker; //if it has attacked another card in this turn
    private boolean isAttacked; //if it has been attacked in this phase
    private boolean isAttacking;    //if it has attacked a rival card in this phase
    private boolean cameOnThisTurn; //if it was drawn in this phase

    {
        resetCell();
        defense = 0;
        attack = 0;
    }


    public void addToAttack(int amount) {   //amount can be negative
        this.attack += amount;
    }

    public void setAttack(Board playerBoard, int monsterCardAttack) {
//        attack = playerBoard.getAdditionalAttack() + monsterCardAttack;
        attack += monsterCardAttack;
    }

    public void setACardInThisCell(PreMonsterCard preMonsterCard) { //TODO
        resetCell();
        //continue
    }

    private void resetCell() {
        cameOnThisTurn = false;
        isAttacked = false;
        isAttacking = false;
        hasBeenAttacker = false;
    }
}
