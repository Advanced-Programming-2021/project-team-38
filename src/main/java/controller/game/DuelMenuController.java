//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package controller.game;

import exceptions.*;
import model.Enums.Phase;
import view.Menus.DuelMenu;
import view.Print;

import java.util.Objects;

public class DuelMenuController {
    private Phase currentPhase;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private DrawPhaseController drawPhaseController;
    private GamePlayController gamePlayController;

    {
//        drawPhaseController = new DrawPhaseController(gamePlayController);todo: I think it should be done in the newDuel function.
        currentPhase = Phase.DRAW;

    }

    public void setPhases(MainPhaseController main, BattlePhaseController battle,
                          StandByPhaseController standBy, DrawPhaseController draw) {
        this.mainPhaseController = main;
        this.battlePhaseController = battle;
        this.standByPhaseController = standBy;
        this.drawPhaseController = draw;
    }

    public static DuelMenuController newDuel() {
        return null;//todo: create the game play controller inside the duel menu controller(by having the users)
    }

    public static String askQuestion(String questionToAsk) {
        return DuelMenu.askQuestion(questionToAsk);
    }

    public void summonMonster(boolean isFlip) throws WrongPhaseForAction, CantDoActionWithCard, UnableToChangePosition, NoSelectedCard, BeingFull, AlreadyDoneAction, NotEnoughTributes {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        if (isFlip) mainPhaseController.flipSummon();
        else mainPhaseController.summonMonster();
    }

    public void setCard() throws WrongPhaseForAction, BeingFull, AlreadyDoneAction, CantDoActionWithCard, NoSelectedCard {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.setCard();
    }

    public void changePosition(boolean isToBeAttackMode) throws WrongPhaseForAction, AlreadyDoneAction, UnableToChangePosition, AlreadyInWantedPosition, NoSelectedCard {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.changePosition(isToBeAttackMode);
    }


    public void attack(int number) throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();
//        battlePhaseController.attack(number); todo: hasti

    }

    public void attackDirect() throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();
        battlePhaseController.attackToLifePoint();
    }

    public void activateEffect() throws WrongPhaseForAction, NoSelectedCard, ActivateEffectNotSpell, BeingFull, SpellPreparation, AlreadyActivatedEffect, CantDoActionWithCard {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.activateEffect();
    }

    public void selectCard(String cardAddress) {

    }

    public void deselectCard() {

    }

    public void showGraveYard(boolean ofCurrentPlayer) {

    }

    public void showCard() {

    }


    public void nextPhase() {
        this.currentPhase = currentPhase.goToNextPhase();
//        switch (Objects.requireNonNull(currentPhase)) {
//            case DRAW:
//                break;
//            case STANDBY:
//                this.standByPhaseController = new StandByPhaseController();
//                break;
//            case MAIN_1:
//            case MAIN_2:
//                this.mainPhaseController = new MainPhaseController(gamePlayController);
//                break;
//            case BATTLE:
//                this.battlePhaseController = new BattlePhaseController();
//                break;
//        } TODO check with negar
        Print.print("phase: " + currentPhase.toString());
    }

    //from gameplay to duel menu controller


}
