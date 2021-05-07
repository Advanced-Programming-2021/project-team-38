package controller;

import exeptions.MenuNavigationError;
import view.MenuName;
import view.Print;

public class RelatedToMenuController {

    public static MenuName currentMenu;
    private static boolean programEnded = false;

    public static void enterMenu(String name) {
        MenuName newMenu = MenuName.searchForMenuName(name);
        if (currentMenu == newMenu)
            new MenuNavigationError();
        else if (currentMenu == MenuName.LOGIN && newMenu == MenuName.MAIN)
            currentMenu = newMenu;
        else if (currentMenu == MenuName.MAIN && newMenu != MenuName.LOGIN)
            currentMenu = newMenu;
        else
            new MenuNavigationError();
    }

    public static void exitMenu() {
        if (currentMenu == MenuName.LOGIN) {
            //TODO endgame
        } else if (currentMenu == MenuName.MAIN)
            currentMenu = MenuName.LOGIN;
        else
            currentMenu = MenuName.MAIN;
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


}
