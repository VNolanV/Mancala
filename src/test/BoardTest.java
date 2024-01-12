/***************
 * This test serves the sole purpose of verifying the existence of methods in the class.
 * It does not validate the types of expected return values, but it does confirm 
 * the presence of 
 * a return type if expected.
 * Please note that this form of testing is not suitable for comprehensive unit testing.
 * It has been designed solely as a tool to identify missing methods within your implementation.
 */

package mancala;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
    private Board board = new Board();
    private ArrayList<Pit> pits;
    private ArrayList<Store> stores;
    private boolean whoseTurn;
    private boolean repeatTurn;

    private Player one = new Player();
    private Player two = new Player();
    private Board boards = new Board();

    // What to do before each test case
    @BeforeEach
    void newStuff() {
        board.setUpPits();
        board.setUpStores();
        board.initializeBoard();
    }

    // Test cases for getNumStones();
    @Test
    void throwsExceptionGetNumStones() {
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(91));
    }

    @Test
    void getNumStonesPitsWithStones() {
        board.setUpPits(); // Use the 'board' instance that was initially set up
        board.setUpStores();
        board.initializeBoard();

        board.getPits().get(4).addStone(); // Add stones to the 'board' instance

        assertAll(
                () -> assertEquals(4, board.getNumStones(3)), // Use 'board' instance here
                () -> assertEquals(5, board.getNumStones(5)) // Use 'board' instance here
        );
    }

    @Test
    void getNumStonesNoStones() {

        board.setUpPits();

        assertAll(
                () -> assertEquals(0, board.getNumStones(3)),
                () -> assertEquals(0, board.getNumStones(4)));
    }

    // Test cases for resetBoard();

    @Test
    void resettingBoard() {

        board.resetBoard();
        assertAll(
                () -> assertEquals(4, board.getPits().get(0).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(1).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(2).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(3).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(4).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(5).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(6).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(7).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(8).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(9).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(10).getStoneCount()),
                () -> assertEquals(4, board.getPits().get(11).getStoneCount()));

    }

    // Test cases for registerPlayers();

    @Test
    void storesAssignedAtLeast() {

        board.registerPlayers(one, two);

        assertAll(
                () -> assertNotNull(one.getStore()),
                () -> assertNotNull(two.getStore()));

    }

    @Test
    void storesAssignedAtMost() {

        board.registerPlayers(one, two);

        assertAll(
                () -> assertEquals(one.getStore(), board.getStores().get(0)),
                () -> assertEquals(two.getStore(), board.getStores().get(1)));

    }

    // Test cases for isSideEmpty();

    @Test
    void allPitsPlayerOneEmpty() {
        board.setUpPits();
        board.getPits().get(6).addStone();
        board.getPits().get(7).addStone();
        board.getPits().get(8).addStone();
        board.getPits().get(9).addStone();
        board.getPits().get(10).addStone();
        board.getPits().get(11).addStone();

        try {
            assertTrue(board.isSideEmpty(1));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }
    }

    @Test
    void allPitsPlayerTwoEmpty() {
        board.setUpPits();
        board.getPits().get(1).addStone();
        board.getPits().get(2).addStone();
        board.getPits().get(3).addStone();
        board.getPits().get(4).addStone();
        board.getPits().get(5).addStone();
        board.getPits().get(0).addStone();

        try {
            assertTrue(board.isSideEmpty(7));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }
    }

    @Test
    void notAllPitsPlayerOneEmpty() {
        board.setUpPits();
        board.getPits().get(1).addStone();

        try {
            assertFalse(board.isSideEmpty(2));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }
    }

    @Test
    void throwsExceptionIsSideEmpty() {
        assertThrows(PitNotFoundException.class, () -> board.isSideEmpty(24));
    }

    // Test cases for captureStones();

    @Test
    void capturingStonesFromOppositePit() {
        board.getPits().get(5).removeStones();
        board.getPits().get(5).addStone();
        whoseTurn = true;
        try {
            assertEquals(4, board.captureStones(5));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }
    }

    @Test
    void capturingStonesFromOppositeDifferentPit() {
        board.getPits().get(4).removeStones();
        board.getPits().get(4).addStone();
        board.getPits().get(7).addStone();
        whoseTurn = true;
        try {
            assertEquals(5, board.captureStones(4));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }

    }

    @Test
    void capturingStonesFromOppositeEmptyPit() {
        board.getPits().get(4).removeStones();
        board.getPits().get(4).addStone();
        board.getPits().get(7).removeStones();
        whoseTurn = true;
        try {
            assertEquals(0, board.captureStones(4));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }

    }

    @Test
    void capturingStonesFromOppositeStartingPitEmpty() {
        board.getPits().get(4).removeStones();
        whoseTurn = true;
        try {
            assertEquals(0, board.captureStones(4));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }

    }

    @Test
    void capturingStonesthrowsException() {
        assertThrows(PitNotFoundException.class, () -> board.captureStones(35));
    }

    // Test cases for distributingStones();

    @Test
    void distributeStonesRepeatTurn() {
        board.registerPlayers(one, two);
        board.getPits().get(5).removeStones();
        board.getPits().get(5).addStone();
        whoseTurn = true;

        try {
            board.distributeStones(6);
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }

        assertTrue(board.getRepeatTurn());

    }

    @Test
    void distributeStonesThrowsException() {
        assertThrows(PitNotFoundException.class, () -> board.distributeStones(15));
    }

    @Test
    void distributeStonesFromEmptyPit() {
        board.getPits().get(4).removeStones();

        try {
            assertEquals(0, board.distributeStones(5));
        } catch (PitNotFoundException err) {
            System.out.println("Pit Not Found");
        }
    }

    @Test distributeStonesCapturingStones(){
        
        board.getPits().get(5).removedStones();
        assertEquals(5, board.distributeStones(1));

        
    }

    @Test
    void testBoardMethodsExist() {
        Method[] methods = Board.class.getDeclaredMethods();

        // Assertions for void methods
        assertAll(
                () -> assertTrue(hasVoidMethod(methods, "resetBoard"), "resetBoard method is missing"),
                () -> assertTrue(hasVoidMethod(methods, "initializeBoard"), "initializeBoard method is missing"),
                () -> assertTrue(hasVoidMethod(methods, "setUpPits"),
                        "setUpPits method is missing"),
                () -> assertTrue(hasVoidMethod(methods, "setUpStores"), "setUpStores method is missing"),
                () -> assertTrue(hasVoidMethod(methods, "registerPlayers",
                        Player.class, Player.class), "registerPlayers method is missing"));

        // Assertions for methods with non-void return types
        assertAll(
                () -> assertTrue(hasMethod(methods, "moveStones", int.class, Player.class),
                        "moveStones method is missing"),
                () -> assertTrue(hasMethod(methods, "distributeStones", int.class),
                        "distributeStones method is missing"),
                () -> assertTrue(hasMethod(methods, "captureStones", int.class), "captureStones method is missing"),
                () -> assertTrue(hasMethod(methods, "getPits"), "getPits method is missing"),
                () -> assertTrue(hasMethod(methods, "getStores"), "getStores method is missing"),
                () -> assertTrue(hasMethod(methods, "isSideEmpty", int.class), "isSideEmpty method is missing"),
                () -> assertTrue(hasMethod(methods, "getNumStones", int.class), "getNumStones method is missing"),
                () -> assertTrue(hasMethod(methods, "toString"), "toString method is missing"));
    }

    private boolean hasVoidMethod(Method[] methods, String methodName, Class<?>... parameterTypes) {
        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getReturnType() == void.class
                    && parameterTypesMatch(method.getParameterTypes(), parameterTypes)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMethod(Method[] methods, String methodName, Class<?>... parameterTypes) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)
                    && method.getReturnType() != void.class
                    && parameterTypesMatch(method.getParameterTypes(), parameterTypes)) {
                return true;
            }
        }
        return false;
    }

    private boolean parameterTypesMatch(Class<?>[] parameterTypes, Class<?>... expectedTypes) {
        if (parameterTypes.length != expectedTypes.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].equals(expectedTypes[i])) {
                return false;
            }
        }
        return true;
    }
}
