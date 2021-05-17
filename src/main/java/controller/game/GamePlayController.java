package controller.game;

import lombok.Getter;
import model.Board;
import model.Deck;
import model.Enums.Phase;
import model.Player;
import model.User;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;

import java.util.regex.Matcher;

@Getter
public
class GamePlayController {

//    private User firstUser;
//    private User secondUser;
    private Player currentPlayer;
    private Player rival;
    private int round;
    private PreCard selectedCard;
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
        duelMenu.setDrawPhase(new DrawPhaseController(this), currentPhase);
    }
//    ---------------------------------------- getters and setters and stuff  -------------------------------------------------

    public Board getCurrentPlayerBoard() {
        return currentPlayer.getBoard();
    }

    public Board getRivalBoard() {
        return rival.getBoard();
    }

    public PreCard getCurrentPlayerSelectedCard() {
        return selectedCard;
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

    public void selectCard(String cardAddress) {

        //currentPlayerBoard.getCardByAddress(cardAddress);
    }

    public void deselectedCard() {
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

}
