package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.GamePlayController;
import exceptions.*;
import model.Board;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

import static controller.game.SummonController.specialSummonPreCard;

public class Harpie extends SpellTrap {

    public Harpie(PreCard preCard) {
        super(preCard);
        setName("Harpie's Feather Duster");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {
        for (SpellTrapCardInUse spellTrapCardInUse : rivalPlayer.getBoard().getSpellTrapZone()) {
            ((SpellTrap)spellTrapCardInUse.getThisCard()).destroyThis(
                    rivalPlayer, myPlayer, spellTrapCardInUse, gamePlay);
        }
    }
}
