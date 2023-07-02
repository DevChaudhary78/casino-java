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

        // condition where one of the cards is "A" and the rest of them are 2, 3, ...
        if(cards.get(cards.size()-1).getValue() == 11 && cards.get(0).getValue() == 2) return true;

        return answer;
    }

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

    private boolean isRoyalFlush(List<Card> cards) {
        boolean isFlush = isFlush(cards);

        return isFlush && cards.get(cards.size() - 1).getValue() == 11;
    }

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

    public int handValue(List<Card> cards) {
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
