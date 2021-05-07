package controller.game;


import exeptions.FullZone;
import exeptions.NoSelectedCard;
import exeptions.UnableToSummonMonster;
import model.Cell;
import model.Player;
import model.card.Card;
import model.card.monster.Monster;

import javax.management.MalformedObjectNameException;

class MainPhaseController {
    private Card selectedCard = null;
    private Player player;

    public MainPhaseController(Player player) {
        this.player = player;
    }

    public void setSelectedCard(Card card) {
        this.selectedCard = card;
    }

    public String run() {
        return null;
    }

    private void summonMonster() {
        if (selectedCard == null) {
            new NoSelectedCard();
            return;
        }
        if (!player.getHand().doesContainCard(selectedCard)
                || !(selectedCard instanceof Monster)) {
            new UnableToSummonMonster();
            return;
        }
        if (player.getBoard().isMonsterZoneFull()) {
            new FullZone(true);
            return;
        }

    }

    private String setMonster() {
        return null;
    }

    private String changePosition() {
        return null;
    }

    private String flipSummon(Cell cell) {
        return null;
    }

    private String activeEffect() {
        return null;
    }

    private String setTrapOrSpell(Cell cell) {
        return null;
    }

    private String tributeCard() {
        return null;
    }

    public void activateTrap() {

    }
}
