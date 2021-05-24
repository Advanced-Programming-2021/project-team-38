package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.RoundController;
import exceptions.*;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

import static controller.game.SummonController.specialSummonPreCard;

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
        PreCard preCard = gamePlay.getSelectedPreCard();
        if (preCard == null)
            throw new NoSelectedCard();
        else if (!(preCard instanceof PreMonsterCard))
            throw new NotAppropriateCard("monster");
        else {
            specialSummonPreCard(preCard, myPlayer);
            if (isSelectedFromOtherPlayerBoard)
                rivalPlayer.getBoard().getGraveYard().removeCard(preCard);
            else myPlayer.getBoard().getGraveYard().removeCard(preCard);
        }
    }
}
