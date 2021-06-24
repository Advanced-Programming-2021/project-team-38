package model.card.spelltrap.handmadeSpell;

import controller.game.MainPhaseController;
import controller.game.RoundController;
import view.exceptions.*;
import model.Board;
import model.Player;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.SpellTrap;

import java.util.ArrayList;

public class AdvancedRitualArt extends SpellTrap {
    private MainPhaseController controller;

    public AdvancedRitualArt(PreCard preCard) {
        super(preCard);
        setName("Advanced Ritual Art");
        super.shouldDieAfterActivated = true;
    }

    private void setController(MainPhaseController controller) {
        this.controller = controller;
    }

    public boolean areEnoughTributesForLevel(int level, Board board) {
        if (board == null) return false;
        ArrayList<Integer> levels = new ArrayList<>();
        for (MonsterCardInUse monsterCardInUse : board.getMonsterZone()) {
            Card card = monsterCardInUse.getThisCard();
            if (card instanceof Monster) {
                PreMonsterCard preMonsterCard = (PreMonsterCard) monsterCardInUse.getThisCard().getPreCardInGeneral();
                levels.add(preMonsterCard.getLevel());
            }
        }
        return canFindSubsetOfSum(levels, level);
    }

    private boolean canFindSubsetOfSum(ArrayList<Integer> set, int sum) {
        if (sum < 0) return false;
        if (sum == 0) return true;
        if (set.isEmpty()) return false;
        int element = set.get(0);
        if (element < 0) return false;
        set.remove(element);
        if (canFindSubsetOfSum(set, sum)) return true;
        return canFindSubsetOfSum(set, sum - element);
    }

    @Override
    public boolean areEffectPreparationsDone(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController roundController) {
        if (myPlayer == null || rivalPlayer == null || thisCard == null || roundController == null) return false;
        this.setController(roundController.getDuelMenuController().getMainPhaseController());
        if (controller == null) return false;

        ArrayList<Monster> goodMonsters = myPlayer.getHand().getMonstersOfType(MonsterCardType.RITUAL);
        for (Monster goodMonster : goodMonsters) {
            if (areEnoughTributesForLevel(goodMonster.getLevel(), myPlayer.getBoard())) return true;
        }
        return false;
    }


    @Override
    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, RoundController gamePlay) throws PreparationsNotChecked, BeingFull, NotEnoughTributes, AlreadyDoneAction, CantDoActionWithCard {
//        if (!this.areEffectPreparationsDone(myPlayer, rivalPlayer, thisCard, gamePlay))
//            throw new PreparationsNotChecked();
//        boolean isCancelled = controller.handleRitualSummon();
//        if (isCancelled) return;
//        this.setActivated(true); //todo
    }

}
