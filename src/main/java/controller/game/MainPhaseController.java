package controller.game;


import exeptions.*;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.PreMonsterCard;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

class MainPhaseController {
    private Player player;
    private GamePlayController controller;
    private ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards

    {
        summonedInThisPhase = new ArrayList<>();
    }

    public MainPhaseController(Player player, GamePlayController controller) {
        this.player = player;
        this.controller = controller;
    }

    private PreCard getSelectedPreCard() throws NoSelectedCard {
        PreCard selectedCard = this.controller.getCurrentPlayerSelectedCard();
        if (selectedCard == null) throw new NoSelectedCard();
        return selectedCard;
    }

    private MonsterCardInUse getSelectedMonsterZoneCardInUse(PreCard selectedCard) throws UnableToChangePosition {
        if (!(selectedCard instanceof PreMonsterCard)) throw new UnableToChangePosition();
        MonsterCardInUse[] zone = player.getBoard().getMonsterZone();
        for (MonsterCardInUse monsterCardInUse : zone) {
            if (monsterCardInUse.getThisCard().getPreCardInGeneral().equals(selectedCard)) return monsterCardInUse;
        }
        throw new UnableToChangePosition();
    }


    /* this function doesn't handle flip summon */
    private void summonMonster() throws NoSelectedCard, UnableToSummonMonster, FullZone, AlreadyDoneAction, NotEnoughTributes {
        PreCard selectedCard = getSelectedPreCard();
        /* checking the errors */
        if (!player.getHand().doesContainCard(selectedCard)
                || !(selectedCard instanceof PreMonsterCard)) {
            throw new UnableToSummonMonster(" ");
        }
        if (player.getBoard().isMonsterZoneFull()) throw new FullZone(true);

        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        SummonController summonController = new SummonController(monsterCardInUse, (PreMonsterCard) selectedCard, controller, summonedInThisPhase);
        summonController.run();

    }


    private void changePosition(boolean isToBeAttackMode) throws NoSelectedCard, UnableToChangePosition, AlreadyDoneAction, AlreadyInWantedPosition {
        PreCard selectedCard = getSelectedPreCard();
        MonsterCardInUse monsterCardInUse = getSelectedMonsterZoneCardInUse(selectedCard);

        if (monsterCardInUse.isPositionChanged()) throw new AlreadyDoneAction("changed this card position");

        if (isToBeAttackMode) {
            if (monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(true);
        } else {
            if (!monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(false);
        }
    }


    public void flipSummon() throws NoSelectedCard, UnableToChangePosition, UnableToSummonMonster {
        PreCard selectedCard = getSelectedPreCard();
        MonsterCardInUse monsterCardInUse = getSelectedMonsterZoneCardInUse(selectedCard);
        if (summonedInThisPhase.contains(monsterCardInUse))
            throw new UnableToSummonMonster("flip ");
        if (monsterCardInUse.isFaceUp() || monsterCardInUse.isInAttackMode())
            throw new UnableToSummonMonster("flip ");

        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setAttacking(false);
        new SuccessfulAction("", "flip summoned");
    }


    public void setSelected() {

    }

    private void setTrap() {

    }

    private void setSpell() {

    }

    private void setMonster() {

    }

    public void activateEffect() {

    }
}
