import java.util.Scanner;

public class Casino {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BlackJack blackJack = new BlackJack();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Draw a card");
            System.out.println("2. Run Monte Carlo simulation");
            System.out.println("3. Reset the game");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> {
                    Card drawnCard = blackJack.draw();
                    if (drawnCard != null) {
                        System.out.println("Card drawn: " + drawnCard);
                    } else {
                        System.out.println("No more cards to draw.");
                    }
                    System.out.println("Player score: " + blackJack.getMyScore());
                    System.out.println("Dealer score: " + blackJack.getDealerScore());
                    String winResult = blackJack.winCheck();
                    if (!winResult.equals("notDone")) {
                        System.out.println("Game over. " + winResult + " wins!");
                        blackJack.reset();
                    }
                }
                case 2 -> {
                    System.out.print("Enter the number of simulations to run: ");
                    int numSimulations = scanner.nextInt();
                    System.out.print("Enter the number of draws per simulation: ");
                    int numDraws = scanner.nextInt();
                    blackJack.runMonteCarlo(numSimulations, numDraws);
                    int simMyWins = blackJack.getSim_MyWins();
                    int simDealerWins = blackJack.getSim_dealerWins();
                    int simNotDone = blackJack.getSim_notDone();
                    double winPercentage = (double) simMyWins / numSimulations * 100;
                    double losePercentage = (double) simDealerWins / numSimulations * 100;
                    double notDonePercentage = (double) simNotDone / numSimulations * 100;
                    System.out.println("Simulation Results:");
                    System.out.print("\nPlayer wins: " + simMyWins + " (" + Math.round(winPercentage) + "%): ");
                    for(int i = 0; i < Math.round(winPercentage); i++) {
                        System.out.print("*");
                    }
                    System.out.print("\nDealer wins: " + simDealerWins + " (" + Math.round(losePercentage) + "%): ");
                    for(int i = 0; i < Math.round(losePercentage); i++) {
                        System.out.print("*");
                    }
                    System.out.print("\nNot finished: " + simNotDone + " (" + Math.round(notDonePercentage) + "%): ");
                    for(int i = 0; i < Math.round(notDonePercentage); i++) {
                        System.out.print("*");
                    }
                    System.out.println("\n");
                }
                case 3 -> {
                    blackJack.reset();
                    System.out.println("Game reset.");
                }
                case 4 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }
}



