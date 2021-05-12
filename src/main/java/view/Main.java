package view;

import controller.RelatedToMenuController;
import exeptions.*;

public class Main {
    public static void main(String[] args) throws InvalidCommand, MenuNavigationError, AlreadyExistingError, OccurrenceException, NotExisting, BeingFull, LoginError, WrongPassword, EqualPasswordException, WrongMenu {
        boolean isProgramEnded = false;
        while (!isProgramEnded) {
            Menu.checkMenuCommands();
            isProgramEnded = RelatedToMenuController.isProgramEnded();
        }
        System.out.println("Good Bye!");
    }
}
