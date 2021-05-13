package view;

import controller.RelatedToMenuController;
import exeptions.InvalidCommand;
import exeptions.MenuNavigationError;
import exeptions.WrongMenu;

public class Main {
    public static void main(String[] args) {
        boolean isProgramEnded = false;
        while (!isProgramEnded) {
            try {
                Menu.checkMenuCommands();
            } catch (InvalidCommand | MenuNavigationError | WrongMenu exception) {
                System.out.println(exception.getMessage());
            }
            isProgramEnded = RelatedToMenuController.isProgramEnded();
        }
        System.out.println("Good Bye!");
    }
}
