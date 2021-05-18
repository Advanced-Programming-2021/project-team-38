package controller;

import exceptions.InvalidName;
import exceptions.NotEnoughMoney;
import model.User;
import model.card.PreCard;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShopMenuController {
    private static HashMap<String, Integer> allCards; //todo:  Why did we need this? (I'm negar!)
    private static User user;

    public static String showAllCards() {
        ArrayList<PreCard> allPreCards = (ArrayList<PreCard>) PreCard.getAllPreCardsInstances().keySet(); //todo: what is the error?!
        ArrayList<String> cards = new ArrayList<>();
        for (PreCard preCard : allPreCards) {
            cards.add(preCard.getName() + ": " + preCard.getDescription());
        }
        Collections.sort(cards);
        StringBuilder cardsToShow = new StringBuilder();
        for (String card : cards) {
            cardsToShow.append(card).append("\n");
        }
        return cardsToShow.toString();
    }

    public static void checkBuying(String cardName) throws NotEnoughMoney, InvalidName {
        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidName("card", "name");
        user = LoginMenuController.getCurrentUser();
        if (!user.getCardTreasury().containsKey(cardName) &&
                preCard.getPrice() > user.getBalance()) throw new NotEnoughMoney();
        sellCard(preCard);
    }

    private static void sellCard(PreCard preCard) {
        int price;
        if (user.getCardTreasury().containsKey(preCard.getName())) price = 0;
        else price = preCard.getPrice();
        user.decreaseBalance(price);
        user.addPreCard(preCard);
        new SuccessfulAction("card " + preCard.getName(), "is sold");
    }
}