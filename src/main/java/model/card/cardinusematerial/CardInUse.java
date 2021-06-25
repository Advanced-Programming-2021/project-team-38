package model.card.cardinusematerial;

import controller.game.DuelMenuController;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.CardState;
import model.Enums.Phase;
import model.Player;
import model.card.Card;
import model.card.spelltrap.SpellTrap;
import model.watchers.Watcher;

import java.util.ArrayList;

@Getter
@Setter
public abstract class CardInUse {
    public ArrayList<Watcher> watchersOfCardInUse;
    public Card thisCard;
    public Player ownerOfCard;
    public boolean isPositionChanged;  //if card manner was changed in a turn ->true then ->false //todo: in the beginning of any turn we should change it to false
    public boolean isFaceUp;
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

    public void changePosition() {
        isPositionChanged = true; //todo : check with hasti. change position isn't only for face up ( I'm not sure if it's even important for face up change).
        isFaceUp = !isFaceUp;
    }

//    public void setFaceUp(boolean faceUp) {
//        if (faceUp) faceUpCard();
//        else isFaceUp = false;
//    }


    public void faceUpCard() { //note
        watchByState(CardState.FACE_UP);
        isFaceUp = true;
    }

    public void setACardInCell(Card card) {
        thisCard = card;
        card.cardIsBeingSetInCell(this);
//        card.putBuiltInWatchers(this);
    }

    public void resetCell() {
        isPositionChanged = false;
        thisCard = null;
        isFaceUp = false;
        for (Watcher watcher : watchersOfCardInUse) {
            watcher.disableWatcher(this);
        }

        watchersOfCardInUse = new ArrayList<>();
        assert thisCard != null;    //TODO what is it doing?
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

    public void activateMyEffect() {
        if (thisCard == null) return;
        watchByState(CardState.ACTIVE_EFFECT);
        ((SpellTrap) thisCard).setActivated(true);
    }

    public void updateCard() {
        if (!isCellEmpty()) thisCard.putBuiltInWatchers(this);
        if (board.getMyPhase() == Phase.END || board.getMyPhase() == Phase.END_RIVAL) {
            isPositionChanged = false;
        }
    }
}
