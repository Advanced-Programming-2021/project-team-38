package view.Menus;

import controller.LoginMenuController;
import controller.RelatedToMenuController;
import exeptions.AlreadyExistingError;
import exeptions.InvalidCommand;
import exeptions.LoginError;
import view.MenuName;

import java.util.regex.Matcher;

import static view.Menu.getCommandMatcher;

public class LoginMenu {
    public static void sendLoginInfo() {

    }

    public static void checkMenuCommands(String command) throws InvalidCommand, AlreadyExistingError, LoginError {
        if (RelatedToMenuController.isMenuFalse(MenuName.LOGIN)) {
            throw new InvalidCommand(); //TODO why invalid command?
        }
        if (command.startsWith("create ")) {
            createUser(command);
        } else if (command.startsWith("login ")) {
            login(command);
        } else
            throw new InvalidCommand();
    }

    private static void createUser(String command) throws InvalidCommand, AlreadyExistingError {
        Matcher usernameMatcher = getCommandMatcher(command, "--username (<username>\\S+)");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (<password>\\S+)");
        Matcher nickMatcher = getCommandMatcher(command, "--nickname (<nickName>\\S+)");
        if (usernameMatcher.find() && passwordMatcher.find() && nickMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            String nickName = nickMatcher.group("nickName");
            LoginMenuController.createUser(username, nickName, password);
        } else throw new InvalidCommand();
    }

    private static void login(String command) throws InvalidCommand, LoginError {
        Matcher usernameMatcher = getCommandMatcher(command, "--username (<username>\\S+)");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (<password>\\S+)");
        if (usernameMatcher.find() && passwordMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            LoginMenuController.login(username, password);
        } else throw new InvalidCommand();
    }

}
