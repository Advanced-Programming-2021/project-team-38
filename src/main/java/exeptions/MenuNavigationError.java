package exeptions;

import view.PrintError;

public class MenuNavigationError extends Exception {
    public MenuNavigationError() {
        super("menu navigation is not possible");
    }
}
