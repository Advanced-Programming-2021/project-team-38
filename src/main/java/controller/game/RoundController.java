package controller.game;

import exceptions.*;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Enums.Phase;
import model.Enums.RoundResult;
import model.Enums.ZoneName;
import model.Player;
import model.User;
import model.card.Card;
import model.card.cardinusematerial.CardInUse;
import view.Print;

@Getter
@Setter
public class RoundController {

    private Player currentPlayer;
    private Player rival;
    private Player winner;
    private Player loser;

    private Phase currentPhase;
    private ActionsOnRival actionsOnRival;

    //    private CardInUse selectedCardInUse;
//    private PreCard selectedPreCard;
    private Card selectedCard;
    private boolean isSelectedCardFromRivalBoard;

    private boolean isRoundEnded;
    private boolean isTurnEnded;
    private int roundIndex; //0,1,2


    private DuelMenuController duelMenuController;

    {
        isSelectedCardFromRivalBoard = false;
        actionsOnRival = new ActionsOnRival(this);
        this.isRoundEnded = false;
    }


    public RoundController(User firstUser, User secondUser, DuelMenuController duelMenuController, int roundIndex)
            throws InvalidDeck, InvalidName, NoActiveDeck {
        currentPlayer = new Player(firstUser, this);
        rival = new Player(secondUser, this);
        currentPhase = Phase.DRAW;
        this.duelMenuController = duelMenuController;
        duelMenuController.setDrawPhase(new DrawPhaseController(this, true));
        this.roundIndex = roundIndex;
    }


    /*  getters and setters and stuff  */
    public boolean isAnyCardSelected() {
//        return !(this.selectedPreCard == null && this.selectedCardInUse == null);
        return this.selectedCard != null;
    }

    public Board getCurrentPlayerBoard() {
        return currentPlayer.getBoard();
    }

    public Board getRivalBoard() {
        return rival.getBoard();
    }



    /* general actions (in any phase) */

    public void selectCard(ZoneName zoneName, boolean isForOpponent, int cardIndex) throws InvalidSelection, NoCardFound {
        Player ownerOfToBeSelected = currentPlayer;
        if (isForOpponent) ownerOfToBeSelected = rival;
        switch (zoneName) {
            case HAND:
                if (isForOpponent) throw new InvalidSelection();
                this.selectedCard = currentPlayer.getHand().getCardWithNumber(cardIndex);
                break;
            case MONSTER:
                this.selectedCard = ownerOfToBeSelected.getBoard().getCardInUse(cardIndex, true).getThisCard();
                break;
            case SPELL:
                this.selectedCard = ownerOfToBeSelected.getBoard().getCardInUse(cardIndex, false).getThisCard();
                break;
            case FIELD:
                Card fieldCard = ownerOfToBeSelected.getBoard().getFieldCard().getThisCard();
                if (fieldCard == null) throw new NoCardFound();
                this.selectedCard = fieldCard;
                break;
            case GRAVEYARD:
                this.selectedCard = ownerOfToBeSelected.getBoard().getGraveYard().getCard(cardIndex);
                break;
            default:
                throw new InvalidSelection();
        }
        Print.print("card selected");
    }

    public void deselectCard() {
        this.selectedCard = null;
        isSelectedCardFromRivalBoard = false; //todo: why do we need this thing?
    }

    public String showGraveYard(boolean ofCurrentPlayer) {
        if (ofCurrentPlayer) return currentPlayer.getBoard().getGraveYard().toString();
        else return rival.getBoard().getGraveYard().toString();
    }

    public void surrender() {
        setRoundWinner(RoundResult.CURRENT_SURRENDER);
    }


    /* the main part, the game */

    public void setRoundWinner(RoundResult roundResult) { //I think it needs an input for draw, maybe better get an enum
        this.isRoundEnded = true;
        this.isTurnEnded = true;
        switch (roundResult) {
            case CURRENT_WON:
                winner = currentPlayer;
                loser = rival;
                break;
            case RIVAL_WON:
            case CURRENT_SURRENDER:
                winner = rival;
                loser = currentPlayer;
                break;
            case DRAW:
                //todo
                break;
        }
    }

    public void announceRoundWinner() {
        if (winner == null || loser == null) return;//todo!
        this.duelMenuController.handleRoundWinner(winner.getOwner(), loser.getOwner(), winner.getLifePoint(), loser.getLifePoint(), 1, 0, this.roundIndex);
        //todo: not sure what to put as the scores!
    }

    public void sendToGraveYard(CardInUse cardInUse) {
//        cardInUse.sendToGraveYard();
    }

}
