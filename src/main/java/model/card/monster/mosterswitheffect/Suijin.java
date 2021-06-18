//just copied

package model.card.monster.mosterswitheffect;

import controller.game.BattleController;
import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class Suijin extends Monster {
    private boolean isEffectUsed = false;

    public Suijin(PreCard preCard) {
        super(preCard);
        setName("SuijinWatcher");
    }

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        return monsterCardInUse.isFaceUp() && !isEffectUsed &&
                monsterCardInUse.isAttacked();
    }

    @Override
    public void receiveAttack(BattleController battleController) {
        if (canActiveEffect(battleController.getPreyBoard(), null, battleController.getPreyCard(), null)) {
            battleController.setAttackerAttack(0);
            isEffectUsed = true;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Suijin(preCardInGeneral);
    }
}
