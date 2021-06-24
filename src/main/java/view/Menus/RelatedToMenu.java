package view.Menus;

import controller.RelatedToMenuController;
import view.exceptions.InvalidCommand;
import view.exceptions.MenuNavigationError;
import view.exceptions.NeedToLogin;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, MenuNavigationError, NeedToLogin {
        if (command.startsWith("enter ")) {
            RelatedToMenuController.enterMenu(command.substring(6));
        } else if (command.matches("exit")) {
            RelatedToMenuController.exitMenu();
        } else if (command.matches("show-current")) {
            RelatedToMenuController.showMenu();
        } else if (command.equals("help")) {
            RelatedToMenuController.showMenuHelp();
        } else throw new InvalidCommand();
    }
}
