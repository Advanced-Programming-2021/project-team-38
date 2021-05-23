//Field card
package model.card.spelltrap.handmadeSpell;

import controller.game.GamePlayController;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.MonsterType;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

import java.util.ArrayList;

public class Forest extends SpellTrap {
    public ArrayList<MonsterCardInUse> thisCardWatching;

    {
        thisCardWatching = new ArrayList<>();
    }

    public Forest(PreCard preCard) {
        super(preCard);
        setName("Forest");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {
        for (MonsterCardInUse monsterCardInUse : myPlayer.getBoard().getMonsterZone()) {
            monsterCardInUse.addToAreWatchingYou(this);
            watch(monsterCardInUse, myPlayer, rivalPlayer);
        }
    }

    @Override
    public void watch(MonsterCardInUse monsterCardInUse, Player myPlayer, Player rivalPlayer) {
        if (!thisCardWatching.contains(monsterCardInUse)) {
            MonsterType targetMonsterType = ((PreMonsterCard) monsterCardInUse.getThisCard().getPreCardInGeneral())
                    .getMonsterType();
            if (targetMonsterType == MonsterType.INSECT ||
                    targetMonsterType == MonsterType.BEAST_WARRIOR ||
                    targetMonsterType == MonsterType.BEAST) {
                monsterCardInUse.addToAttack(200);
                monsterCardInUse.addToDefense(200);
                thisCardWatching.add(monsterCardInUse);
            }
        }
    }

    @Override   //TODO should deactive it's effect
    public void destroyThis(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {

    }
}
