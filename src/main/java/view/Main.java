package view;

import controller.RelatedToMenuController;

public class Main {
    public static void main(String[] args) {
        boolean isProgramEnded = true;
        while (isProgramEnded) {
            Menu.checkMenuCommands();
            isProgramEnded = RelatedToMenuController.isProgramEnded();
        }
        System.out.println("Good Bye!");
    }
}
