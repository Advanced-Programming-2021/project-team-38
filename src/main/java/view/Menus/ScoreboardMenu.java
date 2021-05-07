package view.Menus;

import controller.ScoreBoardMenuController;
import exeptions.InvalidCommand;

public class ScoreboardMenu {
    public static void checkMenuCommands(String command) {
        if (command.equals("show"))
            System.out.println(ScoreBoardMenuController.showScoreBoard());
        else new InvalidCommand();
    }

}
