package view.Menus;

import controller.RelatedToMenuController;
import controller.game.DuelMenuController;
import exceptions.*;
import view.Menu;
import view.MenuName;
import view.messageviewing.Print;

import java.util.Scanner;
import java.util.regex.Matcher;

public class DuelMenu {
    private static final Scanner scanner;
    private static DuelMenuController duelMenuController = null;

    static {
        scanner = new Scanner(System.in);
    }

    public static boolean supportsCommand(String command) {
        return true;//todo
    }

    public static void checkMenuCommands(String command)
            throws InvalidCommand, WrongMenu, InvalidDeck, InvalidName, NoActiveDeck, NumOfRounds {
        if (RelatedToMenuController.isMenuFalse(MenuName.DUEL))
            throw new WrongMenu();
        if (command.contains("--new")) {
            if (duelMenuController == null) {
                String secondUserName = getSecondUserNameInCommand(command.substring(9));
                int numOfRounds = getNumOfRounds(command.substring(9));
                duelMenuController = DuelMenuController.newDuel(secondUserName, numOfRounds);
                while (duelMenuController.isIsAnyBattleRunning()) {
                    duelMenuController.runGame();
//                    try {
//                        checkCommandsInGame();
//                    } catch (Exception exception) {
//                        System.out.println(exception.getMessage());
//                    }
                }
                duelMenuController = null;
            }
        } else {
            throw new InvalidCommand();
        }

    }

    public static void checkCommandsInGame() throws InvalidCommand { //todo: surrender
        try {
            String command = scanner.nextLine();
            if (command.equals("select -d"))
                duelMenuController.deselectCard();
            else if (command.startsWith("select "))
                duelMenuController.selectCard(command.substring(7));
            else if (command.equals("summon"))
                duelMenuController.summonMonster(false);
            else if (command.equals("set"))
                duelMenuController.setCard();
            else if (command.startsWith("set -- position "))
                duelMenuController.changePosition(command.substring(16).equals("attack"));
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
        } catch (Exception exception) {
            if (exception instanceof InvalidCommand) throw new InvalidCommand();
            System.out.println(exception.getMessage());
        }
    }

    private static int getNumOfRounds(String command) throws InvalidCommand {
        command = command.concat(" ");
        Matcher matcher = Menu.getCommandMatcher(command, "--round (<round>\\d+) ");
        if (matcher.find()) {
            return Integer.parseInt(matcher.group("round"));
        } else throw new InvalidCommand();
    }

    private static String getSecondUserNameInCommand(String command) throws InvalidCommand {
        command = command.concat(" ");
        Matcher matcher = Menu.getCommandMatcher(command, "--second-player (<username>\\S+) ");
        if (matcher.find()) {
            return matcher.group("username");
        } else {
            matcher = Menu.getCommandMatcher(command, "--ai");
            if (matcher.find())
                return "AI";
            else throw new InvalidCommand();
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
