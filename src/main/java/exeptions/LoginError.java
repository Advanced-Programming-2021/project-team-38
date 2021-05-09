package exeptions;

import view.PrintError;

public class LoginError extends Exception {
    public LoginError() {
        super("Username and password didn't match!");
    }
}
