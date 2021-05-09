package controller;

import exeptions.InvalidCardName;
import exeptions.NotEnoughMoney;
import model.User;
import model.card.Card;
import model.card.CardType;
import model.card.PreCard;

import java.util.HashMap;

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
//            sellCard(cardToSell);
        } catch (Exception e) {
            new InvalidCardName();
        }
    }

    private static void sellCard(PreCard preCard) {
        if (user.getBalance() < preCard.getPrice()) {
            new NotEnoughMoney();
        } else {
            user.decreaseBalance(preCard.getPrice());
            user.addPreCard(preCard);
            System.out.println("Card purchase was successful!");
        }
    }
}