package view;

import controller.RelatedToMenuController;

public class Main {
    public static void main(String[] args) {
        boolean hasProgramEnded = false;
        while (!hasProgramEnded) {
            Menu.checkMenuCommands();
            hasProgramEnded = RelatedToMenuController.hasProgramEnded();
        }
        System.out.println("Good Bye!");
    }
}
