package controller.game;

import model.Board;
import model.Deck;
import model.Player;
import model.User;
import model.card.PreCard;

import java.util.regex.Matcher;

class GamePlayController {

    private User firstUser;
    private User secondUser;
    private Player currentPlayer;
    private Player rival;
    private int round;
    private PreCard currentPlayerSelectedCard;
    private PreCard rivalSelectedCard;
    private Board currentPlayerBoard;
    private Board rivalBoard;
    private PhaseName currentPhase;


    public GamePlayController(User firstUser, User secondUser) {

        //secondUser =new User(secondUser);

        currentPlayerBoard = new Board();
        rivalBoard = new Board();
        currentPlayer = new Player(firstUser);
        rival = new Player(secondUser);
    }
//    ---------------------------------------- getters and setters and stuff  -------------------------------------------------


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getRival() {
        return rival;
    }

    public int getRound() {
        return round;
    }

    public void setCurrentPlayerBoard(Board currentPlayerBoard) {
        this.currentPlayerBoard = currentPlayerBoard;
    }

    public Board getCurrentPlayerBoard() {
        return currentPlayerBoard;
    }

    public void setRivalBoard(Board rivalBoard) {
        this.rivalBoard = rivalBoard;
    }

    public Board getRivalBoard() {
        return rivalBoard;
    }

    public void setCurrentPhase(PhaseName currentPhase) {
        this.currentPhase = currentPhase; //todo: we should order the phases and here we should set the phase to the next phase
    }

    public PreCard getCurrentPlayerSelectedCard() {
        return currentPlayerSelectedCard;
    }

    public PreCard getRivalSelectedCard() {
        return rivalSelectedCard;
    }

    public PhaseName getCurrentPhase() {
        return currentPhase;
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
