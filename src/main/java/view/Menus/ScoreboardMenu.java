package view.Menus;

import controller.ScoreBoardMenuController;
import exeptions.InvalidCommand;

public class ScoreboardMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand {
        if (command.equals("show"))
            System.out.println(ScoreBoardMenuController.showScoreBoard());
        else throw new InvalidCommand();
    }

}
