package controller;

import exeptions.AlreadyExistingError;
import exeptions.NotExisting;
import model.Deck;
import model.User;
import view.messageviewing.Print;

import java.util.regex.Matcher;

public class DeckMenuController {
    private static User user;

    public static void createDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        if (user.findDeckByName(deckName) != null)
            new AlreadyExistingError("deck", "name", deckName);
        else
            new Deck(deckName, user);
    }

    public static void deleteDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            new NotExisting("deck", deckName);
        else
            Print.print(user.removeDeck(targetDeck));   //TODO make printing better
    }

    public static void chooseActiveDeck(Matcher matcher) {
        String deckName = matcher.group("deckName");
        Deck targetDeck = user.findDeckByName(deckName);
        if (targetDeck == null)
            new NotExisting("deck", deckName);
        else
            user.setActiveDeck(targetDeck);
    }

    public static void addCardToDeck(Matcher matcher, boolean side) {   //if it is side deck the boolean should be true
        String cardName = matcher.group("cardName");
        String deckName = matcher.group("deckName");
        if (!user.getCardTreasury().containsKey(cardName) ||
                user.getCardTreasury().get(cardName)==0)
            new NotExisting("card", cardName);
        else if (user.findDeckByName(deckName) == null)
            new NotExisting("deck", deckName);
        else {

        }

    }

    public static String removeCardFromDeck(Matcher matcher) {
        return null;
    }

    public static String showAllDecks(Matcher matcher) {
        return null;
    }

    public static String showDeck(Matcher matcher) {
        return null;
    }

    public static String showCards(Matcher matcher) {
        return null;
    }
}