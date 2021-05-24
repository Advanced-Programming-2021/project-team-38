package model.card.spelltrap.handmadeSpell;

import controller.game.RoundController;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.spelltrap.SpellTrap;

public class DarkHole extends SpellTrap {

    public DarkHole(PreCard preCard) {
        super(preCard);
        setName("Dark Hole");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) {
        for (MonsterCardInUse monsterCardInUse : myPlayer.getBoard().getMonsterZone()) {
            ((Monster) monsterCardInUse.getThisCard()).destroyThis(
                    myPlayer.getBoard(), rivalPlayer.getBoard(), null, monsterCardInUse, 0);
        }

        for (MonsterCardInUse monsterCardInUse : rivalPlayer.getBoard().getMonsterZone()) {
            ((Monster) monsterCardInUse.getThisCard()).destroyThis(
                    myPlayer.getBoard(), rivalPlayer.getBoard(), null, monsterCardInUse, 0);
        }
    }
}
