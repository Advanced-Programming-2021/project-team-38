package view;

import exceptions.InvalidCommand;
import exceptions.MenuNavigationError;
import exceptions.WrongMenu;
import view.Menus.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static void checkMenuCommands() throws InvalidCommand, MenuNavigationError, WrongMenu {
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
            try {
                DuelMenu.checkMenuCommands(command.substring(5));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (command.equals("user logout"))
            MainMenu.logout();
        else
            throw new InvalidCommand();
        //todo : the command "card show <card name>" can be used in duel, shop, and deck menus
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        if (input == null) return null;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}
