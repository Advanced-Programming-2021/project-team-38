package controller.game;

import exceptions.*;
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

@Getter
public
class GamePlayController {

    //    private User firstUser;
//    private User secondUser;
    private Player currentPlayer;
    private Player rival;
    private int round;
    private Phase currentPhase;
    //    private MainPhaseController mainPhase;
//    private BattlePhaseController battlePhase;
//    private StandByPhaseController standByPhase;
//    private DrawPhaseController drawPhase;
    private ActionsOnRival actionsOnRival;
    private CardInUse selectedCardInUse;
    private PreCard selectedPreCard;
    private int numOfRounds;

    {
//        mainPhase = new MainPhaseController(this);
//        battlePhase = new BattlePhaseController(this);
//        standByPhase = new StandByPhaseController(this);
//        drawPhase = new DrawPhaseController(this);
        actionsOnRival = new ActionsOnRival(this);
    }


    public GamePlayController(User firstUser, User secondUser, DuelMenuController duelMenu, int numOfRounds)
            throws InvalidDeck, InvalidName, NoActiveDeck, NumOfRounds {
        if (isGameValid(firstUser, secondUser, numOfRounds)) {
            currentPlayer = new Player(firstUser);
            rival = new Player(secondUser);
            currentPhase = Phase.DRAW;
            duelMenu.setDrawPhase(new DrawPhaseController(this, true), currentPhase);
            this.numOfRounds = numOfRounds;
        }
    }

    /* static methods for initializing the game */
    private static boolean isGameValid(User firstUser, User secondUser, int numOfRounds) throws InvalidName, NumOfRounds, NoActiveDeck, InvalidDeck {
        if (firstUser == null || secondUser == null)
            throw new InvalidName("user", "username");//it won't happen! just for making sure!
        if (firstUser.getActiveDeck() == null) throw new NoActiveDeck(firstUser.getUsername());
        if (secondUser.getActiveDeck() == null) throw new NoActiveDeck(secondUser.getUsername());
        if (!isDeckValid(firstUser.getActiveDeck())) throw new InvalidDeck(firstUser.getUsername());
        if (!isDeckValid(secondUser.getActiveDeck())) throw new InvalidDeck(secondUser.getUsername());
        if (numOfRounds != 1 && numOfRounds != 3) throw new NumOfRounds();
        return true;
    }

    private static boolean isDeckValid(Deck deck) {
        return true;//todo
    }

    /*  getters and setters and stuff  */
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

    /* general actions (in any phase) */

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


    /* the main part, the game */

    public void playOneRound() {

    }



    public void runDuel() {
        for (int i = 0; i < numOfRounds; i++) {
            while (true) {
//                playOneTurn(currentPlayer);
                break;
            }
        }
    }

    public void announceWinner(boolean isCurrentPlayerLoser) {

    }

}
