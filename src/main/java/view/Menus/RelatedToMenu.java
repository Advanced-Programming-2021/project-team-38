package view.Menus;

import controller.RelatedToMenuController;
import exceptions.InvalidCommand;
import exceptions.MenuNavigationError;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, MenuNavigationError {
        if (command.startsWith("enter ")) {
            RelatedToMenuController.enterMenu(command.substring(6));
        } else if (command.matches("exit")) {
            RelatedToMenuController.exitMenu();
        } else if (command.matches("show-current")) {
            RelatedToMenuController.showMenu();
        } else
            throw new InvalidCommand();
    }

}
