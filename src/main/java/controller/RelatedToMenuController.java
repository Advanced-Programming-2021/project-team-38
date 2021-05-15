package controller;

import exceptions.InvalidCommand;
import exceptions.MenuNavigationError;
import view.MenuName;
import view.messageviewing.Print;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelatedToMenuController {

    public static MenuName currentMenu;
    private static boolean programEnded = false;

    public static void enterMenu(String name) throws InvalidCommand, MenuNavigationError {
        MenuName newMenu;
        try {
            newMenu = MenuName.valueOf(name.toUpperCase());
        } catch (Exception e) {
            throw new InvalidCommand();
        }
        if (currentMenu == newMenu)
            throw new MenuNavigationError();
        else if (currentMenu == MenuName.LOGIN && newMenu == MenuName.MAIN)
            currentMenu = newMenu;
        else if (currentMenu == MenuName.MAIN && newMenu != MenuName.LOGIN)
            currentMenu = newMenu;
        else
            throw new MenuNavigationError();
    }

    public static void exitMenu() {
        if (currentMenu == MenuName.LOGIN) {
            programEnded = true;
        } else if (currentMenu == MenuName.MAIN)
            currentMenu = MenuName.LOGIN;
        else
            currentMenu = MenuName.MAIN;
    }

    public static boolean isProgramEnded() {
        return programEnded;
    }


    public static void showMenu() {
        Print.print(currentMenu.stringMenu());
    }

    public static void setProgramEnded(boolean isEnded) {
        programEnded = isEnded;
    }

    public static MenuName getCurrentMenu() {
        return currentMenu;
    }

    public static boolean isMenuFalse(MenuName menuName) {
        return currentMenu != menuName;
    }

    public static boolean hasProgramEnded() {
        return programEnded;
    }

    public static String getCommandString(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find())
            return null;
        else
            if (matcher.groupCount() == 0)
                return matcher.group(0);
            else
                return matcher.group(1);
    }
}
