import java.util.*;

public class Poker {
    private final List<Card> dealerCards;
    private final List<Card> playerCards;

    private final Deck deck;

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

        return answer;
    }

    private boolean isFlush(List<Card> cards) {
        boolean isStraight = isStraight(cards);

        if(!isStraight) return false;

        else {
            Card firstCard = cards.get(0);

            boolean answer = true;

            for(Card card: cards) {
                if(!Objects.equals(card.getSuit(), firstCard.getSuit())) {
                    answer = false;
                    break;
                }
            }

            return answer;
        }
    }

    private boolean isRoyalFlush(List<Card> cards) {
        boolean isFlush = isFlush(cards);

        return isFlush && cards.get(cards.size() - 1).getValue() == 11;
    }

    public int handValue(List<Card> cards) {
        int value = 0;

        for(Card card: cards) {
            value += card.getValue();
        }

        return value;
    }

    public Card pick() {
        Card playerCard = this.draw();
        Card dealerCard = this.draw();

        dealerCards.add(dealerCard);
        playerCards.add(playerCard);

        return playerCard;
    }

    public void reset() {
        this.playerCards.clear();
        this.dealerCards.clear();
    }

    public String winCheck() {
        boolean sameSequence = (isOfKind(playerCards) && isOfKind(dealerCards)) || (isStraight(playerCards) && isStraight(dealerCards)) || (isFlush(playerCards) && isFlush(dealerCards));
        if(sameSequence) {
            if(handValue(playerCards) > handValue(dealerCards)) return "Player";
            else return "Dealer";
        }
        else if(isOfKind(dealerCards)) return "Dealer";
        else if(isRoyalFlush(dealerCards) && !isOfKind(playerCards)) return "Dealer";
        else if(isFlush(dealerCards) && !(isOfKind(playerCards) || isRoyalFlush(playerCards))) return "Dealer";
        else if(isStraight(dealerCards) && !(isOfKind(playerCards) || isFlush(playerCards) || isRoyalFlush(playerCards)))  return "Dealer";
        else if(handValue(dealerCards) > handValue(playerCards)) return "Dealer";
        else return "Player";
    }

    private Card draw() {
        return deck.draw(false);
    }

    public List<Card> getDealerCards() {
       return dealerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }
}
