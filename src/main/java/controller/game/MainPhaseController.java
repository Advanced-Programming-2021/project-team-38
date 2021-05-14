package controller.game;


import exeptions.*;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.PreMonsterCard;

class MainPhaseController {
    private Player player;
    private GamePlayController controller;

    public MainPhaseController(Player player, GamePlayController controller) {
        this.player = player;
        this.controller = controller;
    }


    private void summonMonster(SummonType summonType) throws NoSelectedCard, UnableToSummonMonster, FullZone, AlreadyDoneAction, NotEnoughTributes {
        PreCard selectedCard = this.controller.getCurrentPlayerSelectedCard();
        if (selectedCard == null) {
            throw new NoSelectedCard();
        }
        if (!player.getHand().doesContainCard(selectedCard)
                || !(selectedCard instanceof PreMonsterCard)) {
            throw new UnableToSummonMonster();
        }
        if (player.getBoard().isMonsterZoneFull()) {
            throw new FullZone(true);
        } else {
            MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
            SummonController summonController = new SummonController(monsterCardInUse, (PreMonsterCard) selectedCard, summonType, controller);
            summonController.run();
        }

    }


    private void changePosition() {

    }

    private void flipSummon() {

    }

    private void activeEffect() {

    }

    private void setTrapOrSpell() {

    }

    private void tributeCard() {

    }

    public void activateTrap() {

    }
}
