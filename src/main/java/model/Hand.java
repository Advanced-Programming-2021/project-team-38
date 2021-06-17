package model;

import exceptions.InvalidSelection;
import model.card.Card;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.card.monster.PreMonsterCard;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cardsInHand;

    {
        this.cardsInHand = new ArrayList<>();
    }


    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(Card card) {
        return cardsInHand.contains(card);
    }


    //returns the monsters of the desired type in the hand. for example, ritual monster
    public ArrayList<Monster> getMonstersOfType(MonsterCardType monsterCardType) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card instanceof Monster) {
                PreMonsterCard preMonsterCard = (PreMonsterCard) (card.getPreCardInGeneral());
                if (preMonsterCard.getType().equals(monsterCardType)) monsters.add((Monster) card);
            }
        }
        return monsters;
    }

    public void addCard(Card card) {
        if (cardsInHand.size() <= 5)
            this.cardsInHand.add(card);
        //todo: else throw something
    }

    public void removeCard(Card card) {
        this.cardsInHand.remove(card);
    }

    public Card getCardWithNumber(int index) throws InvalidSelection {
        if (index > 0 && index <= cardsInHand.size())
            return cardsInHand.get(index + 1);
        else throw new InvalidSelection();
    }

    @Override
    public String toString() {
        return "c  ".repeat(cardsInHand.size());
    }
}
