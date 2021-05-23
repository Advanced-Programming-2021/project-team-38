//will be activated in main phase 2
//monstersInMyBoard must be counted before battle phase
package model.card.spelltrap.handmadeSpell;

import controller.game.GamePlayController;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.spelltrap.SpellTrap;

public class SupplySquad extends SpellTrap {
    private int monstersInMyBoard;

    public SupplySquad(PreCard preCard) {
        super(preCard);
        setName("Supply Squad");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {
        if (canActiveEffect(myPlayer)) {
            PreCard takenPreCard = myPlayer.takeACardFromDeck();
            myPlayer.getHand().addCard(takenPreCard);
        }
    }

    public boolean canActiveEffect(Player myPlayer) {
        //count monsters in my board
        int count = 0;
        for (MonsterCardInUse monsterCardInUse : myPlayer.getBoard().getMonsterZone()) {
            if (!monsterCardInUse.isCellEmpty())
                count++;
        }

        return monstersInMyBoard - count > 0;
    }
}
