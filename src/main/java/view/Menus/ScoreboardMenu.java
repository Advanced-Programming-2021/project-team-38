package view.Menus;

import controller.RelatedToMenuController;
import controller.ScoreBoardMenuController;
import view.exceptions.InvalidCommand;
import view.exceptions.WrongMenu;
import view.MenuName;

public class ScoreboardMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.SCOREBOARD)) throw new WrongMenu();
        if (command.equals("show"))
            System.out.println(ScoreBoardMenuController.showScoreBoard());
        else throw new InvalidCommand();
    }

}
