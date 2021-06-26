package model.card.cardinusematerial;

import controller.game.DuelMenuController;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.CardState;
import model.Enums.Phase;
import model.Player;
import model.card.Card;
import model.card.monster.Monster;
import model.watchers.Watcher;

import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public abstract class CardInUse {
    public ArrayList<Watcher> watchersOfCardInUse;
    public Card thisCard;
    public Player ownerOfCard;
    public boolean isPositionChanged;  //if card manner was changed in a turn ->true then ->false
    private boolean isFaceUp;
    protected Board board;

    public CardInUse(Board board) {
        this.board = board;
        this.ownerOfCard = board.getOwner();
    }

    {
        isPositionChanged = false;
        isFaceUp = false;
        watchersOfCardInUse = new ArrayList<>();
    }

    public Card getThisCard() {
        return thisCard;
    }

    public boolean isCellEmpty() {
        return thisCard == null;
    }

    public void faceUpCard() { //note
        if (!isFaceUp) {
            watchByState(CardState.FACE_UP);
            isFaceUp = true;
            isPositionChanged = true;
        }
    }

    public void setACardInCell(Card card) {
        thisCard = card;
        card.cardIsBeingSetInCell(this);
    }

    public void resetCell() {
        isPositionChanged = false;
        isFaceUp = false;
        for (Watcher watcher : watchersOfCardInUse) {
            watcher.disableWatcher(this);
        }

        watchersOfCardInUse = new ArrayList<>();
        if (thisCard != null)
            thisCard.theCardIsBeingDeleted();
        thisCard = null;
    }

    public void sendToGraveYard() {
        watchByState(CardState.SENT_TO_GRAVEYARD);
        resetCell();
    }

    public void watchByState(CardState cardState) {
        DuelMenuController duelMenuController = this.getBoard().getController();
        for (Watcher watcher : watchersOfCardInUse) {
            if (watcher.ownerOfWatcher == this && cardState == CardState.ACTIVE_EFFECT)
                watcher.watch(this, CardState.ACTIVE_MY_EFFECT, duelMenuController);
            else
                watcher.watch(this, cardState, duelMenuController);
        }
    }

    public void updateCard() {
        if (!isCellEmpty()) thisCard.putBuiltInWatchers(this);
        if (board.getMyPhase() == Phase.END || board.getMyPhase() == Phase.END_RIVAL) {
            isPositionChanged = false;
        }
        Collections.sort(watchersOfCardInUse);
    }

    //used in showing the board
    @Override
    public String toString() {
        if (thisCard == null) return "E ";
        else if (thisCard instanceof Monster) {
            String mannerString = "D";
            MonsterCardInUse monsterCardInUse = (MonsterCardInUse) this;
            if (monsterCardInUse.isInAttackMode()) mannerString = "O";
            if (monsterCardInUse.isFaceUp()) mannerString = mannerString + "O";
            else mannerString = mannerString + "H";
            return mannerString;
        } else {
            if (isFaceUp) return "O ";
            else return "H ";
        }
    }
}
