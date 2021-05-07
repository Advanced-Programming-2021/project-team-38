package controller;

import view.MenuName;

import java.util.ArrayList;

public class RelatedToMenuController {

    private static ArrayList<MenuName> upperMenus;
    private static MenuName currentMenu;
    private static boolean programEnded = false;

    static {
        upperMenus = new ArrayList<>();
    }

    public static void setCurrentMenu(MenuName menuName) {
        currentMenu = menuName;
        upperMenus.add(menuName);
    }

    public static void exitMenu() {
        if (currentMenu.equals(MenuName.LOGIN)) setProgramEnded(true);
        upperMenus.remove(currentMenu);
        currentMenu = null;
    }

    public static void setProgramEnded(boolean isEnded) {
        programEnded = isEnded;
    }

    public static MenuName getCurrentMenu() {
        return currentMenu;
    }

    public static boolean hasProgramEnded() {
        return programEnded;
    }

    public static boolean isMenuCorrect(MenuName menuName) {
        return currentMenu.equals(menuName);
    }

}
