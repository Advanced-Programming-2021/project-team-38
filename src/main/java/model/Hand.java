package model;

import exceptions.InvalidSelection;
import model.card.CardType;
import model.card.PreCard;
import model.card.monster.MonsterCardType;
import model.card.monster.PreMonsterCard;

import java.util.ArrayList;

public class Hand {
    private ArrayList<PreCard> cardsInHand;

    {
        this.cardsInHand = new ArrayList<>();
    }


    public ArrayList<PreCard> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(PreCard preCard) {
        return cardsInHand.contains(preCard);
    }

    public ArrayList<PreMonsterCard> getMonstersOfType(MonsterCardType monsterCardType) {
        ArrayList<PreMonsterCard> monsters = new ArrayList<>();
        for (PreCard preCard : cardsInHand) {
            if (preCard.getCardType().equals(CardType.MONSTER)) {
                PreMonsterCard preMonsterCard = (PreMonsterCard) preCard;
                if (preMonsterCard.getType().equals(MonsterCardType.RITUAL)) monsters.add(preMonsterCard);
            }
        }
        return monsters;
    }

    public void addCard(PreCard preCard) {
        if (cardsInHand.size() <= 5)
            this.cardsInHand.add(preCard);
        //todo: else throw something
    }

    public void removeCard(PreCard preCard) {
        this.cardsInHand.remove(preCard);
    }

    public PreCard getCardWithNumber(int index) throws InvalidSelection {
        if (index > 0 && index <= cardsInHand.size())
            return cardsInHand.get(index + 1);
        else throw new InvalidSelection();
    }

    @Override
    public String toString() {
        return "c  ".repeat(cardsInHand.size());
    }
}
