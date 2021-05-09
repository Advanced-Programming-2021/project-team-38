package controller;

import exeptions.AlreadyExistingError;
import exeptions.BeingFull;
import exeptions.NotExisting;
import exeptions.OccurrenceException;
import model.Deck;
import model.User;
import model.card.PreCard;
import view.Print;

public class DeckMenuController {
    private static User user;

    public static void createDeck(String deckName) throws AlreadyExistingError {
        if (user.findDeckByName(deckName) != null)
            throw new AlreadyExistingError("deck", "name", deckName);
        else
            new Deck(deckName, user);
    }

    public static void deleteDeck(String deckName) throws NotExisting {
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else
            user.removeDeck(targetDeck);
    }

    public static void chooseActiveDeck(String deckName) throws NotExisting {
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else
            user.setActiveDeck(targetDeck);
    }

    public static void addCardToDeck(String command, boolean side) throws NotExisting, BeingFull, OccurrenceException {   //if it is side deck the boolean should be true
        String cardName = RelatedToMenuController.
                getCommandString(command, "--card ([^-]+) ");
        String deckName = RelatedToMenuController.
                getCommandString(command, "--deck ([^-]+) ");
        Deck targetDeck = user.findDeckByName(deckName);
        if (!user.getCardTreasury().containsKey(cardName) ||
                user.getCardTreasury().get(cardName) == 0)
            throw new NotExisting("card", cardName);
        else if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else {
            targetDeck.addCard(cardName, side);
        }
    }

    public static void removeCardFromDeck(String command, boolean side) throws NotExisting {
        String cardName = RelatedToMenuController.
                getCommandString(command, "--card ([^-]+) ");
        String deckName = RelatedToMenuController.
                getCommandString(command, "--deck ([^-]+) ");
        Deck targetDeck = user.findDeckByName(deckName);
        PreCard targetPreCard = PreCard.findCard(cardName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else if (side && !targetDeck.getSideCards().contains(targetPreCard))
            throw new NotExisting("card", cardName);    //TODO add the side or main thing
        else if (!side && !targetDeck.getMainCards().contains(targetPreCard))
            throw new NotExisting("card", cardName);    //TODO add the side or main thing
        else
            targetDeck.removeCard(targetPreCard, side);
    }

    public static void showAllDecks() {
        Print.print("Decks:\nActive deck:");
        Deck activeDeck = user.getActiveDeck();
        if (activeDeck != null) {
            Print.print(activeDeck.toString());
        }

        Print.print("Other decks:");
        for (Deck deck : user.getDecks()) {
            if (deck != activeDeck)
                Print.print(deck.toString());
        }
    }

    public static void showDeck(String command, boolean side) throws NotExisting {
        String deckName = RelatedToMenuController.
                getCommandString(command, "--deck-name ([^-]+) ");
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);

        targetDeck.showDeck(side);
    }

    public static void showCards() {
        user.printMyCards();
    }
}