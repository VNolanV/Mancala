# Project Title

Text Based Mancala Game

## Description

This java program is coded with object orientation in mind and is meant to create
a text based version of the board game known as Mancala.

## AI Solution Review

## Getting Started

The TextUI file is the main file and is where the program is run from. 
The other classes simply provide the objects and the various methods that are needed
to interact with the objects and create the game experience.

Constructors:

public class Board {
    private ArrayList<Pit> pits;
    private ArrayList<Store> stores;
    private boolean whoseTurn;
    private boolean repeatTurn;
}

public class MancalaGame {
    private Board board;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Scanner scan = new Scanner(System.in);
    private boolean redo = false;
}

public class Pit {
    private int stoneCount;
}

public class Store {
    private int totalStones;
    private Player owner;
}

public class Player {
    private String name;
    private Store store;
    private int iD;
}

### Dependencies

* Default Java Libraries
* Junit Assertion Libraries 

### Executing program

* How to build and run the program
* Step-by-step bullets

run the gradle file with gradle build
use gradle echo to find the command
to run from class files

To run the program from class files:
java -cp build/classes/java/main ui.TextUI

* include the expected output

Upon running the program, expect a full version of the Mancala board game to be presented

## Limitations

N/A  

## Author Information

Name: Nolan Van
Email: nvan@uoguelph.ca
Student ID: 1228849

## Development History

NOTE:   The AI Solution and the solution I created were created independently of
        each other with no cross references.

10/21/2023:
     - Pushed the intial file structure that would be needed.

10/23/2023:
    - Fixed the file strcuture
    - Created and inserted skeleton classes for board, mancala game, pit, player and store classes of initial AI solution.
    - Inserted updated skeleton classes for board, mancala game, pit, player and store classes of initial AI solution.

10/24/2023
    - Inserted AI created exception files

10/26/2023
    - Pushed compliable but not fully functional version of mancala created by the AI
    - Updated the appropriate markdown files

10/29/2023
    - Pushed human created mancala game that is not fully functional and not perfected
    - Pushed updated version of human created mancala game
    - Pushed further updated version of human created mancala game
    - Pushed update to all files to ensure correctness

10/31/2023
    - Pushed finalized assignment with the AI solution portion being re-done as earlier version was none functional.

## Acknowledgments

N/A