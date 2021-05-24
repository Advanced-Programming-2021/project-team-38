package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.RoundController;
import exceptions.*;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.spelltrap.CardIcon;
import model.card.spelltrap.PreSpellTrapCard;
import model.card.spelltrap.SpellTrap;

public class Terraforming extends SpellTrap {

    public Terraforming(PreCard preCard) {
        super(preCard);
        setName("Terraforming");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) throws NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, CloneNotSupportedException {
        DuelMenuController duelMenu = gamePlay.getDuelMenuController();
        gamePlay.deselectCard();
        duelMenu.askForSth("please select a field card from your deck");  //TODO bring to view
        PreCard preCard = gamePlay.getSelectedPreCard();
        if (preCard == null)
            throw new NoSelectedCard();
        else if (!(preCard instanceof PreSpellTrapCard))
            throw new NotAppropriateCard("field spell");
        else if (((PreSpellTrapCard) preCard).getIcon() != CardIcon.FIELD)
            throw new NotAppropriateCard("field spell");
        else {
            myPlayer.getBoard().getDeckInUse().removeCard(preCard, false);
            myPlayer.getHand().addCard(preCard);
        }
    }
}
