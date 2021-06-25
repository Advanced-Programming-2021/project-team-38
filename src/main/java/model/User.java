
package model;

import model.card.PreCard;
import view.Print;
import view.SuccessMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class User implements Comparable<model.User> {
    private final String username;
    private String password;
    private String nickName;
        private int score;
    private final HashMap<String, Integer> cardTreasury;   //shows how many cards do we have of each type
    //TODO the hashmap key must be deleted after the value becomes 0
//    private ArrayList<PreCard> preCards;
    private final ArrayList<Deck> decks;
    private int balance;
        private Deck activeDeck;
        private final static ArrayList<model.User> allUsers;

        static {
            allUsers = new ArrayList<>();
        }

    {
        cardTreasury = new HashMap<>();
//        preCards = new ArrayList<>();
        decks = new ArrayList<>();
    }

        public User(String username, String password, String nickName) {
            this.username = username;
            this.password = password;
            this.nickName = nickName;
            this.score = 0;
            this.balance = 100000; //todo I'm not sure!
            allUsers.add(this);

        }

        public static User getUserByName(String username) {
            for (User user : allUsers) {
                if (user.username.equals(username)) {
                    return user;
                }
            }
            return null;
        }

        public static User getUserByNickName(String nickName) {
            for (model.User user : allUsers) {
                if (user.nickName.equals(nickName)) {
                    return user;
                }
            }
            return null;
        }

        public static String showScoreBoard() {
            StringBuilder scoreBoard = new StringBuilder();
            allUsers.sort(model.User::compareTo);
            int counter = 1;
            model.User previousUser = null;
            for (model.User user : allUsers) {
                if (previousUser != null && user.score != previousUser.score) counter++;
                scoreBoard.append(counter).append("- ").append(user.username).append(": ").append(user.getScore()).append("\n");
                previousUser = user;
            }
            return scoreBoard.toString();
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getNickName() {
            return nickName;
        }

        public int getScore() {
            return score;
        }

        public HashMap<String, Integer> getCardTreasury() {
            return cardTreasury;
        }

        public Deck findDeckByName(String name) {
            for (Deck deck : decks) {
                if (deck.equalNames(name))
                    return deck;
            }

            return null;
        }

        public ArrayList<Deck> getDecks() {
            return decks;
        }

        public int getBalance() {
            return balance;
        }

        public Deck getActiveDeck() {
            return activeDeck;
        }

        public static ArrayList<User> getAllUsers() {
            return allUsers;
        }

        public void changePassword(String newPassword) {
            this.password = newPassword;
            Print.print(String.format(SuccessMessages.changingSuccessfully, "password"));
        }

        public void changeNickname(String newNickname) {
            this.nickName = newNickname;
            Print.print(String.format(SuccessMessages.changingSuccessfully, "nickname"));
        }

        public void increaseBalance(int increasingAmount) {
            this.balance += increasingAmount;
        }

        public void decreaseBalance(int decreasingAmount) {
            this.balance -= decreasingAmount;
        }

        public void addPreCardToTreasury(PreCard preCard) {
            if (preCard == null) return;
            if (this.cardTreasury.containsKey(preCard.getName())) {
                cardTreasury.put(preCard.getName(), cardTreasury.get(preCard.getName()) + 1);
            } else {
                this.cardTreasury.put(preCard.getName(), 1);
            }
//            this.preCards.add(preCard); //TODO what is this for?
        }

    public void increaseScore(int increasingAmount) {
        this.score += increasingAmount;
    }

    public boolean isPasswordWrong(String password) {
        return !password.equals(this.password);
    }

    public void setActiveDeck(Deck deck) {
        if (this.decks.contains(deck)) {    //TODO what if an error happens?
            this.activeDeck = deck;
        }
    }

    @Override
    public int compareTo(model.User otherUser) {
            if (this.score > otherUser.score) return -1;
            if (this.score < otherUser.score) return 1;
            return this.username.compareTo(otherUser.username);
        }

        public void addDeck(Deck deck) {
            decks.add(deck);
        }

        public void removeDeck(Deck deck) {
            decks.remove(deck);
            Print.print(SuccessMessages.removeDeck);
        }

        public void printMyCards() {
            ArrayList<String> myCards = new ArrayList<>(getCardTreasury().keySet());
            Collections.sort(myCards);

            for (String cardName : myCards) {
                Print.print(Objects.requireNonNull(PreCard.findCard(cardName)).toString());
            }
        }
    }
