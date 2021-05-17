package view;

import controller.RelatedToMenuController;
import exceptions.InvalidCommand;
import exceptions.MenuNavigationError;
import exceptions.WrongMenu;

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
