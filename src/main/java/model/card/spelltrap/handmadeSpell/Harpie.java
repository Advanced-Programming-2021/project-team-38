package model.card.spelltrap.handmadeSpell;

import controller.game.RoundController;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.spelltrap.SpellTrap;

public class Harpie extends SpellTrap {

    public Harpie(PreCard preCard) {
        super(preCard);
        setName("Harpie's Feather Duster");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) {
        for (SpellTrapCardInUse spellTrapCardInUse : rivalPlayer.getBoard().getSpellTrapZone()) {
            ((SpellTrap) spellTrapCardInUse.getThisCard()).destroyThis(
                    rivalPlayer, myPlayer, spellTrapCardInUse, gamePlay);
        }
    }
}
