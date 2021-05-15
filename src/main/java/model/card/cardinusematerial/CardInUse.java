package model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import model.card.Card;
import model.card.PreCard;

@Getter
@Setter
public abstract class CardInUse {
    protected Card thisCard;
    protected boolean isPositionChanged;  //if card manner was changed in a round ->true then ->false //todo: in the beginning of any turn we should change it to false
    protected boolean isFaceUp;


    {
        isPositionChanged = false;
        isFaceUp = false;
    }


    public Card getThisCard() {
        return thisCard;
    }

    public void emptyCardInUse() {
        thisCard = null;
        //TODO takmil
    }

    public boolean isCellEmpty() {
        return thisCard == null;
    }

    public void setACardInThisCell(PreCard preCard) { //TODO

    }

    public abstract void putInGraveYard();

    public void changePosition() {
        isPositionChanged = true; //todo : check with hasti. change position isn't only for face up ( I'm not sure if it's even important for face up change).
        isFaceUp = !isFaceUp;
    }
}
