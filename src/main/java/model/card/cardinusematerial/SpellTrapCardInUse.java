//TODO complete putInGraveYard
package model.card.cardinusematerial;

import lombok.Getter;
import model.Board;
import model.CardState;
import model.card.CardType;
import model.card.spelltrap.SpellTrap;

@Getter
public class SpellTrapCardInUse extends CardInUse {

    public SpellTrapCardInUse(Board board) {
        super(board);
    }

    public void activateMyEffect() {
        if (thisCard == null) return;
        watchByState(CardState.ACTIVE_EFFECT);
        if (thisCard.getPreCardInGeneral().getCardType().equals(CardType.TRAP)) {
//            DuelMenu.
        }
        ((SpellTrap) thisCard).setActivated(true);
    }
}
