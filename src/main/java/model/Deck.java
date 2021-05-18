package model;

import exceptions.BeingFull;
import exceptions.OccurrenceException;
import model.card.CardType;
import model.card.PreCard;
import model.card.spelltrap.CardStatus;
import model.card.spelltrap.PreSpellTrapCard;
import view.Print;
import view.SuccessMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private String name;
    private ArrayList<PreCard> mainCards;
    private ArrayList<PreCard> sideCards;
    private User owner;
//    private boolean isNumOfCardsValid;

    public Deck(String name, User owner) {
        setOwner(owner);
        this.name = name;
        this.mainCards = new ArrayList<>();
        this.sideCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<PreCard> getMainCards() {
        return mainCards;
    }

    public ArrayList<PreCard> getSideCards() {
        return sideCards;
    }

    public User getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

//    public void setNumOfCardsValid(boolean numOfCardsValid) {
//        isNumOfCardsValid = numOfCardsValid;
//    }
//
//    public boolean isNumOfCardsValid() {
//        return isNumOfCardsValid;
//    }

//    print a deck by its side
    //start
    public void showDeck(boolean side) {
        Print.print(this.getName());
        sortAndFilter(side);
    }

    private void sortAndFilter(boolean side) {   //TODO test
        ArrayList<PreCard> preCards;
        if (side)   preCards = sideCards;
        else    preCards = mainCards;

        preCards.sort(Comparator.comparing(PreCard::getName));
        filterAndPrint(preCards);
    }

    private void filterAndPrint(ArrayList<PreCard> preCards) {
        List<PreCard> monsterCards = preCards.stream()
                .filter(p -> p.getCardType() == CardType.MONSTER).
                        collect(Collectors.toList());
        Print.print("Monsters:");
        for (PreCard preCard : monsterCards) {
            Print.print(preCard.toString());
        }

        List<PreCard> spellTrapCards = preCards.stream()
                .filter(p -> p.getCardType() != CardType.MONSTER).
                        collect(Collectors.toList());
        Print.print("Spell and Traps:");
        for (PreCard preCard : spellTrapCards) {
            Print.print(preCard.toString());
        }
    }
    //the end

    public void removeCard(PreCard preCard, boolean side) {
        if (side) {
            sideCards.remove(preCard);
        } else
            mainCards.remove(preCard);

        Print.print(SuccessMessages.removeCardFromDeck);
    }

    public void addCard(String name, boolean side) throws BeingFull, OccurrenceException {
        if (side) {
            if (sideCards.size() >= 15) {
                throw new BeingFull("side deck");
            }
        } else {
            if (mainCards.size() >= 40) {
                throw new BeingFull("main deck");
            }
        }

        PreCard preCard = PreCard.findCard(name);

        if (isPossibleToAdd(name, preCard)) {
            if (side)
                sideCards.add(preCard);
            else
                mainCards.add(preCard);

            Print.print(SuccessMessages.addCardToDeck);
        }

    }

    public boolean equalNames(String name) {
        return name.equals(this.getName());
    }

    private boolean isPossibleToAdd(String nameOfCard, PreCard preCard) throws OccurrenceException {
        int limit = findLimitOfCard(nameOfCard);
        int occurrence = Collections.frequency(mainCards, preCard) +
                Collections.frequency(sideCards,preCard);
        if (occurrence == limit) {
            throw new OccurrenceException(limit, nameOfCard, this.getName());
        }

        return true;
    }

    private int findLimitOfCard(String nameOfCard) {
        int limit = 1; // for when card is limited
        PreSpellTrapCard preSTCard = (PreSpellTrapCard) PreCard.findCard(nameOfCard);
        if (preSTCard == null || preSTCard.getStatus() == CardStatus.UNLIMITED)
            limit = 3;

        return limit;
    }

    @Override
    public String toString() {
        return getName() + "main deck " + mainCards.size() + ", side deck "
                + sideCards.size() + ", valid/invalid?";
    }
}
