package view.Menus;

import controller.ShopMenuController;
import exceptions.InvalidCommand;
import exceptions.InvalidName;
import exceptions.NotEnoughMoney;
import view.messageviewing.Print;

public class ShopMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand {
        if (command.startsWith("buy ")) {
            String cardName = command.substring(4);
            try {
                ShopMenuController.checkBuying(cardName);
            } catch (InvalidName | NotEnoughMoney exception) {
                System.out.println(exception.getMessage());
            }
        } else if (command.equals("show --all")) {
            Print.print(ShopMenuController.showAllCards());
        } else throw new InvalidCommand();
    }

}
