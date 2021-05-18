package controller.game;

import exceptions.InvalidSelection;
import exceptions.NoCardFound;
import lombok.Getter;
import model.Board;
import model.Deck;
import model.Enums.Phase;
import model.Enums.ZoneName;
import model.Player;
import model.User;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import view.Print;

import java.util.regex.Matcher;

@Getter
public
class GamePlayController {

//    private User firstUser;
//    private User secondUser;
    private Player currentPlayer;
    private Player rival;
    private int round;
//    private PreCard rivalSelectedCard;
//    private Board currentPlayerBoard;
//    private Board rivalBoard;             if they were needed use player
    private Phase currentPhase;
//    private MainPhaseController mainPhase;
//    private BattlePhaseController battlePhase;
//    private StandByPhaseController standByPhase;
//    private DrawPhaseController drawPhase;
    private ActionsOnRival actionsOnRival;
    private CardInUse selectedCardInUse;
    private PreCard selectedPreCard;

    {
//        mainPhase = new MainPhaseController(this);
//        battlePhase = new BattlePhaseController(this);
//        standByPhase = new StandByPhaseController(this);
//        drawPhase = new DrawPhaseController(this);
        actionsOnRival = new ActionsOnRival(this);
    }


    public GamePlayController(User firstUser, User secondUser, DuelMenuController duelMenu) {
        currentPlayer = new Player(firstUser);
        rival = new Player(secondUser);
        currentPhase = Phase.DRAW;
        duelMenu.setDrawPhase(new DrawPhaseController(this, true), currentPhase);
    }
//    ---------------------------------------- getters and setters and stuff  -------------------------------------------------

    public boolean isAnyCardSelected() {
        return !(this.selectedPreCard == null && this.selectedCardInUse == null);
    }

    public Board getCurrentPlayerBoard() {
        return currentPlayer.getBoard();
    }

    public Board getRivalBoard() {
        return rival.getBoard();
    }


    public void increaseRound() {
        round++;
    }


//    ---------------------------------------- static methods for initializing the game -------------------------------------

    private static boolean areUsersValid(Matcher matcher) {
        return true;
    } //todo : why here!?

    public static boolean isGameValid(String firstUsername, String secondUsername) {
        return true;
    }

    public static boolean isDeckValid(Deck deck) {
        return true;
    }


//    ---------------------------------------- general actions (in any phase) -------------------------------------------------

    public void showBoard() {

    }

    public void selectCard(ZoneName zoneName, boolean isForOpponent, int cardIndex) throws InvalidSelection, NoCardFound {
        switch (zoneName) {

            case HAND:
                if (isForOpponent) throw new InvalidSelection();
                this.selectedCardInUse = null;
                this.selectedPreCard = currentPlayer.getHand().getCardWithNumber(cardIndex);
                break;
            case MONSTER:
                this.selectedPreCard = null;
                this.selectedCardInUse = currentPlayer.getBoard().getCardInUse(cardIndex, true);
                break;
            case SPELL:
                this.selectedPreCard = null;
                this.selectedCardInUse = currentPlayer.getBoard().getCardInUse(cardIndex, false);
                break;
            case FIELD:
                this.selectedCardInUse = null;
                PreCard fieldCard = currentPlayer.getBoard().getFieldCard();
                if (fieldCard == null) throw new NoCardFound();
                this.selectedPreCard = currentPlayer.getBoard().getFieldCard();
                break;
            default:
                throw new InvalidSelection();
        }
        Print.print("card selected");
    }

    public void deselectedCard() {
        this.selectedPreCard = null;
        this.selectedCardInUse = null;
    }

    public void showGraveYard() {
        //currentPlayerBoard.getGraveYard().showGraveYard();
    }

    public void surrender(Player player) {
    }


//    ------------------------------------------- the main part, the game  ----------------------------------------------------

    public void playOneTurn(Player player) {

    }


    public void runDuel() {

    }

    public void announceWinner(boolean isCurrentPlayerLoser) {

    }

}
