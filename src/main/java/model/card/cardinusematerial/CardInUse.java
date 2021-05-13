package model.card.cardinusematerial;

import lombok.Getter;
import lombok.Setter;
import model.card.Card;

@Getter
@Setter
public class  CardInUse{
    private Card thisCard;
    private boolean isPositionChanged;  //if card manner was changed in a round ->true then ->false
    private boolean isFaceUp;   //maybe not needed


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


}
