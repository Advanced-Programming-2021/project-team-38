//any input should be handled here, the phase is checked here, and the gamePlay is called from here

package controller.game;

import controller.LoginMenuController;
import lombok.Getter;
import model.*;
import model.Enums.Phase;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;
import model.card.monster.MonsterManner;
import model.watchers.Watcher;
import view.Menus.DuelMenu;
import view.Menus.RelatedToMenu;
import view.Print;
import view.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;


@Getter
public class DuelMenuController {
    private static boolean isAnyGameRunning = false;

    private User firstUser;
    private User secondUser;

    private final ArrayList<HashMap<User, Integer>> usersLP; //the i th member of list, is a hashmap that shows the lp of each user in the i th round
    private final ArrayList<User> roundsWinner;

    private Phase currentPhase;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private DrawPhaseController drawPhaseController;

    private boolean canHaveBattlePhase = false;

    private RoundController roundController;
    private int numOfRounds;
    boolean hasSecondPlayerTurnStarted = false;

    private DuelMenuController(User firstUser, User secondUser, int numOfRounds) throws NumOfRounds {
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

    public static DuelMenuController newDuel(String secondUserName, int numOfRounds) throws InvalidName, NumOfRounds, InvalidDeck, NoActiveDeck, InvalidThing {
        User secondUser = User.getUserByName(secondUserName);
        User firstUser = LoginMenuController.getCurrentUser();

        if (isGameValid(firstUser, secondUser)) {
            DuelMenuController duelMenuController = new DuelMenuController(firstUser, secondUser, numOfRounds);
            isAnyGameRunning = true;
            return duelMenuController;
        }
        return null;
    }

    private static boolean isGameValid(User firstUser, User secondUser) throws InvalidName, NoActiveDeck, InvalidDeck, InvalidThing {
        if (firstUser == null || secondUser == null)
            throw new InvalidName("user", "username");
        if (firstUser.equals(secondUser)) throw new InvalidThing("second username");
        checkValidityOfUser(firstUser);
        checkValidityOfUser(secondUser);
        return true;
    }

    private static void checkValidityOfUser(User user) throws NoActiveDeck, InvalidDeck {
        Deck deck = user.getActiveDeck();
        if (deck == null) throw new NoActiveDeck(user.getUsername());
        if (Deck.isDeckInvalid(deck)) throw new InvalidDeck(user.getUsername());
        ArrayList<PreCard> allCards = new ArrayList<>();
        allCards.addAll(deck.getMainCards());
        allCards.addAll(deck.getSideCards());
        for (PreCard preCard : allCards) {
            if (!user.getCardTreasury().containsKey(preCard.getName()))
                throw new InvalidDeck(user.getUsername());
            if (Collections.frequency(allCards, preCard)
                    > user.getCardTreasury().get(preCard.getName()))
                throw new InvalidDeck(user.getUsername());
        }
    }

//    public static void main(String[] args) {
//        ArrayList<PreCard> hello = new ArrayList<>(Arrays.asList(PreCard.findCard("Command Knight"), PreCard.findCard("Command Knight")));
//        System.out.println(Collections.frequency(hello, PreCard.findCard("Command Knight")));
//    }


    /*match actions*/

    public void runMatch() throws InvalidDeck, NoActiveDeck, InvalidName {
        this.roundController = new RoundController(this.firstUser, this.secondUser, this, 0);
        playHeadOrTails();
        for (int i = 0; i < numOfRounds; i++) {
            runOneRound(i);
            if (checkMatchFinished()) break;
            exchangeCardInDecks(roundController.getCurrentPlayer());
            exchangeCardInDecks(roundController.getRival());
        }
        isAnyGameRunning = false;
        announceWinnerOfMatch();
    }

    private void runOneRound(int roundIndex) throws InvalidName, NoActiveDeck, InvalidDeck {
        if (roundIndex != 0)
            this.roundController = new RoundController(this.firstUser, this.secondUser, this, roundIndex);
        nextPhase();

        Watcher.roundController = this.roundController;
        while (!roundController.isRoundEnded()) {
            while (!roundController.isTurnEnded()) {
                DuelMenu.checkCommandsInRound();
            }
            swapPlayers();
            if (!hasSecondPlayerTurnStarted) {
                currentPhase = null;
                hasSecondPlayerTurnStarted = true;
            }
            nextPhase();
        }
        roundController.announceRoundWinner();
    }

    private void swapPlayers() {
        roundController.swapPlayers();
    }

    private void exchangeCardInDecks(Player player) {
        String answer = DuelMenu.askQuestion("Dear" + player.getName() + "!" +
                " Do you want to exchange cards of side deck and main deck?\n" +
                " (no/ from main to side/ from side to main)");
        try {
            switch (answer) {
                case "no":
                    return;
                case "from main to side":
                    String cardName = DuelMenu.askQuestion("Enter the name of the card.");
                    player.getDeck().exchangeCard(cardName, true);
                    break;
                case "from side to main":
                    cardName = DuelMenu.askQuestion("Enter the name of the card.");
                    player.getDeck().exchangeCard(cardName, false);
                    break;
            }
        } catch (InvalidName | NotExisting | OccurrenceException | BeingFull exception) {
            Print.print(exception.getMessage());
        }
        exchangeCardInDecks(player);
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

    public void playHeadOrTails() {
        boolean isHead = Math.random() < 0.5;
        if (isHead) swapPlayers();
        DuelMenu.showHeadOrTails(isHead, firstUser.getUsername(), secondUser.getUsername());
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

    /* actions in a round*/

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

    public void attack(int number) throws WrongPhaseForAction, CardAttackedBeforeExeption, CardCantAttack, NoCardToAttack, NoSelectedCard, InvalidSelection, NoCardFound {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();

        battlePhaseController.battleAnnounced(number);

    }

    public void attackDirect() throws WrongPhaseForAction, CardAttackedBeforeExeption, CardCantAttack, CantAttackDirectlyException, NoSelectedCard {
        if (!currentPhase.equals(Phase.BATTLE))
            throw new WrongPhaseForAction();
        battlePhaseController.attackToLifePoint();
    }

    public void activateEffect() throws WrongPhaseForAction, NoSelectedCard, ActivateEffectNotSpell, BeingFull, AlreadyActivatedEffect {
        if (!currentPhase.equals(Phase.MAIN_1) && !currentPhase.equals(Phase.MAIN_2))
            throw new WrongPhaseForAction();
        mainPhaseController.activateEffect(true);
    }

    public void selectCard(String address) throws InvalidSelection, NoCardFound {
        CardAddress cardAddress = new CardAddress(address);
        roundController.selectCard(cardAddress.getZoneName(), cardAddress.isForOpponent(), cardAddress.getIndex());
    }

    public void deselectCard() throws NoSelectedCard {
        if (roundController.isAnyCardSelected()) {
            roundController.deselectCard();
        } else throw new NoSelectedCard();
    }

    public String showGraveYard(boolean ofCurrentPlayer) {
        return roundController.showGraveYard(ofCurrentPlayer);
    }

    public void showCard() throws NoSelectedCard {
        if (roundController != null && roundController.isAnyCardSelected()) {
            Card card = roundController.getSelectedCard();
            if (card == null) throw new NoSelectedCard();
            if (!canSeeCard(roundController.getCurrentPlayer(), card)) {
                Print.print("You can't view this card!");
                return;
            }
            if (roundController.getCurrentPlayer().getHand().doesContainCard(card)){
                try {
                    RelatedToMenu.showCard(card.getName());
                } catch (NotExisting ignore) {}
            }
            CardInUse cardInUse = roundController.getSelectedCardInUse();
            if (cardInUse == null) throw new NoSelectedCard();

//            MonsterCardInUse monsterCardInUse = (MonsterCardInUse) roundController.getSelectedCardInUse();
            if (cardInUse instanceof MonsterCardInUse) {
                MonsterCardInUse monsterCardInUse = (MonsterCardInUse) cardInUse;
                Monster monster = (Monster) monsterCardInUse.getThisCard();
                if (monster != null) {
                    Print.print(monster.getName() + " - attack : " + monsterCardInUse.getAttack() + " defense : " + monsterCardInUse.getDefense() + "\n\t" + monster.getMyPreCard().getDescription());
                }
            } else Print.print(card.toString());
        } else throw new NoSelectedCard();
    }

    private boolean canSeeCard(Player player, Card card) {
        CardInUse cardInUse = getRoundController().findCardsCell(card);
        if (cardInUse == null) {
            return player.getHand().doesContainCard(card);
        } else {
            if (cardInUse.getBoard().getOwner() == player) return true;
            return cardInUse.isFaceUp();
        }
    }

    public void surrender() {
        roundController.surrender();
    }

    public void nextPhase() {
        if (currentPhase != null) {
            this.currentPhase = currentPhase.goToNextGamePhase();
            switch (Objects.requireNonNull(currentPhase)) {
                case DRAW:
                    this.drawPhaseController = new DrawPhaseController(roundController, false);
                    break;
                case STANDBY:
                    this.standByPhaseController = new StandByPhaseController(roundController);
                    break;
                case MAIN_1:
                    //in case MAIN_2 the controller is the same as MAIN_1
                    this.mainPhaseController = new MainPhaseController(roundController);
                    break;
                case BATTLE:
                    if (!canHaveBattlePhase) {
                        canHaveBattlePhase = true;
                        nextPhase();
                        return;
                    } else this.battlePhaseController = new BattlePhaseController(roundController);
                    break;
                case END:
                    this.roundController.setTurnEnded(true);
                    DuelMenu.showPhase(currentPhase.toString());
                    roundController.updateAfterChangePhase();
                    return;
                case MAIN_2:
                    break;
            }
        } else {
            this.currentPhase = Phase.DRAW;
            this.drawPhaseController = new DrawPhaseController(roundController, true);
        }
        roundController.setCurrentPhase(currentPhase);

        DuelMenu.showPhase(currentPhase.toString());
        roundController.updateAfterChangePhase();
        if (currentPhase == Phase.DRAW) drawPhaseController.run();
    }

    public boolean askToEnterSummon() {
        return DuelMenu.forceGetCommand("summon", "you should ritual summon right now");
    }

    public MonsterManner getRitualManner() {
        String mannerString = DuelMenu.getRitualManner();
        return MonsterManner.getMonsterManner(mannerString);
    }

    public void showBoard() {
        if (roundController != null) {
            roundController.showBoard();
        }
    }

    public void showHand() {
        Hand hand = roundController.getCurrentPlayer().getHand();
        for (Card card : hand.getCardsInHand()) {
            System.out.println(card.getName());
        }
    }
}