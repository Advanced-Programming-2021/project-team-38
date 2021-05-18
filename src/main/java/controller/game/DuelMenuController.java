//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package controller.game;

import controller.LoginMenuController;
import exceptions.*;
import model.Enums.Phase;
import model.Enums.ZoneName;
import model.User;
import view.Menu;
import view.Menus.DuelMenu;
import view.Print;

import java.util.Objects;
import java.util.regex.Matcher;

public class DuelMenuController {
    private Phase currentPhase;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private DrawPhaseController drawPhaseController;
    private GamePlayController gamePlayController;
    private int numOfRounds;
    private static boolean isAnyGameRunning = false;


    public static DuelMenuController newDuel(String secondUserName, int numOfRounds) throws InvalidName, NumOfRounds, InvalidDeck, NoActiveDeck {
        User secondUser = User.getUserByName(secondUserName);
        if (secondUser == null) throw new InvalidName("player", "username");

        DuelMenuController duelMenuController = new DuelMenuController();
        GamePlayController gamePlayController = new GamePlayController(LoginMenuController.getCurrentUser(), secondUser, duelMenuController);
        duelMenuController.setNumOfRounds(numOfRounds);
        duelMenuController.setGamePlayController(gamePlayController);
        isAnyGameRunning = true;
        return duelMenuController;
    }

    public boolean isIsAnyBattleRunning() {
        return isAnyGameRunning;
    }

    private void setGamePlayController(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    private void setNumOfRounds(int numOfRounds) throws NumOfRounds {
        if (numOfRounds != 1 && numOfRounds != 3) throw new NumOfRounds();
        this.numOfRounds = numOfRounds;
    }

    public void setDrawPhase(DrawPhaseController draw, Phase gamePhase) {
        this.drawPhaseController = draw;
        currentPhase = gamePhase;
    }

    public static String askQuestion(String questionToAsk) {
        return DuelMenu.askQuestion(questionToAsk);
    }

    public void askForSth(String wanted) throws InvalidTributeAddress, NoCardFound, InvalidSelection {
        selectCard(DuelMenu.askForSth(wanted));
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

    public void changePosition(boolean isToBeAttackMode) throws WrongPhaseForAction, AlreadyDoneAction, UnableToChangePosition, AlreadyInWantedPosition, NoSelectedCard, CantDoActionWithCard {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.changePosition(isToBeAttackMode);
    }

    public void attack(int number) throws WrongPhaseForAction, CardAttackedBeforeExeption, CardCantAttack, NoCardToAttack, NoSelectedCard {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();

        battlePhaseController.battleAnnounced(number);

    }

    public void attackDirect() throws WrongPhaseForAction, CardAttackedBeforeExeption, CardCantAttack, CantAttackDirectlyException, NoSelectedCard {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();
        battlePhaseController.attackToLifePoint();
    }

    public void activateEffect() throws WrongPhaseForAction, NoSelectedCard, ActivateEffectNotSpell, BeingFull, SpellPreparation, AlreadyActivatedEffect, CantDoActionWithCard {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.activateEffect();
    }

    public void selectCard(String cardAddress) throws InvalidTributeAddress, InvalidSelection, NoCardFound {
        cardAddress = cardAddress.concat(" ");
        boolean isOpponent = false;
        ZoneName zoneName = null;
        Matcher flagMatcher = Menu.getCommandMatcher(cardAddress, "--(<field>\\S+) ");
        while (flagMatcher.find()) {
            String field = flagMatcher.group("field");
            if (field.equals("opponent")) {
                if (!isOpponent) isOpponent = true;
                else throw new InvalidSelection();
            } else {
                if (zoneName != null) throw new InvalidSelection();
                switch (field) {
                    case "hand":
                        zoneName = ZoneName.HAND;
                        break;
                    case "monster":
                        zoneName = ZoneName.MONSTER;
                        break;
                    case "spell":
                        zoneName = ZoneName.SPELL;
                        break;
                    case "field":
                        zoneName = ZoneName.FIELD;
                        break;
                    default:
                        throw new InvalidSelection();
                }
            }
        }
        if (zoneName == null) throw new InvalidSelection();
        int cardIndex;
        try {
            cardIndex = Integer.parseInt(cardAddress);
        } catch (Exception e) {
            if (!zoneName.equals(ZoneName.FIELD))
                throw new InvalidSelection();
            cardIndex = -1;
        }
        gamePlayController.selectCard(zoneName, isOpponent, cardIndex);
    }

    public void deselectCard() throws NoSelectedCard {
        if (gamePlayController.isAnyCardSelected()) {
            gamePlayController.deselectedCard();
        } else throw new NoSelectedCard();
    }

    public void showGraveYard(boolean ofCurrentPlayer) {

    }

    public void showCard() {

    }

    public void nextPhase() {
        this.currentPhase = currentPhase.goToNextGamePhase();
        switch (Objects.requireNonNull(currentPhase)) {
            case DRAW:
                break;
            case STANDBY:
                this.standByPhaseController = new StandByPhaseController(gamePlayController);
                break;
            case MAIN_1:
            case MAIN_2:
                this.mainPhaseController = new MainPhaseController(gamePlayController);
                break;
            case BATTLE:
                this.battlePhaseController = new BattlePhaseController(gamePlayController);
                break;
            case END:
                this.gamePlayController.setTurnEnded(true);
        }
        Print.print("phase: " + currentPhase.toString());
    }

    public void runGame() throws InvalidCommand {
        for (int i = 0; i < numOfRounds; i++) {
            gamePlayController.setRoundEnded(false);
            runOneRound();
            //todo: print that one round is ended and we want to go to the next round
        }

    }

    private void runOneRound() throws InvalidCommand {
        while (!gamePlayController.isRoundEnded()) {
            while (!gamePlayController.isTurnEnded()) {
                DuelMenu.checkCommandsInGame();
            }
            gamePlayController.swapPlayers();
            if (!gamePlayController.isRoundEnded()) gamePlayController.setTurnEnded(false);
        }
    }


    //from gameplay to duel menu controller

}
