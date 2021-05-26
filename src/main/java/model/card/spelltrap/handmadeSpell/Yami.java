//Field card
package model.card.spelltrap.handmadeSpell;

import controller.game.RoundController;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.MonsterType;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

import java.util.ArrayList;

public class Yami extends SpellTrap {
    public ArrayList<MonsterCardInUse> thisCardWatching;

    {
        thisCardWatching = new ArrayList<>();
    }

    public Yami(PreCard preCard) {
        super(preCard);
        setName("Yami");
    }

    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) {
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
            if (targetMonsterType == MonsterType.FIEND ||
                    targetMonsterType == MonsterType.SPELLCASTER) {
                monsterCardInUse.addToAttack(200);
                monsterCardInUse.addToDefense(200);
                thisCardWatching.add(monsterCardInUse);
            } else if (targetMonsterType == MonsterType.FAIRY) {
                monsterCardInUse.addToDefense(-200);
                monsterCardInUse.addToAttack(-200);
                thisCardWatching.add(monsterCardInUse);
            }
        }
    }

    @Override   //TODO should deactive it's effect
    public void destroyThis(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) {

    }
}