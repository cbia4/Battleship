
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
    private Position[] shipPosition;
    private Set<Position> positionsLeft;

    public Ship(int id, Position[] shipPosition) {
        this.id = id;
        this.shipPosition = shipPosition;
        this.shipSize = this.shipPosition.length;
        this.positionsLeft = new HashSet<>();
        this.positionsLeft.addAll(Arrays.asList(this.shipPosition));
    }

    public void printPosition() {
        System.out.print(String.format("%3s",this.id));
        System.out.println(" -> " + Arrays.toString(this.shipPosition));
    }

    // determine whether the ship is hit or sunk
    public String hit(Position position) {
        positionsLeft.remove(position);
        if (positionsLeft.size() < 1) return "Sunk";
        return "Hit";
    }

    // return a copy of the ship's position array
    public Position[] getPosition() {
        Position[] positionCopy = new Position[this.shipSize];
        System.arraycopy(this.shipPosition,0,positionCopy,0, this.shipSize);
        return positionCopy;
    }
}