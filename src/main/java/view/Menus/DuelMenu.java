package view.Menus;

import controller.RelatedToMenuController;
import controller.game.DuelMenuController;
import exceptions.InvalidCommand;
import exceptions.WrongMenu;
import exceptions.WrongPhaseForAction;
import view.Menu;
import view.MenuName;
import view.messageviewing.Print;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class DuelMenu {
    private static final Scanner scanner;
    public static ArrayList<String> commands;
    private static DuelMenuController duelMenuController = null;

    static {
        scanner = new Scanner(System.in);
    }

    public static boolean supportsCommand(String command) {
        return true;//todo
    }

    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.PROFILE))
            throw new WrongMenu();
        try {
            if (command.startsWith("duel new ")) {
                if (duelMenuController == null) {
                    duelMenuController = DuelMenuController.newDuel(); //todo the regex stuff. the input shouldn't be empty
                    //todo: sout the draw phase name?
                }
            } else {
                if (duelMenuController != null) {
                    if (command.equals("select -d"))
                        duelMenuController.deselectCard();
                    else if (command.startsWith("select "))
                        duelMenuController.selectCard(command.substring(7));
                    else if (command.equals("summon"))
                        duelMenuController.summonMonster(false);
                    else if (command.equals("set"))
                        duelMenuController.setCard();
                    else if (command.startsWith("set -- position "))
                        duelMenuController.setPosition(command.substring(16).equals("attack"));
                    else if (command.equals("flip-summon"))
                        duelMenuController.summonMonster(true);
                    else if (command.equals("attack direct"))
                        duelMenuController.attackDirect();
                    else if (command.startsWith("attack ")) {
                        Matcher matcher = Menu.getCommandMatcher(command.substring(7), "(%d)");
                        if (matcher.find()) duelMenuController.attack(Integer.parseInt(matcher.group()));
                    } else if (command.equals("activate effect"))
                        duelMenuController.activateEffect();
                    else if (command.equals("show graveyard"))
                        duelMenuController.showGraveYard(true);
                    else if (command.equals("card show --selected"))
                        duelMenuController.showCard();
                    else if (command.equals("next phase"))
                        duelMenuController.nextPhase();
                    else
                        throw new InvalidCommand();
                } else {
                    //todo: duel menu controller is null. what is the error?
                }
            }
        } catch (WrongPhaseForAction exception) {
            System.out.println(exception.getMessage());
        }
    }

//        public static String scanNameOfCard(){
//
//    }

    public static String askQuestion(String questionToAsk) {
        Print.print(questionToAsk);
        return scanner.nextLine();
    }

}
