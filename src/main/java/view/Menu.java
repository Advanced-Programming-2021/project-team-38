package view;

import exeptions.InvalidCommand;
import view.Menus.*;

import java.util.Scanner;

public class Menu {
    protected static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public void checkMenuCommands() {
        String command = scanner.nextLine();
        if (command.startsWith("menu "))
            RelatedToMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("user "))
            LoginMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("scoreboard "))
            ScoreboardMenu.checkMenuCommands(command.substring(11));
        else if (command.startsWith("profile "))
            ProfileMenu.checkMenuCommands(command.substring(8));
        else if (command.startsWith("deck "))
            DeckMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("shop "))
            ShopMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("duel "))
            DuelMenu.checkMenuCommands(command.substring(5));
        else
            new InvalidCommand();
    }
}
