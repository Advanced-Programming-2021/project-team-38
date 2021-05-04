package view.Menus;

import controller.RelatedToMenuController;
import exeptions.InvalidCommand;
import view.MenuName;

public class DeckMenu {
    public static void checkMenuCommands(String command) {
        if (!RelatedToMenuController.isMenuCorrect(MenuName.DECK)) {
            new InvalidCommand();
        }


    }

    public static String scanNameOfCard() {

        return null;
    }
}
