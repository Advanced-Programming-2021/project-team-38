//summon cyberse
package model.card.monster.mosterswitheffect;

import model.Board;
import model.Enums.Phase;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class Texchanger extends Monster{

    private boolean isEffectActivatedAtThisTurn = false;

    public Texchanger(PreCard preCard) {
        super(preCard);
        setName("Texchanger");
    }

    @Override
    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        if (isEffectActivatedAtThisTurn)
            return super.canReceiveAttack(attackerBoard, myBoard, attacker, thisCard);
        else {
            isEffectActivatedAtThisTurn = true;
            summonCyberse(myBoard);
            return false;
        }
    }

    private void summonCyberse(Board myBoard) {
        //summon a cyberse with no effect from hand, deck or graveyard
    }

    @Override
    public void deactiveEffect(Board myBoard) {
        if (myBoard.getMyPhase() == Phase.END_RIVAL)
            isEffectActivatedAtThisTurn = false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Texchanger(preCardInGeneral);
    }
}
