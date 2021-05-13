package model.card.cardinusematerial;

import model.Board;
import model.card.cardinusematerial.CardInUse;

public class MonsterCardInUse extends CardInUse {
    private int attack, defense;
    private boolean hasAttackedInThisTurn;

    {
        hasAttackedInThisTurn = false;
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
}
