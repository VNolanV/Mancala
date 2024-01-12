package mancala;

public class Store {
    private int totalStones;
    private Player owner;

    public Store(){
        totalStones = 0;
        owner = null;
    }

    public void addStones(int amount){
        totalStones += amount;
    }

    public int emptyStore(){
        int stonesInStore = totalStones;
        totalStones = 0;
        return stonesInStore;
    }

    public Player getOwner(){
        return owner;
    }

    public int getTotalStones(){
        return totalStones;
    }

    public void setOwner(Player player){
        owner = player;
    }

    @Override
    public String toString() {
        return "Store owned by " + (owner != null ? owner.getName() : "No owner") + "\nTotal Stones: " + totalStones;
    }
}

