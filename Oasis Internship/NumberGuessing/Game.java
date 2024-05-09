package Project.NumberGuessing;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private int generateNumber, userGuess, noOfGuess = 0;
    private int limitOfGuess = 1;
    private static final int MAX_round = 3;

    public void generateNumber() {
        Random random = new Random();
        generateNumber = random.nextInt(100);
        System.out.println("Number Generated." + generateNumber);
    }

    public void userInput() {
        System.out.println("Enter Your Guessing Number(0 - 100):");
        System.out.println("Remaining attempted: " + (5 - limitOfGuess));
        Scanner sc = new Scanner(System.in);
        userGuess = sc.nextInt();
    }

    boolean checkNumber() {
        noOfGuess++;
        int score = (6 - noOfGuess) * 10;
        if (userGuess == generateNumber) {
            System.out.println("***Congratulation Your guess is correct.***\nYour Score=" + score);
            return true;
            // System.exit(0);
        } else if (userGuess < generateNumber) {
            System.out.println("Your guessed number is less than actual number");
        } else if (userGuess > generateNumber) {
            System.out.println("Your guessed number is greater than actual number");
        }
        return false;

    }

    void playGame() {
        Scanner sc = new Scanner(System.in);
        int round = 1;
        boolean playAgain = true;
        while (playAgain) {
            if (round <= MAX_round) {
                generateNumber();

                boolean b = false;
                while (!b && limitOfGuess < 6) {
                    System.out.println("Round: " + round);

                    userInput();
                    b = checkNumber();
                    limitOfGuess++;
                    if (limitOfGuess == 5) {
                        System.out.println("Do you want to see the answer?(y/n):");
                        String ch2 = sc.next();
                        boolean answer = ch2.equalsIgnoreCase("y");
                        if (answer) {
                            System.out.println("Number Generated." + generateNumber);
                        }
                    }

                }
                if (!b) {
                    System.out.println("You exceed your no. of attempts");

                }

                round++;
            } else {
                System.out.println("You can't move more round.\n Exit from Program.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
                System.exit(0);
            }
            System.out.println("Do you want to play again?(y/n):");
            String ch3 = sc.next();
            sc.close();
            playAgain = ch3.equalsIgnoreCase("y");
            limitOfGuess = 1;
            noOfGuess = 0;
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }// main
}// class
