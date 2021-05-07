package controller;

import exeptions.InvalidCardName;
import exeptions.NotEnoughMoney;
import model.User;
import model.card.Card;
import model.card.CardType;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ShopMenuController {
    private static HashMap<String, Integer> allCards;
    private static User user;

    public static String showAllCards() {
        return null;
    }

    public static void buy(String cardName) {
        try {
            CardType cardType = CardType.valueOf(cardName);
            Card cardToSell = new Card(); //todo : we should make the card based on the name
            sellCard(cardToSell);
        } catch (Exception e) {
            new InvalidCardName();
        }
    }

    private static void sellCard(Card card) {
        if (user.getBalance() < card.getPrice()) {
            new NotEnoughMoney();
        } else {
            user.decreaseBalance(card.getPrice());
            user.addCard(card);
            System.out.println("Card purchase was successful!");
        }
    }
}