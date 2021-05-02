package exeptions;

import view.PrintError;

public class LoginError {
    public LoginError() {
        PrintError.print("Username and password didn’t match!");
    }
}
