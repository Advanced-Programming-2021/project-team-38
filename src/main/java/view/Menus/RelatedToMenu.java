package view.Menus;

import controller.RelatedToMenuController;
import exeptions.InvalidCommand;
import exeptions.MenuNavigationError;
import view.MenuName;

import java.util.ArrayList;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) {
        if (command.equals("exit"))
            RelatedToMenuController.exitMenu();
        else if (command.startsWith("enter ")) {
            if (RelatedToMenuController.getCurrentMenu() != null)
                new MenuNavigationError();
            else {
                String menuNameString = command.substring(6);
                MenuName menuName = MenuName.valueOf(menuNameString);
                try {
                    RelatedToMenuController.setCurrentMenu(menuName);
                } catch (Exception e) {
                    new InvalidCommand(); //todo : If the menu name isn't valid, should the output be invalid command or another error?
                }
            }
        } else if (command.equals("show-current"))
            System.out.println(RelatedToMenuController.getCurrentMenu().name());
    }

}
