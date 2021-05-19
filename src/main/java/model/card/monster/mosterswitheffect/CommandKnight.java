//doesn't have deactive effect

package model.card.monster.mosterswitheffect;

import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class CommandKnight extends Monster {


    public CommandKnight(PreCard preCard) {
        super(preCard);
        setName("Command Knight");
    }


    @Override
    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        if (canActiveEffect(playerBoard, rivalBoard, monsterCardInUse))
            playerBoard.addToAllMonsterCellsAttack(400);
    }

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse thisCard) {
        return thisCard.isFaceUp() && thisCard.isPositionChanged();
    }

    @Override
    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        return super.canReceiveAttack(attackerBoard, myBoard, attacker, thisCard) &&
                myBoard.getFirstEmptyCardInUse(true) == null;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return new CommandKnight(preCardInGeneral);
    }
}
