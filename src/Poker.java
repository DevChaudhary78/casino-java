import java.util.*;

/**
 * For the game of Poker, I have made some slight modifications to the original
 * game of Poker for the sake of simplicity.
 * E.g., I have not included checking 2, 3 or 4 same cards in larger hands
 * <p>
 * Both the player & dealer will have 2 cards initially
 * Player can decide weather to draw more cards or to reveal cards
 * Maximum number of cards are 5
 * If the player decided to reveal at any moment, both the dealer's cards & Player's cards will be revealed,
 * and the one with the highest priority wins the game.
 * <p>
 * PRIORITY (increasing to decreasing):
 * 1. Same Rank Cards ("A", "A", "A")
 * 2. Royal Flush ("the A of spade", "the K of spade," "the Q of spade")
 * 3. Flush ("the 7 of Heart", "the 8 of Heart", "the 9 of Heart")
 * 4. Straight ("6", "7", "8")
 * 5. Same suit ("the 3 of Heart", "the 9 of Heart")
 * 6. High Card ("K" > "9")
 *
 * @author Dev Chaudhary, 000885797
 */
public class Poker {
    /** Cards drawn for dealer **/
    private final List<Card> dealerCards;
    /** Cards drawn for player **/
    private final List<Card> playerCards;

    private final Deck deck;

    /** Initially picks 2 cards for both player and dealer **/
    Poker() {
        deck = new Deck();

        dealerCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        boolean flag = false;
        // initially picking two cards
        for(int i = 0; i < 4; i++) {
            Card card = this.draw();
            if(flag) {
                dealerCards.add(card);
            } else {
                playerCards.add(card);
            }

            flag = !flag;
        }
    }

    /**
     * Checks weather the cards of a hand are all the same or not
     * e.g., AAA, QQQ, 222, 55555
     * @param cards hands to check
     * @return true if it's the same kind or false if not
     */
    private boolean isOfKind(List<Card> cards) {
        boolean answer = true;

        Card firstCard = cards.get(0);

        for(Card card: cards) {
            if(card != firstCard) {
                answer = false;
                break;
            }
        }

        return answer;
    }

    /**
     * e.g. 123, 45678, AKQJ10, etc.
     * @param cards hands to check
     * @return ture if it's a straight hand
     */
    private boolean isStraight(List<Card> cards) {
        boolean answer = true;

        cards.sort(Comparator.comparingInt(Card::getValue));

        Card firstCard = cards.get(0);

        for(int i = 1; i < cards.size(); i++) {
            if(cards.get(i).getValue() != firstCard.getValue()+i){
                answer = false;
                break;
            }
        }

        // condition where one of the cards is "A" and the rest of them are 2, 3, ...
        if(cards.get(cards.size()-1).getValue() == 11 && cards.get(0).getValue() == 2) return true;

        return answer;
    }

    /**
     * Flush is a hand where all cards for a straight of the same suit
     * @param cards hands to check
     * @return true if the hand is flush or else false
     */
    private boolean isFlush(List<Card> cards) {
        boolean isStraight = isStraight(cards);

        if(!isStraight) return false;

        else {
            Card firstCard = cards.get(0);

            boolean answer = true;

            for(Card card: cards) {
                if(!card.getSuit().equalsIgnoreCase(firstCard.getSuit())) {
                    answer = false;
                    break;
                }
            }

            return answer;
        }
    }

    /**
     * Royal Flush is Flush on Steroids, e.g. AKQJ10 (only this (:)
     * @param cards
     * @return
     */
    private boolean isRoyalFlush(List<Card> cards) {
        boolean isFlush = isFlush(cards);

        return isFlush && cards.get(cards.size() - 1).getValue() == 11;
    }

    /**
     * Checks the card if it got the same suit (e.g., Heart of 2 & Heart of 9)
     * @param cards
     * @return true if consists of same suit else false
     */
    private boolean sameSuit(List<Card> cards) {
        boolean answer = true;

        Card firstCard = cards.get(0);

        for(Card card: cards) {
            if(!card.getSuit().equals(firstCard.getSuit())) {
                answer = false;
                break;
            }
        }

        return answer;
    }

    /**
     * Just calculates the total hand value of all cards
     * @param cards hand of cards
     * @return the total value
     */
    private int handValue(List<Card> cards) {
        int value = 0;

        for(Card card: cards) {
            if(card.getSuit().equalsIgnoreCase("j")) value += 11;
            else if(card.getSuit().equalsIgnoreCase("q")) value += 12;
            else if(card.getSuit().equalsIgnoreCase("k")) value += 13;
            else if(card.getSuit().equalsIgnoreCase("a")) value += 14;
            else value += card.getValue();
        }

        return value;
    }

    /**
     * When user decides to pick in place of reveal, we pick one card each for both dealer and player
     * @return the drawn card for just player (not dealer)
     */
    public Card pick() {
        Card playerCard = this.draw();
        Card dealerCard = this.draw();

        dealerCards.add(dealerCard);
        playerCards.add(playerCard);

        return playerCard;
    }

    /**
     * Resets the whole game of poker
     */
    public void reset() {
        this.playerCards.clear();
        this.dealerCards.clear();
    }

    /**
     * Checks who is the winner of the game, based on the priority of the hand
     * @return the winner's name
     */
    public String winCheck() {
        int dealerPriority;
        int playerPriority;

        // dealer
        if(isOfKind(dealerCards)) dealerPriority = 5;
        else if(isRoyalFlush(dealerCards)) dealerPriority = 4;
        else if(isFlush(dealerCards)) dealerPriority = 3;
        else if(isStraight(dealerCards)) dealerPriority = 2;
        else if(sameSuit(dealerCards)) dealerPriority = 1;
        else dealerPriority = 0;

        // player
        if(isOfKind(playerCards)) playerPriority = 5;
        else if(isRoyalFlush(playerCards)) playerPriority = 4;
        else if(isFlush(playerCards)) playerPriority = 3;
        else if(isStraight(playerCards)) playerPriority = 2;
        else if(sameSuit(playerCards)) playerPriority = 1;
        else playerPriority = 0;

        if(playerPriority > dealerPriority) return "Player";
        else if(dealerPriority > playerPriority) return "Dealer";
        else {
            if(handValue(playerCards) > handValue(dealerCards)) return "Player";
            else if(handValue(dealerCards) > handValue(playerCards)) return "Dealer";
            else return "Draw";
        }
    }

    /**
     * draws card from a deck
     * @return random drawn card
     */
    private Card draw() {
        return deck.draw(false);
    }

    /**
     * Getter for dealerCards (used when reveal)
     * @return dealerCards
     */
    public List<Card> getDealerCards() {
       return dealerCards;
    }

    /**
     * Getter for playerCards
     * @return playerCards
     */
    public List<Card> getPlayerCards() {
        return playerCards;
    }
}
