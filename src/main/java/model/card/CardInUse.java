package model.card;

public class  CardInUse{
    private Card thisCard;


    public Card getThisCard() {
        return thisCard;
    }

    public void emptyCardInUse() {
        thisCard = null;
        //TODO takmil
    }
}
