//just copied

package model.card.monster.mosterswitheffect;

import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class Suijin extends Monster {
    private boolean isEffectUsed = false;

    public Suijin(PreCard preCard) {
        super(preCard);
        setName("Suijin");
    }


    @Override
    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        //if user wants to active the effect.
        isEffectUsed = true;
    }

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        return monsterCardInUse.isFaceUp() && !isEffectUsed &&
                monsterCardInUse.isAttacked();
    }

    @Override
    protected void receiveAttack(Board attackerBoard, Board myBoard, CardInUse attacker, CardInUse thisCard) {

    }

    @Override
    public Monster getCard() {
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Suijin(preCardInGeneral);
    }
}
