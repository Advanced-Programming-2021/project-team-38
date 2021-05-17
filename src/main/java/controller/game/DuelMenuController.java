//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package controller.game;

import exceptions.*;
import model.Enums.Phase;
import model.Enums.ZoneName;
import view.Menu;
import view.Menus.DuelMenu;
import view.Print;

import java.util.Objects;
import java.util.regex.Matcher;

import java.util.Objects;

public class DuelMenuController {
    private Phase currentPhase;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private DrawPhaseController drawPhaseController;
    private GamePlayController gamePlayController;


    public void setDrawPhase(DrawPhaseController draw, Phase gamePhase) {
        this.drawPhaseController = draw;
        currentPhase = gamePhase;
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

    public void deselectCard() {

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
        }
        Print.print("phase: " + currentPhase.toString());
    }

    //from gameplay to duel menu controller

}
