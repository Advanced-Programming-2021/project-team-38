package model.card.spelltrap;

public enum CardStatus {
    UNLIMITED(3),
    LIMITED(1);

    int limit;

    CardStatus(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
