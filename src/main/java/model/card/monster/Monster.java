package model.card.monster;

import lombok.Getter;
import lombok.Setter;
import model.card.Card;
import model.card.PreCard;

@Getter
@Setter
public class Monster extends Card {
    protected PreMonsterCard myPreCard;
    protected boolean canBeNormalSummoned;
    protected int numOfNeededTributes;
    protected int level;

    public Monster(PreCard preCard) {
        super(preCard);
        myPreCard = (PreMonsterCard) preCard;
        if (preCard != null)
            this.level = ((PreMonsterCard) preCard).getLevel();
        setNumOfTributes();

    }


    protected void setNumOfTributes() {
        int level = myPreCard.getLevel();
        if (level <= 4) numOfNeededTributes = 0;
        else if (level <= 6) numOfNeededTributes = 1;
        else numOfNeededTributes = 2;
    }

    @Override
    public void setName(String name) {
        if (getName() == null)
            super.setName(name);
    }

    public Monster getCard() {
        return this;
    }


    public int getRawAttack() {
        return myPreCard.getAttack();
    }

    public int getRawDefense() {
        return myPreCard.getDefense();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Monster(preCardInGeneral);
    }

    public void spinCard() {
        //TODO what for?
    }
}
