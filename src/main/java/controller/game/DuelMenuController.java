//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package controller.game;

import exceptions.WrongPhaseForAction;
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
        drawPhaseController = new DrawPhaseController();
        currentPhase = Phase.DRAW;

    }

    public static DuelMenuController newDuel() {
        return null;//todo: create the game play controller inside the duel menu controller(by having the users)
    }

    public static String askQuestion(String questionToAsk) {
        return DuelMenu.askQuestion(questionToAsk);
    }

    public void summonMonster(boolean isFlip) throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
    }

    public void setCard() throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();

    }

    public void setPosition(boolean isToBeAttackMode) throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
    }


    public void attack(int number) throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();

    }

    public void attackDirect() throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();
    }

    public void activateEffect() throws WrongPhaseForAction {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
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
        switch (Objects.requireNonNull(currentPhase)) {
            case DRAW:
                break;
            case STANDBY:
                this.standByPhaseController = new StandByPhaseController();
                break;
            case MAIN_1:
            case MAIN_2:
                this.mainPhaseController = new MainPhaseController(gamePlayController);
                break;
            case BATTLE:
                this.battlePhaseController = new BattlePhaseController();
                break;
        }
        Print.print("phase: " + currentPhase.toString());
    }


}
