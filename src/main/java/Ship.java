
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by colinbiafore on 8/20/17.
 * Ship class to hold state for ships on the game board
 *
 */

public class Ship {

    private int id;
    private int shipSize;
    private String[] shipPosition;
    private Set<String> positionsLeft;

    public Ship(int id, String[] shipPosition) {
        this.id = id;
        this.shipPosition = shipPosition;
        this.shipSize = this.shipPosition.length;
        this.positionsLeft = new HashSet<>();
        this.positionsLeft.addAll(Arrays.asList(this.shipPosition));
    }

    public void printPosition() {
        System.out.println(this.id + " -> " + Arrays.toString(this.shipPosition));
    }

    // determine whether the ship is hit or sunk
    public String hit(String position) {
        positionsLeft.remove(position);
        if (positionsLeft.size() < 1) return "Sunk";
        return "Hit";
    }

    // return a copy of the ship's position array
    public String[] getPosition() {
        String[] positionCopy = new String[this.shipSize];
        System.arraycopy(this.shipPosition,0,positionCopy,0, this.shipSize);
        return positionCopy;
    }
}