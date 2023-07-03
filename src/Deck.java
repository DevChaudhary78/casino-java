import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Deck abstracts the direct complexity to draw and pick Cards and creating decks manually
 * @author Dev Chaudhary, 000885797
 */
public class Deck {
    /** All four suits in the card games **/
    String[] suits = new String[]{"Club", "Heart", "Spade", "Diamond"};
    /** All four ranks in the card games **/
    String[] ranks = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    /** 4 times the deck of 52 cards **/
    Card[] cards = new Card[208];

    /** Initializes the Deck **/
    Deck() {
        int i = 0;
        for(int j = 0; j < 4; j++) {
            for (String suit : suits) {
                for (String rank : ranks) {
                    Card card = new Card(suit, rank);
                    cards[i] = card;
                    i++;
                }
            }
        }
    }

    /** * Basically draw is picking a random card from a deck of a card * it means if the card has not yet been played,
     * then only we can draw it *
     * @param isSimulated this boolean value will tell us weather the draw is a "real" or a "simulated" draw.
     *                    simulated draw is used by monte carlo algorithm.
     * @return a random-drawn card which has not been played yet
     */
    public Card draw(boolean isSimulated) {
        List<Card> availableCards = new ArrayList<>();
        for(Card card: cards) {
            if(!card.isPlayed() && (!isSimulated || !card.isSimulated())) {
                availableCards.add(card);
            }
        }

        if(availableCards.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(availableCards.size());
        Card drawnCard = availableCards.get(index);

        if(isSimulated) {
            drawnCard.setSimulated(true);
        } else {
            drawnCard.setPlayed(true);
        }

        return drawnCard;
    }

    /**
     * Resets the whole deck to play any game again (sets isPlayed to false for every card)
     */
    public void shuffle() {
        for(Card card: cards) {
            card.setPlayed(false);
        }
    }

    /**
     * Same as shuffle but for simulation
     */
    public void resetSimulation() {
        for(Card card: cards) {
            card.setSimulated(false);
        }
    }
}
