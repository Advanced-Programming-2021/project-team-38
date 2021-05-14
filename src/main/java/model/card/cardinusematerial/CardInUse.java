package model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import model.card.Card;
import model.card.PreCard;
import model.card.monster.PreMonsterCard;

@Getter
@Setter
public class CardInUse{
    protected Card thisCard;
    protected boolean isPositionChanged;  //if card manner was changed in a round ->true then ->false
    protected boolean isFaceUp;   //maybe not needed



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
}
