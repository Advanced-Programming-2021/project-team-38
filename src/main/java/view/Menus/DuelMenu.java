package view.Menus;

import controller.RelatedToMenuController;
import controller.game.DuelMenuController;
import exceptions.*;
import view.Menu;
import view.MenuName;
import view.messageviewing.Print;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class DuelMenu {
    private static final Scanner scanner;
    private static DuelMenuController duelMenuController;

    static {
        scanner = new Scanner(System.in);
    }

    public static void checkMenuCommands(String command)
            throws InvalidCommand, WrongMenu, InvalidDeck, InvalidName, NoActiveDeck, NumOfRounds {
        if (RelatedToMenuController.isMenuFalse(MenuName.DUEL))
            throw new WrongMenu();
        if (command.contains("--new")) {
            sendNewDuelRequest(command);
        } else throw new InvalidCommand();
    }

    private static void sendNewDuelRequest(String command) throws InvalidCommand, InvalidName, NumOfRounds, InvalidDeck, NoActiveDeck {
        if (duelMenuController == null) {
            String secondUserName = getSecondUserNameInCommand(command.substring(6));
            int numOfRounds = getNumOfRounds(command.substring(6));
            duelMenuController = DuelMenuController.newDuel(secondUserName, numOfRounds);
            while (duelMenuController != null && duelMenuController.isIsAnyGameRunning()) {
                duelMenuController.runMatch();
            }
            duelMenuController = null;
        }
    }

    public static void checkCommandsInRound() throws InvalidCommand {
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
                Print.print(duelMenuController.showGraveYard(true));
            else if (command.equals("show graveyard --opponent"))
                Print.print(duelMenuController.showGraveYard(false)); //todo: fine?
            else if (command.equals("card show --selected"))
                duelMenuController.showCard();
            else if (command.equals("next phase"))
                duelMenuController.nextPhase();
            else if (command.equals("surrender"))
                duelMenuController.surrender();
            else
                throw new InvalidCommand();
        } catch (Exception exception) {
            if (exception instanceof InvalidCommand) throw new InvalidCommand();
            Print.print(exception.getMessage());
        }
    }

    private static int getNumOfRounds(String command) throws InvalidCommand {
        command = command.concat(" ");
        Matcher matcher = Menu.getCommandMatcher(command, "--rounds (?<rounds>\\d+) ");
        if (matcher.find()) {
            return Integer.parseInt(matcher.group("rounds"));
        } else throw new InvalidCommand();
    }

    private static String getSecondUserNameInCommand(String command) throws InvalidCommand {
        command = command.concat(" ");
        Matcher matcher = Menu.getCommandMatcher(command, "--second-player (?<username>\\S+) ");
        if (matcher.find()) {
            return matcher.group("username");
        } else {
            matcher = Menu.getCommandMatcher(command, "--ai");
            if (matcher.find())
                return "AI";
            else throw new InvalidCommand();
        }
    }

    public static void showHeadOrTails(boolean isHead, String firstName, String secondName) {
        Print.print("Playing head or tails to figure out the first player...");
        if (isHead) Print.print("HEAD!");
        else Print.print("TAILS!");
        Print.print("The first player is " + firstName + "\n" + "The second player is " + secondName);
    }

    public static String askQuestion(String questionToAsk) {
        Print.print(questionToAsk);
        return scanner.nextLine();
    }

    public static String askForSth(String wanted) {
        Print.print(wanted);
        return scanner.nextLine();
    }

    public static void showWinner(String winnerUsername, int winnerScore, int loserScore, boolean isForMatch) {
        String matchOrGame;
        if (!isForMatch) matchOrGame = "game";
        else matchOrGame = "whole match";
        Print.print(winnerUsername + " won the " + matchOrGame + " and the score is: " + winnerScore + "-" + loserScore);
    }

    public static void showPhase(String phaseName) {
        Print.print("phase: " + phaseName);
    }

    public static boolean askToSelectRitualMonsterCard() { //returns true if the process if cancelled
        Print.print("Please select a card for ritual summon and summon it.");
        boolean isFine = false;
        do {
            String command = scanner.nextLine();
            if (command.equals("cancel")) return true;
            if (command.startsWith("select "))
                try {
                    duelMenuController.selectCard(command.substring(7));
                    isFine = true;
                } catch (InvalidSelection | NoCardFound exception) {
                    Print.print(exception.getMessage());
                }
        } while (!isFine);
        return false;
    }

    public static ArrayList<String> getTributeAddresses() {
        Print.print("Enter the tribute addresses." +
                " Note that the sum of their levels should be equal to the ritual monster's level.\n" +
                "Enter \"End\" when you were done");
        ArrayList<String> addresses = new ArrayList<>();
        String command;
        do {
            command = scanner.nextLine();
            if (command.equals("cancel")) return null;
            addresses.add(command);
        } while (!command.equals("End"));
        return addresses;
    }

    public static boolean forceGetCommand(String commandToForceEnter, String messageToShow) { //returns true if it's cancelled;
        String command;
        while (true) {
            Print.print("Enter " + commandToForceEnter);
            command = scanner.nextLine();
            if (command.equals(commandToForceEnter)) return false;
            if (command.equals("cancel")) return true;
            Print.print(messageToShow);
        }
    }

    public static String getRitualManner() {
        Print.print("Enter a manner for the ritual monster (OO/DO)");
        String command = scanner.nextLine();
        switch (command) {
            case "OO":
            case "DO":
                return command;
            default:
                Print.print("you should ritual summon right now");
                return getRitualManner();
        }

    }
}
