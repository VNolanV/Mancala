## Nolan Van 
## Prompt (10/25/2023): 
create a full text-besed version of the Mancala board game using java

## Chat GPT
## Response (10/25/2023):

Creating a full text-based version of the Mancala board game in Java involves several components, 
including setting up the board, implementing the game rules, and providing a user interface for 
players to interact with the game. Here's a simplified text-based version of the Mancala game in Java:

import java.util.Arrays;
import java.util.Scanner;

public class MancalaGame {
    private int[] board;
    private int currentPlayer;
    private boolean gameOver;

    public MancalaGame() {
        board = new int[14];  // 0-5: Player 1's pits, 7-12: Player 2's pits, 6: Player 1's Mancala, 13: Player 2's Mancala
        Arrays.fill(board, 0, 14, 4); // Initialize all pits with 4 stones
        currentPlayer = 1;
        gameOver = false;
    }

    public void printBoard() {
        System.out.println("\nPlayer 2 (North)");
        System.out.println("  12 11 10  9  8  7");
        System.out.println("___________________");
        System.out.printf("| %2d %2d %2d %2d %2d %2d |", board[12], board[11], board[10], board[9], board[8], board[7]);
        System.out.println("\n|                   |");
        System.out.printf("| %2d %2d %2d %2d %2d %2d |", board[0], board[1], board[2], board[3], board[4], board[5]);
        System.out.println("\n___________________");
        System.out.println("  1  2  3  4  5  6");
        System.out.println("Player 1 (South)");
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            printBoard();
            int pitChoice = getPlayerInput(scanner);
            distributeStones(pitChoice);
            checkForEndGame();
            switchPlayer();
        }
        printResult();
        scanner.close();
    }

    private int getPlayerInput(Scanner scanner) {
        int maxPit = (currentPlayer == 1) ? 6 : 12;
        int pitChoice;
        while (true) {
            System.out.print("Player " + currentPlayer + ", choose a pit (1-" + maxPit + "): ");
            pitChoice = scanner.nextInt();
            if (pitChoice >= 1 && pitChoice <= maxPit && board[pitChoice - 1] > 0) {
                return pitChoice - 1;
            }
            System.out.println("Invalid pit choice. Try again.");
        }
    }

    private void distributeStones(int pitIndex) {
        int stones = board[pitIndex];
        board[pitIndex] = 0;
        int currentPit = pitIndex;

        while (stones > 0) {
            currentPit = (currentPit + 1) % 14; // Move to the next pit
            if (currentPlayer == 1 && currentPit == 13) {
                continue; // Skip player 2's Mancala
            } else if (currentPlayer == 2 && currentPit == 6) {
                continue; // Skip player 1's Mancala
            }
            board[currentPit]++;
            stones--;
        }

        int lastPit = currentPit;

        if (currentPlayer == 1 && currentPit < 6 && board[currentPit] == 1 && board[12 - currentPit] > 0) {
            board[6] += board[12 - currentPit] + 1;
            board[currentPit] = 0;
            board[12 - currentPit] = 0;
        } else if (currentPlayer == 2 && currentPit > 6 && currentPit < 13 && board[currentPit] == 1 && board[12 - currentPit] > 0) {
            board[13] += board[12 - currentPit] + 1;
            board[currentPit] = 0;
            board[12 - currentPit] = 0;
        }

        if (lastPit != 6 && lastPit != 13) {
            switchPlayer();
        }
    }

    private void checkForEndGame() {
        boolean player1Empty = Arrays.stream(Arrays.copyOfRange(board, 0, 6)).allMatch(x -> x == 0);
        boolean player2Empty = Arrays.stream(Arrays.copyOfRange(board, 7, 13)).allMatch(x -> x == 0);

        if (player1Empty || player2Empty) {
            // Collect remaining stones into the respective Mancalas
            for (int i = 0; i < 6; i++) {
                board[6] += board[i];
                board[i] = 0;
            }
            for (int i = 7; i < 13; i++) {
                board[13] += board[i];
                board[i] = 0;
            }
            gameOver = true;
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    private void printResult() {
        printBoard();
        int player1Score = board[6];
        int player2Score = board[13];
        if (player1Score == player2Score) {
            System.out.println("It's a tie! Both players have " + player1Score + " points.");
        } else {
            System.out.println("Player " + ((player1Score > player2Score) ? "1" : "2") + " wins with " + Math.max(player1Score, player2Score) + " points!");
        }
    }

    public static void main(String[] args) {
        MancalaGame game = new MancalaGame();
        game.play();
    }
}

This code provides a simple text-based interface for playing Mancala in the console. 
Players take turns selecting a pit, and the game continues until it's over. 
The winner is determined by comparing the Mancala scores. 
You can run this code in a Java development environment or IDE to play the game.