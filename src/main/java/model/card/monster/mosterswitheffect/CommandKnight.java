//use receive attack - doesn't have deactive effect

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
    public Monster setUpMonster() {
        instance = this;
        return this;
    }

    @Override
    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        if (canActiveEffect(playerBoard, rivalBoard, monsterCardInUse))
            playerBoard.addToAllMonsterCellsAttack(400);
    }

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, CardInUse cardInUse) {
        return cardInUse.isFaceUp() && cardInUse.isPositionChanged();
    }

    @Override
    protected void receiveAttack(Board attackerBoard, Board myBoard, CardInUse attacker, CardInUse thisCard) {

    }

    @Override
    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        return super.canReceiveAttack(attackerBoard, myBoard, attacker, thisCard) &&
                myBoard.getFirstEmptyCardInUse(true) == null; //todo: check with hasti
    }

    @Override
    public Monster getCard() {
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CommandKnight(preCardInGeneral);
    }
}
