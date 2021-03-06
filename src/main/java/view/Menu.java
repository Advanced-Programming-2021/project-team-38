package view;

import controller.RelatedToMenuController;
import view.Menus.*;
import view.exceptions.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static void run() {
        RelatedToMenuController.setCurrentMenu(MenuName.LOGIN);
        boolean isProgramEnded = false;
        while (!isProgramEnded) {
            try {
                Menu.checkMenuCommands();
            } catch (InvalidCommand | MenuNavigationError | WrongMenu | NeedToLogin | InvalidDeck | InvalidName | InvalidThing | NoActiveDeck | NumOfRounds | NotExisting exception) {
                Print.print(exception.getMessage());
            }
            isProgramEnded = RelatedToMenuController.isProgramEnded();
        }
    }

    public static void checkMenuCommands() throws InvalidCommand, MenuNavigationError, WrongMenu, NeedToLogin, InvalidDeck, InvalidName, InvalidThing, NoActiveDeck, NumOfRounds, NotExisting {
        String command = scanner.nextLine();
        if (command.startsWith("menu "))
            RelatedToMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("user ") && !command.equals("user logout"))
            LoginMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("scoreboard "))
            ScoreboardMenu.checkMenuCommands(command.substring(11));
        else if (command.startsWith("profile "))
            ProfileMenu.checkMenuCommands(command.substring(8));
        else if (command.startsWith("deck "))
            DeckMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("shop "))
            ShopMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("duel ")) {
            DuelMenu.checkMenuCommands(command.substring(5));
        } else if (command.equals("user logout"))
            MainMenu.logout();
        else if (command.equals("exit program"))
            LoginMenu.checkMenuCommands(command);
        else if (command.startsWith("card show "))
            RelatedToMenu.checkMenuCommands(command);
        else if (command.startsWith("import card")) {

        } else
            throw new InvalidCommand();
        //todo : the command "card show <card name>" can be used in shop, and deck menus
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        if (input == null) return null;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}
