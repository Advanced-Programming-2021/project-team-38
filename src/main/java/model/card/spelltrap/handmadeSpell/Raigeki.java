package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.GamePlayController;
import exceptions.*;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.spelltrap.SpellTrap;

public class Raigeki extends SpellTrap {

    public Raigeki(PreCard preCard) {
        super(preCard);
        setName("Raigeki");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) throws NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, CloneNotSupportedException {
        DuelMenuController duelMenu = gamePlay.getDuelMenu();
        gamePlay.deselectedCard();
        for (int i = 0; i < 5; i++) {
            MonsterCardInUse cell = (MonsterCardInUse) rivalPlayer.getBoard().getFirstEmptyCardInUse(true);
            ((Monster) cell.getThisCard()).destroyThis
                    (null, rivalPlayer.getBoard(), null, cell, 0);
        }

    }
}
