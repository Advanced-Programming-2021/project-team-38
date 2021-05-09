package view.Menus;

import controller.ShopMenuController;
import exeptions.InvalidCardName;
import model.card.CardType;

public class ShopMenu {
    public static void checkMenuCommands(String command) {
        if (command.startsWith("buy ")) {
            String cardName = command.substring(4);
            ShopMenuController.buy(cardName);
        }
    }

}
