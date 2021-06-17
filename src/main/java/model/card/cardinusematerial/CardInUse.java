package model.card.cardinusematerial;

import controller.game.RoundController;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.CardState;
import model.Player;
import model.card.Card;
import model.watchers.Watcher;
import model.watchers.watchingexceptions.CancelBattle;

import java.util.ArrayList;

@Getter
@Setter
public abstract class CardInUse {
    public ArrayList<Watcher> watchersOfCardInUse;
    public Card thisCard;
    public Player ownerOfCard;
    public RoundController roundController;
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
    }


    public Card getThisCard() {
        return thisCard;
    }

    public boolean isCellEmpty() {
        return thisCard == null;
    }

    public abstract void putInGraveYard();

    public void changePosition() {
        isPositionChanged = true; //todo : check with hasti. change position isn't only for face up ( I'm not sure if it's even important for face up change).
        isFaceUp = !isFaceUp;
    }

    public void setACardInCell(Card card) {
        thisCard = card;
        //TODO
        card.putBuiltInWatchers(this);
    }

    public void resetCell() {
        isPositionChanged = false;
        thisCard = null;
        isFaceUp = false;
        for (Watcher watcher: watchersOfCardInUse) {
            watcher.disableWatcher(this);
        }

        watchersOfCardInUse = new ArrayList<>();
    }

    public void sendToGraveYard() throws CancelBattle {
        watchByState(CardState.SENT_TO_GRAVEYARD);
        resetCell();
    }

    public void watchByState(CardState cardState) throws CancelBattle {
        for (Watcher watcher : watchersOfCardInUse) {
            //TODO !!! for negar
            if (watcher.ownerOfWatcher == this && cardState == CardState.ACTIVE_EFFECT)
                watcher.watch(CardState.ACTIVE_MY_EFFECT, null);
            else
                watcher.watch(cardState, null);
        }
    }
}
