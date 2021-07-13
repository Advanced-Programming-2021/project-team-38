//TODO complete putInGraveYard
package model.card.cardinusematerial;

import lombok.Getter;
import model.Board;
import model.CardState;
import model.card.spelltrap.SpellTrap;

@Getter
public class SpellTrapCardInUse extends CardInUse {

    public SpellTrapCardInUse(Board board) {
        super(board);
    }

    public void activateMyEffect() {
        if (thisCard == null) return;
        faceUpAfterActivation();
        watchByState(CardState.ACTIVE_MY_EFFECT);
    }

    public void faceUpAfterActivation() {
        if (!isFaceUp)  faceUpCard();
        ((SpellTrap) thisCard).setActivated(true);
    }
}
