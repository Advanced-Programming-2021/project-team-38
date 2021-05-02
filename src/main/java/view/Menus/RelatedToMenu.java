package view.Menus;

import controller.RelatedToMenuController;
import exeptions.InvalidCommand;

import java.util.ArrayList;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) {
        if (command.startsWith("enter ")) {
            RelatedToMenuController.enterMenu(command.substring(6));
        } else if (command.matches("exit")) {
            RelatedToMenuController.exitMenu();
        } else if (command.matches("show-current")) {
            RelatedToMenuController.showMenu();
        } else
            new InvalidCommand();
    }
}
