package model.card.monster;

import model.Board;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;

public class CommandKnight extends Monster{


    @Override
    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse) {
        if (canActiveEffect(playerBoard, rivalBoard, monsterCardInUse))
            playerBoard.addToAllMonsterCellsAttack(400);
    }

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, CardInUse cardInUse) {
        return cardInUse.isFaceUp() && cardInUse.isPositionChanged();
    }

    @Override
    protected void receiveAttack() {

    }

    @Override
    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, CardInUse attacker) {
        return super.canReceiveAttack(attackerBoard, myBoard, attacker) &&
                myBoard.getFirstEmptyCell(myBoard.getMonsterZone()) == null;
    }

    @Override
    public Monster getCard() {
        return this;
    }
}
