package ui;

import mancala.MancalaGame;
import mancala.PitNotFoundException;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;

import java.util.Scanner;

public class TextUI {
    public static void main(String[] args) throws PitNotFoundException, GameNotOverException {
        System.out.println("\n---Hello, Welcome To Mancala---\n");

        MancalaGame currentGame = new MancalaGame();
        System.out.println("\n---Game Starting---\n");

        Scanner scanner = new Scanner(System.in);

        currentGame.startNewGame();

        while (!currentGame.isGameOver()) {
            System.out.println(currentGame);

            // Ask the current player for their move
            System.out.println("Player " + currentGame.getCurrentPlayer().getName()
                    + ", enter the pit number to move: ");
            int pitNum = scanner.nextInt();

            try {
                currentGame.setRedo(false);
                int stones = currentGame.move(pitNum);
                System.out.println("\nStones Left On Your Side: " + stones + "\n");
            } catch (InvalidMoveException e) {
                System.out.println("Invalid move. Please try again.");
                currentGame.setRedo(true);
            }
            // Switch to the next player's turn
            if (!currentGame.getRedo()) {
                currentGame.setCurrentPlayer(currentGame.getCurrentPlayer() == currentGame.getPlayers().get(0)
                        ? currentGame.getPlayers().get(1)
                        : currentGame.getPlayers().get(0));
            }
            System.out.println("\n\n");
        }

        currentGame.lastGrab();
        System.out.println(currentGame);

        // Game is over, determine the winner
        try {
            currentGame.printWinner(currentGame.getWinner());
        } catch (GameNotOverException e) {
            System.out.println("The game is not over yet.");
        }

        scanner.close();
    }
}
