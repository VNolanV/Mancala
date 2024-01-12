package mancala;

public class NoSuchPlayerException extends Exception{
    public NoSuchPlayerException() {
        super("No such player.");
    }
}

