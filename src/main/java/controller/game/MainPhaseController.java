package controller.game;


import exeptions.*;
import model.Cell;
import model.Player;
import model.card.PreCard;
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
            Cell cellToSummon = player.getBoard().getFirstEmptyCell(true);
            SummonController summonController = new SummonController(cellToSummon, selectedCard, summonType, controller);
            summonController.run();
        }

    }


    private void changePosition() {

    }

    private void flipSummon(Cell cell) {

    }

    private void activeEffect() {

    }

    private void setTrapOrSpell(Cell cell) {

    }

    private void tributeCard() {

    }

    public void activateTrap() {

    }
}
