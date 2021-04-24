package model;

import java.util.ArrayList;

public class User implements Comparable<User>{
    private String username;
    private String password;
    private String nickName;
    private int score;
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks;
    private int balance;
    private Deck activeDeck;
    private static ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    public User(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.score = 0;
        this.cards = new ArrayList<>();
        this.decks = new ArrayList<>();
        this.balance = 0;
        allUsers.add(this);
    }

    public static User getUserByName(String username){
        for (User user : allUsers) {
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }

    public static String showScoreBoard(){
        StringBuilder scoreBoard = new StringBuilder();
        allUsers.sort(User::compareTo);
        int counter = 0;
        User previousUser = null;
        for (User user : allUsers) {
            if(previousUser!= null && user.score != previousUser.score) counter++;
            scoreBoard.append(counter).append(". ").append(user.username).append("\n");
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

    public ArrayList<Card> getCards() {
        return cards;
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

    public void increaseScore(int increasingAmount) {
        this.score += increasingAmount;
    }

    public boolean isPasswordCorrect(String password){
        return password.equals(this.password);
    }

    @Override
    public int compareTo(User otherUser) {
        if(this.score > otherUser.score) return -1;
        if(this.score < otherUser.score) return 1;
        return this.username.compareTo(otherUser.username);
    }
}
