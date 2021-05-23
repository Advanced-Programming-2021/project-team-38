package model.card.monster.mosterswitheffect;

import controller.game.BattleController;
import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class ExploderDragon extends Monster{

    public ExploderDragon(PreCard preCard) {
        super(preCard);
        setName("Exploder Dragon");
    }

    @Override
    public void destroyThis(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard, int LPDamage) {
        super.destroyThis(attackerBoard, myBoard, attacker, thisCard, 0);
        ((Monster) attacker.getThisCard()).destroyThis(null, attackerBoard, null, thisCard, 0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ExploderDragon(preCardInGeneral);
    }
}
