//merely completed: canActiveEffectMethod

package model.card.monster.mosterswitheffect;

import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class ManEaterBug extends Monster {


    public ManEaterBug(PreCard preCard) {
        super(preCard);
        setName("Man-Eater Bug");
    }

    @Override
    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        //choose a rival card and destroy it
    }

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, CardInUse cardInUse) {
        return cardInUse.isFaceUp() && cardInUse.isPositionChanged();
    }

    @Override
    protected void receiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        //if az posht be roo effect active
    }

    @Override
    public Monster getCard() {
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new ManEaterBug(preCardInGeneral);
    }
}
