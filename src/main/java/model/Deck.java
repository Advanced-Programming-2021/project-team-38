package model;

import view.exceptions.BeingFull;
import view.exceptions.InvalidName;
import view.exceptions.NotExisting;
import view.exceptions.OccurrenceException;
import model.card.CardType;
import model.card.PreCard;
import model.card.spelltrap.CardStatus;
import model.card.spelltrap.PreSpellTrapCard;
import view.Print;
import view.messageviewing.SuccessfulAction;

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

    public static boolean isDeckInvalid(Deck deck) {
        return false;//todo
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
        if (side) preCards = sideCards;
        else preCards = mainCards;

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
        String mainOrSide;
        if (side) {
            sideCards.remove(preCard);
            mainOrSide = "side";
        } else {
            mainCards.remove(preCard);
            mainOrSide = "main";
        }
        new SuccessfulAction("card", "removed from" + mainOrSide + " deck");
    }

    public void addCard(String name, boolean side) throws BeingFull, OccurrenceException {
        String mainOrSide;
        if (side) {
            if (sideCards.size() >= 15) {
                throw new BeingFull("side deck");
            }
            mainOrSide = "side";
        } else {
            if (mainCards.size() >= 40) {
                throw new BeingFull("main deck");
            }
            mainOrSide = "main";
        }

        PreCard preCard = PreCard.findCard(name);

        if (isPossibleToAdd(name, preCard)) {
            if (side)
                sideCards.add(preCard);
            else
                mainCards.add(preCard);

            new SuccessfulAction("card", "added to " + mainOrSide + " deck");
        }

    }

    public boolean equalNames(String name) {
        return name.equals(this.getName());
    }

    private boolean isPossibleToAdd(String nameOfCard, PreCard preCard) throws OccurrenceException {
        int limit = 3;
        if (preCard instanceof PreSpellTrapCard)
            limit = findLimitOfCard(nameOfCard);
        int occurrence = Collections.frequency(mainCards, preCard) +
                Collections.frequency(sideCards, preCard);
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

    public void exchangeCard(String cardName, boolean isFromMainToSide) throws InvalidName, NotExisting, BeingFull, OccurrenceException {
        ArrayList<PreCard> origin;
        if (isFromMainToSide) origin = this.mainCards;
        else origin = this.sideCards;

        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new NotExisting("card", "name");
        if (!origin.contains(preCard)) throw new InvalidName("card", "name in the deck");
        removeCard(preCard, !isFromMainToSide);
        addCard(cardName, isFromMainToSide);
    }

    @Override
    public String toString() {
        return getName() + "main deck " + mainCards.size() + ", side deck "
                + sideCards.size() + ", valid/invalid?";
    }
}
