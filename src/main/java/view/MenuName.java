package view;

public enum MenuName {
    LOGIN,
    SHOP,
    PROFILE,
    DECK,
    SCOREBOARD,
    DUEL,
    MAIN;

//    String name;

//    public static MenuName searchForMenuName(String name) {
//        for (MenuName menuName : MenuName.values()) {
//            if (menuName.toString().equals(name.toUpperCase()))
//                return menuName;
//        }
//
//        return null;
//    }

//    MenuName(String name) {
//        this.name = name;
//    }


    public String stringMenu() {
        switch (this) {
            case DECK:
                return "Deck Menu";
            case DUEL:
                return "Duel Menu";
            case MAIN:
                return "Main Menu";
            case SHOP:
                return "Shop Menu";
            case LOGIN:
                return "Login Menu";
            case PROFILE:
                return "Profile Menu";
            case SCOREBOARD:
                return "Scoreboard Menu";
            default:
                return "menu is not here";
        }
    }

}
