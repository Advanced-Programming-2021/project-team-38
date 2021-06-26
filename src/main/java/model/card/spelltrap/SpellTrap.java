package model.card.spelltrap;

import controller.game.RoundController;
import lombok.Getter;
import lombok.Setter;
import model.Player;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;

@Getter
@Setter
public class SpellTrap extends Card {
    protected PreSpellTrapCard myPreCard;
    private boolean isActivated;

    public SpellTrap(PreCard preCard) {
        super(preCard);
        myPreCard = (PreSpellTrapCard) preCard;
    }
}
