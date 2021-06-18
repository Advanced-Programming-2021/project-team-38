//TODO complete putInGraveYard
package model.card.cardinusematerial;

import lombok.Getter;
import model.Board;
import model.CardState;

@Getter
public class SpellTrapCardInUse extends CardInUse {
    public void activeMyEffect() {
        watchByState(CardState.ACTIVE_MY_EFFECT);


    public SpellTrapCardInUse(Board board) {
        super(board);
    }
}
