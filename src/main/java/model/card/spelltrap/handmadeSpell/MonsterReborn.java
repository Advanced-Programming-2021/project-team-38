package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.RoundController;
import exceptions.*;
import model.Player;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.spelltrap.SpellTrap;

public class MonsterReborn extends SpellTrap {

    public MonsterReborn(PreCard preCard) {
        super(preCard);
        setName("Monster Reborn");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay)
            throws NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, BeingFull {
        DuelMenuController duelMenu = gamePlay.getDuelMenuController();
        gamePlay.deselectCard();
        String selectCardCommand = duelMenu.askForSth("please select a monster from graveyard");  //TODO bring to view
        boolean isSelectedFromOtherPlayerBoard = selectCardCommand.contains("--opponent");
        duelMenu.selectCard(selectCardCommand);
        Card card = gamePlay.getSelectedCard();
        if (card == null)
            throw new NoSelectedCard();
        else if (!(card instanceof Monster))
            throw new NotAppropriateCard("monster");
        else {
//            specialSummon((Monster) card, myPlayer);
            if (isSelectedFromOtherPlayerBoard)
                rivalPlayer.getBoard().getGraveYard().removeCard(card);
            else myPlayer.getBoard().getGraveYard().removeCard(card);
        }
    }
}
