package mancala;

import java.util.ArrayList;
import java.util.Scanner;

public class MancalaGame {
    private Board board;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Scanner scan = new Scanner(System.in);
    private boolean redo = false;

    public MancalaGame() {
        setBoard(new Board());
        players = new ArrayList<>();

        setPlayers(new Player(), new Player());
        board.registerPlayers(players.get(0), players.get(1));
        currentPlayer = players.get(0);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getRedo() {
        return redo;
    }

    public void setRedo(boolean pop) {
        redo = pop;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException {
        if (pitNum < 1 || pitNum > 12) {
            throw new PitNotFoundException();
        }

        return board.getNumStones(pitNum);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getStoreCount(Player player) throws NoSuchPlayerException {
        if (player != players.get(0) || player != players.get(1)) {
            throw new NoSuchPlayerException();
        }

        return player.getStoreCount();
    }

    public Player getWinner() throws GameNotOverException, PitNotFoundException {
        if (!isGameOver()) {
            throw new GameNotOverException();
        }

        if (board.getStores().get(0).getTotalStones() > board.getStores().get(1).getTotalStones()) {
            return players.get(0);
        } else if (board.getStores().get(0).getTotalStones() < board.getStores().get(1).getTotalStones()) {
            return players.get(1);
        } else {
            return null;
        }
    }

    public void lastGrab() {
        if (currentPlayer == players.get(0)) {
            for (int i = 0; i < 6; i++) {
                board.getStores().get(0).addStones(board.getPits().get(i).getStoneCount());
            }
        } else if (currentPlayer == players.get(1)) {
            for (int i = 6; i < 12; i++) {
                board.getStores().get(1).addStones(board.getPits().get(i).getStoneCount());
            }
        }
        board.setUpPits();
    }

    public void printWinner(Player winner) {
        if (winner != null) {
            System.out.println("Player " + winner.getName() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public boolean isGameOver() throws PitNotFoundException {
        return board.isSideEmpty(1) || board.isSideEmpty(7);
    }

    public int move(int startPit) throws InvalidMoveException, PitNotFoundException, IllegalStateException {
        ArrayList<Pit> pits = board.getPits();
        int total = 0;

        if ((startPit < 1 || startPit > 12)
                || (currentPlayer.getId() == 1 && (startPit < 1 || startPit > 6))
                || (currentPlayer.getId() == 2 && (startPit < 7 || startPit > 12))
                || pits.get(startPit - 1).getStoneCount() == 0) {
            redo = true;
            throw new InvalidMoveException();
        }

        if (currentPlayer == players.get(0) || currentPlayer == players.get(1)) {
            redo = false;
        } else {
            redo = true;
            throw new IllegalStateException();
        }

        redo = false;

        int pointsScored = board.moveStones(startPit, currentPlayer);
        System.out.println("\nPoints Scored: " + pointsScored + "\n");

        if (board.getRepeatTurn()) {
            redo = true;
            board.setRepeatTurn(false);
        }

        if (currentPlayer == players.get(0)) {
            for (int i = 0; i < 6; i++) {
                total += pits.get(i).getStoneCount();
            }
        } else if (currentPlayer == players.get(1)) {

            for (int i = 6; i < 12; i++) {
                total += pits.get(i).getStoneCount();
            }
        }

        return total;
    }

    public void setBoard(Board theBoard) {
        board = theBoard;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) {
        players.clear();

        System.out.println("\nEnter Name For Player 1(Pits: 1-6):\n");
        onePlayer.setName(scan.nextLine());
        onePlayer.setId(1);
        players.add(onePlayer);

        System.out.println("\nEnter Name For Player 2(Pits: 7-12):\n");
        twoPlayer.setName(scan.nextLine());
        twoPlayer.setId(2);
        players.add(twoPlayer);
    }

    public void startNewGame() {

        board.initializeBoard();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mancala Game:\n");
        sb.append("Current Player: ").append(currentPlayer != null
                ? currentPlayer.getName()
                : "None").append("\n");
        sb.append(board.toString());

        return sb.toString();
    }
}
