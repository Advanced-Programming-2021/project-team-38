package controller.game;

import exceptions.*;
import lombok.Getter;
import lombok.Setter;
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
@Setter
public
class GamePlayController {


    private Player currentPlayer;
    private Player rival;
    private Phase currentPhase;
    private ActionsOnRival actionsOnRival;

    private CardInUse selectedCardInUse;
    private PreCard selectedPreCard;
    private boolean isSelectedCardFromRivalBoard = false;

    private boolean isRoundEnded;
    private boolean isTurnEnded;

    private DuelMenuController duelMenuController;

    {
        actionsOnRival = new ActionsOnRival(this);
    }


    public GamePlayController(User firstUser, User secondUser, DuelMenuController duelMenuController)
            throws InvalidDeck, InvalidName, NoActiveDeck {
        if (isGameValid(firstUser, secondUser)) {
            currentPlayer = new Player(firstUser);
            rival = new Player(secondUser);
            currentPhase = Phase.DRAW;
            this.duelMenuController = duelMenuController;
            duelMenuController.setDrawPhase(new DrawPhaseController(this, true), currentPhase);
        }
    }

    /* static methods for initializing the game */
    private static boolean isGameValid(User firstUser, User secondUser) throws InvalidName, NoActiveDeck, InvalidDeck {
        if (firstUser == null || secondUser == null)
            throw new InvalidName("user", "username");//it won't happen! just for making sure!
        if (firstUser.getActiveDeck() == null) throw new NoActiveDeck(firstUser.getUsername());
        if (secondUser.getActiveDeck() == null) throw new NoActiveDeck(secondUser.getUsername());
        if (!isDeckValid(firstUser.getActiveDeck())) throw new InvalidDeck(firstUser.getUsername());
        if (!isDeckValid(secondUser.getActiveDeck())) throw new InvalidDeck(secondUser.getUsername());
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



    /* general actions (in any phase) */

    public void showBoard() {

    }

    public void selectCard(ZoneName zoneName, boolean isForOpponent, int cardIndex) throws InvalidSelection, NoCardFound {
        switch (zoneName) {

            case HAND:
                if (isForOpponent) throw new InvalidSelection();
                this.selectedPreCard = currentPlayer.getHand().getCardWithNumber(cardIndex);
                this.selectedCardInUse = null;
                break;
            case MONSTER:
                this.selectedCardInUse = currentPlayer.getBoard().getCardInUse(cardIndex, true);
                this.selectedPreCard = null;
                break;
            case SPELL:
                this.selectedCardInUse = currentPlayer.getBoard().getCardInUse(cardIndex, false);
                this.selectedPreCard = null;
                break;
            case FIELD:
                PreCard fieldCard = currentPlayer.getBoard().getFieldCard();
                if (fieldCard == null) throw new NoCardFound();
                this.selectedPreCard = currentPlayer.getBoard().getFieldCard();
                this.selectedCardInUse = null;
                break;
            case GRAVEYARD:
                if (isForOpponent) this.selectedPreCard = rival.getBoard().getGraveYard().getPreCard(cardIndex);
                else this.selectedPreCard = currentPlayer.getBoard().getGraveYard().getPreCard(cardIndex);
                this.selectedCardInUse = null;
                break;
            default:
                throw new InvalidSelection();
        }
        Print.print("card selected");
    }

    public void deselectCard() {
        this.selectedPreCard = null;
        this.selectedCardInUse = null;
        isSelectedCardFromRivalBoard = false; //todo: why do we need this thing?
    }

    public String showGraveYard(boolean shouldShowCurrent) {
        if (shouldShowCurrent) return currentPlayer.getBoard().getGraveYard().toString();
        else return rival.getBoard().getGraveYard().toString();
    }

    public void surrender(Player player) {
    }


    /* the main part, the game */

    public void announceWinner(boolean isCurrentPlayerLoser) { //I think it needs an input for draw, maybe better get an enum
        //todo
        //the winner is the winner of the current round
        this.isRoundEnded = true;
        this.isTurnEnded = true;
        //todo: show the winner or the other things in the output
    }

    public void swapPlayers() {
        Player hold = currentPlayer;
        currentPlayer = rival;
        rival = hold;
    }
}
