public class Card {
    private final String suit;
    private final String rank;
    private final int value;
    private boolean played = false;
    private boolean simulated = false;

    Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;

        if(this.rank.equals("J") || this.rank.equals("Q") || this.rank.equals("K")) {
            this.value = 10;
        } else if(this.rank.equals("A")) {
            this.value = 11;
        } else {
            this.value = Integer.parseInt(this.rank);
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isPlayed() {
        return played;
    }

    public boolean isSimulated() {
        return simulated;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String toString() {
        return "the " + this.suit + " of " + this.rank;
    }


    // for poker
    public String getSuit() {
        return this.suit;
    }
}

