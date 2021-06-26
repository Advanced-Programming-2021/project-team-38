package controller.game;

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
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import view.Menus.DuelMenu;
import view.Print;
import view.exceptions.*;

@Getter
@Setter
public class RoundController {

    private Player currentPlayer;
    private Player rival;
    private Player winner;
    private Player loser;

    private Phase currentPhase;
    private ActionsOnRival actionsOnRival;


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
        this.duelMenuController = duelMenuController;
        currentPlayer = new Player(firstUser, this);
        rival = new Player(secondUser, this);
        currentPhase = Phase.DRAW;
        duelMenuController.setDrawPhase(new DrawPhaseController(this, true));
        this.roundIndex = roundIndex;
    }

    public void swapPlayers() {
        Player hold = this.currentPlayer;
        this.currentPlayer = rival;
        this.rival = hold;
    }

    public void updateBoards() {
        currentPlayer.getBoard().updateAfterAction();
        rival.getBoard().updateAfterAction();
        if (currentPlayer.getLifePoint() <= 0) {
            if (rival.getLifePoint() <= 0) {
                setRoundWinner(RoundResult.DRAW);
            } else setRoundWinner(RoundResult.RIVAL_WON);
        }
        if (rival.getLifePoint() <= 0) {
            setRoundWinner(RoundResult.CURRENT_WON);
        }
        showBoard();
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
            case MY_MONSTER_ZONE:
            case RIVAL_MONSTER_ZONE:
                this.selectedCard = ownerOfToBeSelected.getBoard().getCardInUse(cardIndex, true).getThisCard();
                break;
            case MY_SPELL_ZONE:
            case RIVAL_SPELL_ZONE:
                this.selectedCard = ownerOfToBeSelected.getBoard().getCardInUse(cardIndex, false).getThisCard();
                break;
            case MY_FIELD:
            case RIVAL_FIELD:
                Card fieldCard = ownerOfToBeSelected.getBoard().getFieldCell().getThisCard();
                if (fieldCard == null) throw new NoCardFound();
                this.selectedCard = fieldCard;
                break;
            case MY_GRAVEYARD:
            case RIVAL_GRAVEYARD:
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
        cardInUse.sendToGraveYard();
        updateBoards();
    }

    public Player getMyRival(Player myPlayer) {
        if (currentPlayer == myPlayer) return rival;
        else return currentPlayer;
    }

    public CardInUse findCardsCell(Card card) {
        for (MonsterCardInUse monsterCardInUse : currentPlayer.getBoard().getMonsterZone()) {
            if (!monsterCardInUse.isCellEmpty() && monsterCardInUse.thisCard == card)
                return monsterCardInUse;
        }

        for (MonsterCardInUse monsterCardInUse : rival.getBoard().getMonsterZone()) {
            if (!monsterCardInUse.isCellEmpty() && monsterCardInUse.thisCard == card)
                return monsterCardInUse;
        }

        for (SpellTrapCardInUse spellTrapCardInUse : currentPlayer.getBoard().getSpellTrapZone()) {
            if (spellTrapCardInUse.thisCard == card)
                return spellTrapCardInUse;
        }

        for (SpellTrapCardInUse spellTrapCardInUse : rival.getBoard().getSpellTrapZone()) {
            if (spellTrapCardInUse.thisCard == card)
                return spellTrapCardInUse;
        }

        if (currentPlayer.getBoard().getFieldCell().thisCard == card) {
            return currentPlayer.getBoard().getFieldCell();
        }

        if (rival.getBoard().getFieldCell().thisCard == card) {
            return rival.getBoard().getFieldCell();
        }

        return null;
    }

    public CardInUse getSelectedCardInUse() {
        return findCardsCell(selectedCard);
    }

    public void temporaryTurnChange(Player newCurrent) {
        DuelMenu.showTemporaryTurnChange(newCurrent.getName(), newCurrent.getBoard(), getMyRival(newCurrent).getBoard());
    }

    public boolean wantToActivateCard(String cardName) {
        return DuelMenuController.askQuestion(
                "Do you want to activate" + cardName + " ?(Y/N)").equals("Y");
    }

    public boolean arrangeAlternateBattle() {
        return DuelMenuController.askQuestion("your battle was canceled.\n" +
                "Do you want to start a new battle with this card? (Y/N)").equals("Y");
    }

    public void showBoard() {
        Print.print(rival.getBoard().toString());
        Print.print(currentPlayer.getBoard().toString());
    }
}
