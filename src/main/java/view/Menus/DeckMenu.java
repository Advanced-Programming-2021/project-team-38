package view.Menus;

import controller.RelatedToMenuController;
import exeptions.InvalidCommand;
import view.MenuName;

public class DeckMenu {
    public void checkMenuCommands(String command) {
        final String createDeck = "create ";
        final String deleteDeck = "delete ";
        final String activeDeck = "set-activate ";
        final String addCard = "add-card ";
        final String showAllDecks = "show --all";
        final String showDeck = "show ";
        final String showAllCards = "show --cards";

        if (!RelatedToMenuController.isMenuCorrect(MenuName.DECK)) {
            new InvalidCommand();
        }


//        if (command.startsWith(createDeck))
//
//            else if (command.startsWith(deleteDeck))
//
//                else if (command.startsWith(activeDeck))
    }

    public static String scanNameOfCard() {

        return null;
    }
}
