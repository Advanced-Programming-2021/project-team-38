package controller.game;

import model.*;
import model.card.*;

import java.util.regex.Matcher;

class GamePlayController {

    private User firstUser;
    private User secondUser;
    private Player currentPlayer;
    private Player rival;
    private int round;
    //    private Card currentPlayerSelectedCard;
//    private Card rivalSelectedCard;
    private Board currentPlayerBoard;
    private Board rivalBoard;

    public GamePlayController(User firstUser, User secondUser) {

        //secondUser =new User(secondUser);

        currentPlayerBoard = new Board();
        rivalBoard = new Board();
        currentPlayer = new Player(firstUser);
        rival = new Player(secondUser);
    }

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

    public void increaseRound() {
        round++;
    }


    private static boolean areUsersValid(Matcher matcher) {
        return true;
    }

    public static boolean isGameValid(String firstUsername, String secondUsername) {
        return true;
    }

    public static boolean isDeckValid(Deck deck) {
        return true;
    }

    public String runDuel() {
        return null;

    }

    public String showBoard() {
        return null;
    }

    public Card selectCard(String cardAddress) {
        return null;
        //return currentPlayerBoard.getCardByAddress(cardAddress);
    }

    public String playOneTurn(Player player) {
        return null;
    }

    public String showGraveYard() {
        return null;
        //return currentPlayerBoard.getGraveYard().showGraveYard();
    }


    public String surrender(Player player) {
        return null;
    }

    public Card deselectedCard(String cardAddress) {
        return null;
    }


}
