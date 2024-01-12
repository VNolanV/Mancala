package mancala;

import java.util.ArrayList;

public class Board {
    private ArrayList<Pit> pits;
    private ArrayList<Store> stores;
    private boolean whoseTurn;
    private boolean repeatTurn;

    public Board() {
        pits = new ArrayList<>();
        stores = new ArrayList<>();
        initializeBoard();
        repeatTurn = false;
    }

    public int captureStones(int stoppingPoint) throws PitNotFoundException {

        double holder = stoppingPoint;
        double difference = 0;
        double oppositePoint = 0;

        if (stoppingPoint < 0 || stoppingPoint > 11) {
            throw new PitNotFoundException();
        }

        if (holder > 5.5) {
            difference = holder - 5.5;
            oppositePoint = 5.5 - difference;
        } else if (holder < 5.5) {
            difference = 5.5 - holder;
            oppositePoint = 5.5 + difference;
        } else {
            oppositePoint = 0;
        }

        return pits.get((int) oppositePoint).removeStones();

    }

    public int distributeStones(int startingPoint) throws PitNotFoundException {

        int currentPos = startingPoint - 1;
        int numOfStones = pits.get(startingPoint - 1).removeStones();
        int numOfCapturedStones = 0;

        if (startingPoint < 1 || startingPoint > 12) {
            throw new PitNotFoundException();
        }

        while (numOfStones != 0) {

            if (currentPos == 5 && whoseTurn && numOfStones != 0) {
                stores.get(0).addStones(1);
                numOfStones--;

            } else if (currentPos == 11 && !whoseTurn && numOfStones != 0) {
                stores.get(1).addStones(1);
                numOfStones--;
                if (numOfStones == 0) {
                    repeatTurn = true;
                    return numOfStones;
                }
            }

            if (numOfStones == 0) {
                repeatTurn = true;
                return numOfStones;
            }

            currentPos++;
            if (currentPos == 12) {
                currentPos = 0;
            }

            pits.get(currentPos).addStone();
            numOfStones--;

        }

        if (whoseTurn && currentPos >= 0 && currentPos <= 5 && pits.get(currentPos).getStoneCount() == 1) {
            numOfCapturedStones = captureStones(currentPos) + pits.get(currentPos).removeStones();
            stores.get(0).addStones(numOfCapturedStones + pits.get(currentPos).removeStones());
        } else if (!whoseTurn && currentPos >= 6 && currentPos <= 11 && pits.get(currentPos).getStoneCount() == 1) {
            numOfCapturedStones = captureStones(currentPos) + pits.get(currentPos).removeStones();
            stores.get(1).addStones(numOfCapturedStones + pits.get(currentPos).removeStones());
        }
        return numOfCapturedStones + numOfCapturedStones;
    }

    public boolean getRepeatTurn() {
        return repeatTurn;
    }

    public void setRepeatTurn(boolean set) {
        repeatTurn = set;
    }

    public int moveStones(int startPit, Player player) throws PitNotFoundException, InvalidMoveException {

        if ((startPit < 1 || startPit > 6) && player.getId() == 1) {
            throw new InvalidMoveException();
        } else if ((startPit < 7 || startPit > 12) && player.getId() == 2) {
            throw new InvalidMoveException();
        }

        if (player.getId() == 1) {
            whoseTurn = true;
        } else if (player.getId() == 2) {
            whoseTurn = false;
        }

        int preMove = player.getStoreCount();
        distributeStones(startPit);
        int postMove = player.getStoreCount();

        return postMove - preMove;

    }

    public int getNumStones(int pitNum) throws PitNotFoundException {

        if (pitNum < 1 || pitNum > 12) {
            throw new PitNotFoundException();
        }

        if (pitNum - 1 > 0 && pitNum - 1 < pits.size()) {
            return pits.get(pitNum - 1).getStoneCount();
        } else {
            return -1;
        }

    }

    public ArrayList<Pit> getPits() {
        return pits;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void initializeBoard() {
        // Initialize the board by distributing stones.
        setUpPits();
        setUpStores();
        for (int i = 0; i < 12; i++) {
            for (int a = 0; a < 4; a++) {
                pits.get(i).addStone();
            }
        }
    }

    public boolean isSideEmpty(int pitNum) throws PitNotFoundException {

        if (pitNum < 1 || pitNum > 12) {
            throw new PitNotFoundException();
        }

        if (pitNum - 1 >= 0 && pitNum - 1 <= 5) {
            for (int i = 0; i < 6; i++) {
                if (pits.get(i).getStoneCount() != 0) {
                    return false;
                }
            }
        } else if (pitNum - 1 >= 6 && pitNum - 1 <= 11) {
            for (int i = 6; i < 12; i++) {
                if (pits.get(i).getStoneCount() != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void registerPlayers(Player one, Player two) {
        one.setStore(stores.get(0));
        two.setStore(stores.get(1));

        stores.get(0).setOwner(one);
        stores.get(1).setOwner(two);

    }

    public void resetBoard() {

        initializeBoard();
    }

    public void setUpPits() {
        pits.clear();
        for (int i = pits.size(); i < 12; i++) {
            pits.add(new Pit());
        }
        for (int i = 0; i < 12; i++) {
            pits.get(i).removeStones();
        }
    }

    public void setUpStores() {
        stores.clear();
        for (int i = stores.size(); i < 2; i++) {
            stores.add(new Store());
        }
        for (int i = 0; i < 2; i++) {
            stores.get(i).emptyStore();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board:\n\n");
        int a = 12;

        sb.append("Player 2's Store: ").append(stores.get(1).getTotalStones()).append(" stones\n\n");

        for (int i = 0; i < 6; i++) {
            sb.append("Pit ").append(i + 1).append(" [").append(pits.get(i).getStoneCount()).append("]");
            sb.append("   [").append(pits.get(a - 1).getStoneCount()).append("] Pit ").append(a).append("\n");
            a--;
        }
        sb.append("\n");
        sb.append("Player 1's Store ").append(stores.get(0).getTotalStones()).append(" stones\n");
        return sb.toString();
    }

}
