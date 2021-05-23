package view;

import controller.RelatedToMenuController;
import exceptions.InvalidCommand;
import exceptions.MenuNavigationError;
import exceptions.NeedToLogin;
import exceptions.WrongMenu;
import model.card.CardLoader;

public class Main {
    public static void main(String[] args) {
        CardLoader.loadCsv();
        Print.print("Welcome to Yo Gi Oh!");
        boolean isProgramEnded = false;
        while (!isProgramEnded) {
            try {
                Menu.checkMenuCommands();
            } catch (InvalidCommand | MenuNavigationError | WrongMenu | NeedToLogin exception) {
                Print.print(exception.getMessage());
            }
            isProgramEnded = RelatedToMenuController.isProgramEnded();
        }
    }
}
