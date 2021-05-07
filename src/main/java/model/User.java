
    package model;

import java.util.ArrayList;
import java.util.HashMap;

    public class User implements Comparable<model.User>{
        private final String username;
        private final String password;
        private final String nickName;
        private int score;
        private HashMap<String, Integer> cardTreasury;   //how many cards do we have of each type?
//        private ArrayList<Card> cards;
        private ArrayList<Deck> decks;
        private final int balance;
        private Deck activeDeck;
        private final static ArrayList<model.User> allUsers;

        static {
            allUsers = new ArrayList<>();
        }

        {
            cardTreasury = new HashMap<>();
        }

        public User(String username, String password, String nickName) {
            this.username = username;
            this.password = password;
            this.nickName = nickName;
            this.score = 0;
//            this.cards = new ArrayList<>();
            this.decks = new ArrayList<>();
            this.balance = 0;
            allUsers.add(this);
        }

        public static model.User getUserByName(String username){
            for (model.User user : allUsers) {
                if(user.username.equals(username)){
                    return user;
                }
            }
            return null;
        }

        public static String showScoreBoard(){
            StringBuilder scoreBoard = new StringBuilder();
            allUsers.sort(model.User::compareTo);
            int counter = 0;
            model.User previousUser = null;
            for (model.User user : allUsers) {
                if(previousUser!= null && user.score != previousUser.score) counter++;
                scoreBoard.append(counter).append("- ").append(user.username).append("\n");
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

        public void setActiveDeck(Deck activeDeck) {
            this.activeDeck = activeDeck;
        }

        public int getScore() {
            return score;
        }

//        public ArrayList<Card> getCards() {
//            return cards;
//        }

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

        public static ArrayList<model.User> getAllUsers() {
            return allUsers;
        }

        public void increaseScore(int increasingAmount) {
            this.score += increasingAmount;
        }

        public boolean isPasswordCorrect(String password) {
            return password.equals(this.password);
        }
//
//        public void setActiveDeck(Deck deck) {
//            if (this.decks.contains(deck)) {
//                this.activeDeck = deck;
//            }
//        }
        //TODO negar and hasti conflict

        @Override
        public int compareTo(model.User otherUser) {
            if (this.score > otherUser.score) return -1;
            if (this.score < otherUser.score) return 1;
            return this.username.compareTo(otherUser.username);
        }

        public String removeDeck(Deck deck) {
            decks.remove(deck);
            return "deck deleted successfully";
        }
}
    }
