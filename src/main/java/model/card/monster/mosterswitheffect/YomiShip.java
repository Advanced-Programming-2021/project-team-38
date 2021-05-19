//completed

package model.card.monster.mosterswitheffect;

import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class YomiShip extends Monster {


    public YomiShip(PreCard preCard) {
        super(preCard);
        setName("Yomi Ship");
    }

    @Override
    public void destroyThis(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard, int LPDamage) {
        if (attacker != null && thisCard.isAttacked()) {
            ((Monster) attacker.getThisCard()).destroyThis(
                    null, attackerBoard, null, attacker, 0);
        }
        super.destroyThis(attackerBoard, myBoard, attacker, thisCard, 0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new YomiShip(preCardInGeneral);
    }
}
