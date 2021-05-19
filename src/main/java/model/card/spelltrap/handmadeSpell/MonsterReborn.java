package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.GamePlayController;
import controller.game.SummonController;
import exceptions.*;
import model.Enums.ZoneName;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

public class MonsterReborn extends SpellTrap {

    public MonsterReborn(PreCard preCard) {
        super(preCard);
        setName("Monster Reborn");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) throws NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, CloneNotSupportedException {
        DuelMenuController duelMenu = gamePlay.getDuelMenu();
        gamePlay.deselectedCard();
        String selectCardCommand = duelMenu.askForSth("please select a monster from graveyard");  //TODO bring to view
        boolean isSelectedFromOtherPlayerBoard = false;
        if (selectCardCommand.contains("--opponent")) isSelectedFromOtherPlayerBoard = true;
        duelMenu.selectCard(selectCardCommand);
        PreCard preCard = gamePlay.getSelectedPreCard();
        if (preCard == null)
            throw new NoSelectedCard();
        else if (!(preCard instanceof PreMonsterCard))
            throw new NotAppropriateCard("monster");
        else  {
            SummonController.specialSummonPreCard(gamePlay, preCard, myPlayer, ZoneName.MONSTER);
            if (isSelectedFromOtherPlayerBoard)
            rivalPlayer.getBoard().getGraveYard().removeCard(preCard);
            else
            myPlayer.getBoard().getGraveYard().removeCard(preCard);
        }
    }
}
