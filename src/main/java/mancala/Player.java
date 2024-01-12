package mancala;

public class Player {
    private String name;
    private Store store;
    private int iD;

    public Player(){
        name = "Player";
        store = new Store();
        iD = -1;
    }

    public Player(String nameP){
        name = nameP;
    }

    public String getName(){
        return name;
    }

    public Store getStore(){
        return store;
    }

    public int getId(){
        return iD;
    }

    public int getStoreCount(){
        return store.getTotalStones();
    }

    public void setName(String nameP){
        name = nameP;
    }

    public void setStore(Store storeP){
        store = storeP;
    }

    public void setId(int iDP){
        iD = iDP;
    }

    @Override
    public String toString() {
        return "Player: " + name + "\nStore: " + store.toString();
    }
}

