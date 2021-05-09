package view.Menus;

import controller.ProfileMenuController;
import exeptions.InvalidCommand;

public class ProfileMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand {
        if (command.startsWith("change "))
            ProfileMenuController.changeCommands(command.substring(7));
        else
            throw new InvalidCommand();
    }

}
