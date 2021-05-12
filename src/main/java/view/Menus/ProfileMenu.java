package view.Menus;

import controller.ProfileMenuController;
import controller.RelatedToMenuController;
import exeptions.*;
import view.MenuName;

public class ProfileMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu, WrongPassword, EqualPasswordException, AlreadyExistingError {
        if (RelatedToMenuController.isMenuFalse(MenuName.PROFILE))
            throw new WrongMenu();
        else if (command.startsWith("change "))
                ProfileMenuController.changeCommands(command.substring(7));
        else
            throw new InvalidCommand();
    }

}
