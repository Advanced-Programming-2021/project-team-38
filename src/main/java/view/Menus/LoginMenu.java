package view.Menus;

import controller.LoginMenuController;
import controller.RelatedToMenuController;
import exceptions.AlreadyExistingError;
import exceptions.InvalidCommand;
import exceptions.LoginError;
import exceptions.WrongMenu;
import view.MenuName;

import java.util.regex.Matcher;

import static view.Menu.getCommandMatcher;

public class LoginMenu {
    public static void sendLoginInfo() {

    }

    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.LOGIN)) {
            throw new WrongMenu();
        }
        if (command.startsWith("create "))
            createUser(command);
        else if (command.startsWith("login "))
            login(command);
        else
            throw new InvalidCommand();
    }

    private static void createUser(String command) throws InvalidCommand {
        Matcher usernameMatcher = getCommandMatcher(command, "--username (<username>\\S+)");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (<password>\\S+)");
        Matcher nickMatcher = getCommandMatcher(command, "--nickname (<nickName>\\S+)");
        if (usernameMatcher.find() && passwordMatcher.find() && nickMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            String nickName = nickMatcher.group("nickName");
            try {
                LoginMenuController.createUser(username, nickName, password);
            } catch (AlreadyExistingError alreadyExistingError) {
                System.out.println(alreadyExistingError.getMessage());
            }
        } else throw new InvalidCommand();
    }

    private static void login(String command) throws InvalidCommand {
        Matcher usernameMatcher = getCommandMatcher(command, "--username (<username>\\S+)");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (<password>\\S+)");
        if (usernameMatcher.find() && passwordMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            try {
                LoginMenuController.login(username, password);
            } catch (LoginError loginError) {
                System.out.println(loginError.getMessage());
            }
        } else throw new InvalidCommand();
    }

}