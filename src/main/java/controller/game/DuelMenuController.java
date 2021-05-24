//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package controller.game;

import controller.LoginMenuController;
import exceptions.*;
import model.Deck;
import model.Enums.Phase;
import model.Enums.ZoneName;
import model.User;
import view.Menu;
import view.Menus.DuelMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class DuelMenuController {
    private static boolean isAnyGameRunning = false;

    private User firstUser;
    private User secondUser;

    private ArrayList<HashMap<User, Integer>> usersLP;
    private ArrayList<User> roundsWinner;

    private Phase currentPhase;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private DrawPhaseController drawPhaseController;

    private RoundController roundController;
    private int numOfRounds;

    public DuelMenuController(User firstUser, User secondUser, int numOfRounds) throws NumOfRounds {
        setFirstUser(firstUser);
        setSecondUser(secondUser);
        setNumOfRounds(numOfRounds);
        this.roundsWinner = new ArrayList<>();
        this.usersLP = new ArrayList<>();
        for (int i = 0; i < numOfRounds; i++) {
            usersLP.add(new HashMap<>());
        }
    }

    /*initializing the match*/

    public static DuelMenuController newDuel(String secondUserName, int numOfRounds) throws InvalidName, NumOfRounds, InvalidDeck, NoActiveDeck {
        User secondUser = User.getUserByName(secondUserName);
        User firstUser = LoginMenuController.getCurrentUser();

        if (isGameValid(firstUser, secondUser)) {
            DuelMenuController duelMenuController = new DuelMenuController(firstUser, secondUser, numOfRounds);
            isAnyGameRunning = true;
            return duelMenuController;
        }
        return null;
    }

    private static boolean isGameValid(User firstUser, User secondUser) throws InvalidName, NoActiveDeck, InvalidDeck {
        if (firstUser == null || secondUser == null)
            throw new InvalidName("user", "username");
        if (firstUser.getActiveDeck() == null) throw new NoActiveDeck(firstUser.getUsername());
        if (secondUser.getActiveDeck() == null) throw new NoActiveDeck(secondUser.getUsername());
        if (!isDeckValid(firstUser.getActiveDeck())) throw new InvalidDeck(firstUser.getUsername());
        if (!isDeckValid(secondUser.getActiveDeck())) throw new InvalidDeck(secondUser.getUsername());
        return true;
    }

    private static boolean isDeckValid(Deck deck) {
        return true;//todo
    }


    /*match actions*/

    public void runMatch() throws InvalidCommand, InvalidDeck, NoActiveDeck, InvalidName {
//        playHeadOrTails(); //todo
        for (int i = 0; i < numOfRounds; i++) {
            runOneRound(i);
            if (checkMatchFinished()) break;
        }
        isAnyGameRunning = false;
        announceWinnerOfMatch();
    }

    private void runOneRound(int roundIndex) throws InvalidCommand, InvalidName, NoActiveDeck, InvalidDeck {
        this.roundController = new RoundController(this.firstUser, this.secondUser, this, roundIndex);
        while (!roundController.isRoundEnded()) {
            while (!roundController.isTurnEnded()) {
                DuelMenu.checkCommandsInGame();
            }
            swapUsers();
        }
        roundController.announceRoundWinner();
    }

    private void swapUsers() {
        User hold = this.firstUser;
        this.firstUser = this.secondUser;
        this.secondUser = hold;
    }

    public void handleRoundWinner(User winner, User loser, int winnerLP, int loserLP, int winnerScore, int loserScore, int roundIndex) {
        this.usersLP.get(roundIndex).put(winner, winnerLP);
        this.usersLP.get(roundIndex).put(loser, loserLP);
        this.roundsWinner.add(winner);
        DuelMenu.showWinner(winner.getUsername(), winnerScore, loserScore, false);
    }

    public void announceWinnerOfMatch() {
        int firstUserWins = 0, secondUserWins = 0;
        for (int i = 0; i < this.numOfRounds; i++) {
            if (roundsWinner.get(i) != null) {
                if (roundsWinner.get(i).equals(firstUser)) firstUserWins++;
                else if (roundsWinner.get(i).equals(secondUser)) secondUserWins++;
            }
        }
        User matchWinner, matchLoser;
        if (firstUserWins > secondUserWins) {
            matchWinner = firstUser;
            matchLoser = secondUser;
            DuelMenu.showWinner(matchWinner.getUsername(), firstUserWins, secondUserWins, true);
        } else {
            matchWinner = secondUser;
            matchLoser = firstUser;
            DuelMenu.showWinner(matchWinner.getUsername(), secondUserWins, firstUserWins, true);
        }
        handleScoreAndBalance(matchWinner, matchLoser);
    }

    private void handleScoreAndBalance(User matchWinner, User matchLoser) {
        matchWinner.increaseScore(1000 * numOfRounds);
        matchLoser.increaseBalance(100 * numOfRounds);
        matchWinner.increaseBalance(1000 * numOfRounds);
        int maxWinnerLP = 0;
        for (int i = 0; i < this.roundController.getRoundIndex(); i++) {
            int roundLP = usersLP.get(i).get(matchWinner);
            if (roundLP > maxWinnerLP) maxWinnerLP = roundLP;
        }
        matchWinner.increaseBalance(numOfRounds * maxWinnerLP);
    }

    private boolean checkMatchFinished() {
        if (this.roundController.getRoundIndex() != 1) return false;
        return this.roundsWinner.get(0).equals(this.roundsWinner.get(1));
    }


    public boolean isIsAnyGameRunning() {
        return isAnyGameRunning;
    }

    private void setNumOfRounds(int numOfRounds) throws NumOfRounds {
        if (numOfRounds != 1 && numOfRounds != 3) throw new NumOfRounds();
        this.numOfRounds = numOfRounds;
    }

    private void setFirstUser(User user) {
        this.firstUser = user;
    }

    private void setSecondUser(User user) {
        this.secondUser = user;
    }

    public void setDrawPhase(DrawPhaseController draw, Phase gamePhase) {
        this.drawPhaseController = draw;
        currentPhase = gamePhase;
    }


    /* actions in a round*/

    public static String askQuestion(String questionToAsk) {
        return DuelMenu.askQuestion(questionToAsk);
    }

    public String askForSth(String wanted) throws InvalidTributeAddress, NoCardFound, InvalidSelection {
        return DuelMenu.askForSth(wanted);
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
                zoneName = ZoneName.getZoneName(field);
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
        roundController.selectCard(zoneName, isOpponent, cardIndex);
    }

    public void deselectCard() throws NoSelectedCard {
        if (roundController.isAnyCardSelected()) {
            roundController.deselectCard();
        } else throw new NoSelectedCard();
    }

    public void showGraveYard(boolean ofCurrentPlayer) {
        roundController.showGraveYard(ofCurrentPlayer);
    }

    public void showCard() {//todo
    }

    public void surrender() {
        roundController.surrender();
    }

    public void nextPhase() {
        this.currentPhase = currentPhase.goToNextGamePhase();
        switch (Objects.requireNonNull(currentPhase)) {
            case DRAW:
                break;
            case STANDBY:
                this.standByPhaseController = new StandByPhaseController(roundController);
                break;
            case MAIN_1:
            case MAIN_2:
                this.mainPhaseController = new MainPhaseController(roundController);
                break;
            case BATTLE:
                this.battlePhaseController = new BattlePhaseController(roundController);
                break;
            case END:
                this.roundController.setTurnEnded(true);
        }
        DuelMenu.showPhase(currentPhase.toString());
    }

}
