package view.Menus;

import controller.RelatedToMenuController;
import model.card.PreCard;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.PreSpellTrapCard;
import view.MenuName;
import view.Print;
import view.exceptions.*;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, MenuNavigationError, NeedToLogin, WrongMenu, NotExisting {
        if (command.startsWith("enter ")) {
            RelatedToMenuController.enterMenu(command.substring(6));
        } else if (command.matches("exit")) {
            RelatedToMenuController.exitMenu();
        } else if (command.matches("show-current")) {
            RelatedToMenuController.showMenu();
        } else if (command.equals("help")) {
            RelatedToMenuController.showMenuHelp();
        } else if (command.startsWith("card show ")) {
            MenuName currentMenu = RelatedToMenuController.currentMenu;
            if (currentMenu == MenuName.DECK || currentMenu == MenuName.SHOP)
                showCard(command.substring(10));
            else throw new WrongMenu();
        } else throw new InvalidCommand();


    }

    public static void showCard(String cardName) throws NotExisting {
        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new NotExisting("Card", cardName);
        else {
            Print.print(preCard.getName());
            Print.print(preCard.getCardType().toString().toLowerCase());
            if (preCard instanceof PreMonsterCard) {
                Print.print("Level:\t" + ((PreMonsterCard) preCard).getLevel());
                Print.print("Type:\t" + ((PreMonsterCard) preCard).getMonsterType().toString().toLowerCase());
                Print.print("ATK:\t" + ((PreMonsterCard) preCard).getAttack());
                Print.print("DEF:\t" + ((PreMonsterCard) preCard).getDefense());
            } else {
                Print.print("Type:\t" + ((PreSpellTrapCard) preCard).getIcon().toString().toLowerCase());
            }
            Print.print("Description:\n\t" + preCard.getDescription());
        }
    }
}

