/**
 * Black Jack is a game where both dealer and player pick card one by one,
 * The person who scores more than 21 first gets busted and another player wins
 * The person who scores exactly more than 21 first wins
 * @author Dev Chaudhary, 000885797
 */
public class BlackJack {
    /** Player's score **/
    private int myScore;
    /** Dealer's Score **/
    private int dealerScore;
    /** To check the weather, it's a player turn or dealer's turn **/
    private boolean myTurn;
    /** Number of wins in a simulation run for player **/
    private int sim_myWins;
    /** Number of wins in a simulation run for dealer **/
    private int sim_dealerWins;
    /** Simulations where nobody won **/
    private int sim_notDone;
    /** constant max points to win (pass this you will lose) **/
    private int winPoints = 21;

    private final Deck deck;

    BlackJack() {
        this.myScore = 0;
        this.dealerScore = 0;
        this.myTurn = true;

        deck = new Deck();
    }

    /**
     * resets the whole game
     */
    public void reset() {
        this.myScore = 0;
        this.dealerScore = 0;
        this.myTurn = true;
        deck.shuffle();
        deck.resetSimulation();
    }

    /**
     * by default, uses the real draw
     * @return the card which is being drawn
     */
    public Card draw() {
        Card card = deck.draw(false);
        if(card != null) {
            int value = card.getValue();
            if(myTurn) {
                myScore += value;
            } else {
                dealerScore += value;
            }

            myTurn = !myTurn;
        }
        return card;
    }

    /**
     * overloaded draw method which can help user to decide weather to select simulated draw or real draw
     * @param isSimulated decided weather to get a simulated or real draw
     * @return the card which is being drawn
     */
    public Card draw(boolean isSimulated) {
        Card card = deck.draw(isSimulated);
        if(card != null) {
            int value = card.getValue();
            if(this.isMyTurn()) {
                myScore += value;
            } else {
                dealerScore += value;
            }

            myTurn = !myTurn;
        }
        return card;
    }

    /**
     * checks the condition according to Black Jack's game rule and returns the winner
     * @return the winner
     */
    public String winCheck() {
        if(myScore == winPoints && dealerScore == winPoints) return "Dealer";
        else if(myScore > winPoints) return "Dealer";
        else if(myScore == winPoints) return "Player";
        else if(dealerScore == winPoints) return "Dealer";
        else if(dealerScore > winPoints) return "Player";
        else return "notDone";
    }

    /**
     * Monte Carlo is basically a brute force method to find the probability of winning a particular game.
     * In this scenario,
     * We are finding the probability
     * to win a game by providing number of games to play and number of draws per play
     * @param n the number of games to run
     * @param draws the number of draws to simulate
     * This method won't return the winning probability,
     *              but will modify variables, which will be used by View class i.e., Casino
     */
    public void runMonteCarlo(int n, int draws) {
        this.clearSimulation();

        int tempScore = this.myScore;
        int tempDealerScore = this.dealerScore;
        boolean tempTurn = this.myTurn;

        for(int i = 0; i < n; i++) {
            this.reset();

            for(int j = 0; j < draws; j++) {
                this.draw(true);

                if(!winCheck().equalsIgnoreCase("notDone")) {
                    break;
                }
            }

            if(this.winCheck().equalsIgnoreCase("Player")) {
                this.sim_myWins++;
            } else if(this.winCheck().equalsIgnoreCase("Dealer")) {
                this.sim_dealerWins++;
            } else {
                this.sim_notDone++;
            }
        }

        myScore=tempScore;
        dealerScore=tempDealerScore;
        myTurn=tempTurn;
    }


    /**
     * clears the simulation wins for all 3 possibilities
     */
    private void clearSimulation() {
        this.sim_myWins = 0;
        this.sim_dealerWins = 0;
        this.sim_notDone = 0;

        for(Card card: deck.cards) {
            card.setSimulated(false);
        }
    }

    /** Getters & Setters for myScore, dealerScore, myTurn, sim_dealerWins, sim_notDone, sim_myWins **/
    public int getMyScore() {
        return this.myScore;
    }

    public int getDealerScore() {
        return this.dealerScore;
    }

    public boolean isMyTurn() {
        return this.myTurn;
    }

    public int getSim_dealerWins() {
        return this.sim_dealerWins;
    }

    public int getSim_MyWins() {
        return this.sim_myWins;
    }

    public int getSim_notDone() {
        return this.sim_notDone;
    }
}
