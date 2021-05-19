package model.card.monster.mosterswitheffect;

import controller.game.BattleController;
import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class Marshmallon extends Monster{

    public Marshmallon(PreCard preCard) {
        super(preCard);
        setName("Marshmallon");
    }

    @Override
    public void receiveAttack(BattleController battleController) {
        if (!battleController.isPreyCardInAttackMode())
            battleController.getAttackerBoard().getOwner().decreaseLifePoint(1000);
    }

    @Override
    public void destroyThis(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard, int LPDamage) {
        myBoard.getOwner().decreaseLifePoint(LPDamage);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Marshmallon(preCardInGeneral);
    }
}
