package view.Menus;

import controller.RelatedToMenuController;
import controller.ShopMenuController;
import view.exceptions.InvalidCommand;
import view.exceptions.InvalidName;
import view.exceptions.NotEnoughMoney;
import view.exceptions.WrongMenu;
import view.MenuName;
import view.messageviewing.Print;

public class ShopMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.SHOP))
            throw new WrongMenu();
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
