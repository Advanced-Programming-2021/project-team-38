package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.RoundController;
import view.exceptions.*;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.spelltrap.SpellTrap;
import view.Print;

public class PotOfGreed extends SpellTrap {

    public PotOfGreed(PreCard preCard) {
        super(preCard);
        setName("Pot of Greed");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) throws NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, CloneNotSupportedException {
        DuelMenuController duelMenu = gamePlay.getDuelMenuController();
        gamePlay.deselectCard();
        Print.print("two cards from deck were added to your hand");

        myPlayer.getHand().addCard(
                myPlayer.getBoard().getDeckInUse().getMainCards().remove(0));

        myPlayer.getHand().addCard(
                myPlayer.getBoard().getDeckInUse().getMainCards().remove(0));
    }
}
