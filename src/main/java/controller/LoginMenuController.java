package controller;

import exeptions.AlreadyExistingError;
import exeptions.LoginError;
import model.User;
import view.messageviewing.SuccessfulAction;

public class LoginMenuController {
    private static User currentUser;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void createUser(String username, String nickname, String password) throws AlreadyExistingError {
        if (hasNoCreatingError(username, nickname)) {
            new User(username, password, nickname);
            new SuccessfulAction("user", "created");
        }
    }

    private static boolean hasNoCreatingError(String username, String nickname) throws AlreadyExistingError {
        if (User.getUserByName(username) != null)
            throw new AlreadyExistingError("user", "username", username);
        else if (User.getUserByNickName(nickname) != null)
            throw new AlreadyExistingError("user", "nickname", nickname);
        else return true;
//        return false;
    }

    public static void login(String username, String password) throws LoginError {
        if (hasNoLoginError(username, password)) {
            currentUser = User.getUserByName(username);
            new SuccessfulAction("user", "logged in");
            setUserInClasses(currentUser);
        }
    }

    private static boolean hasNoLoginError(String username, String password) throws LoginError {
        User user = User.getUserByName(username);
        if (user == null)
            throw new LoginError();
        else if (user.isPasswordCorrect(password))
            throw new LoginError();
        else return true;
//        return false;
    }

//    public static String logout(Matcher matcher) {
//        return null;
//    }

    private static void setUserInClasses(User user) {
        //todo : what do I do!?
    }

}