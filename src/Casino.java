import java.util.Scanner;

public class Casino {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;

        while (flag) {
            System.out.println("Which game you want to play?");
            System.out.println("\t1. Black Jack");
            System.out.println("\t2. Poker");
            System.out.println("\t3. Exit");
            System.out.print("Enter your choice: ");
            int gameChoice = scanner.nextInt();

            switch (gameChoice) {
                case 1 -> {
                    BlackJack blackJack = new BlackJack();
                    System.out.println("Welcome to Casino \uD83C\uDFB0: you're playing BlackJack! \uD83C\uDCCF");

                    boolean exit = false;
                    while (!exit) {
                        System.out.println("1. Draw a card");
                        System.out.println("2. Run Monte Carlo simulation");
                        System.out.println("3. Reset the game");
                        System.out.println("4. Exit");
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

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
                                    if(winResult.equalsIgnoreCase("player")) {
                                        System.out.println("\n" + ConsoleColors.GREEN + "Game over. " + winResult + " wins! \uD83D\uDE80" + ConsoleColors.RESET);
                                    } else {
                                        System.out.println("\n" + ConsoleColors.RED + "Game over. " + winResult + " wins! \uD83E\uDDBE" + ConsoleColors.RESET);
                                    }
                                    blackJack.reset();
                                }
                            }
                            case 2 -> {
                                System.out.println(ConsoleColors.YELLOW + "\nRunning Monte Carlo...\uD83C\uDFC3\u200D\n" + ConsoleColors.RESET);
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
                                System.out.print(ConsoleColors.GREEN + "\nPlayer wins: " + simMyWins + " (" + Math.round(winPercentage) + "%): ");
                                for(int i = 0; i < Math.round(winPercentage); i++) {
                                    System.out.print("*");
                                }
                                System.out.println(ConsoleColors.RESET);

                                System.out.print(ConsoleColors.RED + "\nDealer wins: " + simDealerWins + " (" + Math.round(losePercentage) + "%): ");
                                for(int i = 0; i < Math.round(losePercentage); i++) {
                                    System.out.print("*");
                                }
                                System.out.println(ConsoleColors.RESET);

                                System.out.print(ConsoleColors.YELLOW + "\nNot finished: " + simNotDone + " (" + Math.round(notDonePercentage) + "%): ");
                                for(int i = 0; i < Math.round(notDonePercentage); i++) {
                                    System.out.print("*");
                                }
                                System.out.println(ConsoleColors.RESET + "\n");
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
                }
                case 2 -> {
                    Poker poker = new Poker();
                    System.out.println("Welcome to poker! ");

                    System.out.println("Your draws: ");
                    System.out.println("\t" + poker.getPlayerCards().get(0));
                    System.out.println("\t" + poker.getPlayerCards().get(1));

                    boolean run = true;

                    while(run) {
                        System.out.println("What you want to do next? ");
                        System.out.println("\t1. Draw");
                        System.out.println("\t2. Reveal");
                        System.out.println("\t3. Reset");
                        System.out.println("\t4. Exit");
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();

                        switch (choice) {
                            case 1 -> {
                                if(poker.getPlayerCards().size() == 5) {
                                    System.out.println("Max Cards!");
                                } else {
                                    Card card = poker.pick();

                                    System.out.println("Your new card: " + card);

                                    System.out.println("Your cards: ");
                                    for(int i = 0; i < poker.getPlayerCards().size(); i++) {
                                        System.out.println("\t" + poker.getPlayerCards().get(i));
                                    }
                                }
                            }

                            case 2 -> {
                                System.out.println(ConsoleColors.GREEN + "\nThe winner is " + poker.winCheck() + "\n" + ConsoleColors.RESET);

                                System.out.println("Dealer's cards: ");
                                for(int i = 0; i < poker.getDealerCards().size(); i++) {
                                    System.out.println("\t" + poker.getDealerCards().get(i));
                                }

                                System.out.println("\nYour Cards: ");
                                for(int i = 0; i < poker.getPlayerCards().size(); i++){
                                    System.out.println("\t" + poker.getPlayerCards().get(i));
                                }

                                run = false;
                            }

                            case 3 -> poker.reset();

                            case 4 -> run = false;

                            default -> System.out.println("Invalid Choice!");
                        }
                    }

                }

                case 3 -> flag = false;

                default -> System.out.println("Invalid Choice!");
            }
        }

        scanner.close();
    }
}



