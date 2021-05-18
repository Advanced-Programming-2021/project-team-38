package model.card.spelltrap.handmadeSpell;

import controller.game.DuelMenuController;
import controller.game.GamePlayController;
import exceptions.NoSelectedCard;
import exceptions.NotAMonsterCard;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

public class MonsterReborn extends SpellTrap {

    public MonsterReborn(PreCard preCard) {
        super(preCard);
        setName("Monster Reborn");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {
        DuelMenuController duelMenu = gamePlay.getDuelMenu();
        gamePlay.deselectedCard();
        duelMenu.askForSth("please select a card from monster zone");  //TODO bring to view
        PreCard preCard = gamePlay.getSelectedPreCard();
        if (preCard == null)
            throw new NoSelectedCard();
        else if (!(preCard instanceof PreMonsterCard))
            throw new NotAMonsterCard();

    }
}
