/**
 * Card, as the name implies is the blueprint Class for a card in a deck
 * it consists of suit & rank (which are base properties)
 * However here there are some extra properties like value, played & simulated, which will be discussed further.
 * @author Dev Chaudhary, 000885797
 */
public class Card {
    /** Suit of a particular Card **/
    private final String suit;
    /** Rank of a particular Card **/
    private final String rank;
    /** The value of the card indicates the weightage of the card,
     * meaning Ace, King, Queen & Jack holds the highest points,
     * while other cards hold points equivalent to their face value **/
    private final int value;
    /** used internally to decide weather the card is drawn from the deck or not by user **/
    private boolean played = false;
    /** same as played but used for Monte Carlo Simulation **/
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

    /**
     * getter for value
     * @return weightage of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * getter for played
     * @return weather the card is playable or not
     */
    public boolean isPlayed() {
        return played;
    }

    /**
     * getter for simulated
     * @return same as played but for Monte Carlo
     */
    public boolean isSimulated() {
        return simulated;
    }

    /**
     * Setter for simulated
     * @param simulated to set the simulated property
     */
    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }

    /**
     * Setter for played
     * @param played boolean value to set played property
     */
    public void setPlayed(boolean played) {
        this.played = played;
    }

    /**
     * e.g. "the Ace of 9"
     * @return the rank and the suit of the card
     */
    public String toString() {
        return "the " + this.suit + " of " + this.rank;
    }


    /**
     * getter for suit (only used in Poker.java)
     * @return suit of the card
     */
    // for poker
    public String getSuit() {
        return this.suit;
    }
}

