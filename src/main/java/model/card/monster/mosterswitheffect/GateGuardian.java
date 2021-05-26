package model.card.monster.mosterswitheffect;

import model.card.PreCard;
import model.card.monster.Monster;

public class GateGuardian extends Monster {

    public GateGuardian(PreCard preCard) {
        super(preCard);
        setName("Gate Guardian");
        super.numOfNeededTributes = 3;
    }


}
