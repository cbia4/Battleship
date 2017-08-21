import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by colinbiafore on 8/20/17.
 * Board has the ability to create ships (if the bounds are correct)
 * and will execute an attack resulting in an updated state of the board
 */

public class Board {

    private final int BOARD_SIZE = 10;
    private char[][] board;
    private int numShips;
    private Map<String,Ship> shipPositions;
    private List<Ship> ships;

    public Board() {

        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        this.numShips = 0;
        this.shipPositions = new HashMap<>();
        this.ships = new ArrayList<>();

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 'X';
            }
        }
    }

    // checks validity of ship position and adds a new ship if no conflicts exist
    // ship must be placed within the board bounds with a valid direction given (LEFT, DOWN)
    // ship must not collide with another ship (no overlapping ships/coordinates)
    public boolean createShip(int length, String rowStart, String colStart, String direction) {

        // translate alphanumeric coordinates
        int[] coords = GameUtil.strToCoord(rowStart,colStart);
        int row = coords[0];
        int col = coords[1];

        // check for invalid ship parameters
        if (row < 0 || col < 0 || row + length > BOARD_SIZE || col + length > BOARD_SIZE) return false;
        if (!(direction.equalsIgnoreCase("LEFT") || direction.equalsIgnoreCase("DOWN"))) return false;

        // determine the ships position from input parameters
        String[] shipPosition = generateShipPositions(row,col,length,direction);
        if (shipPosition == null) return false;

        // create a new ship and add to board
        Ship ship = new Ship(this.numShips,shipPosition);
        this.ships.add(ship);
        addShipPositions(ship);
        return true;
    }

    // (createShip helper) add new ship positions to the shipPositions map
    private void addShipPositions(Ship ship) {
        this.numShips++;
        for(String position : ship.getPosition()) {
            this.shipPositions.put(position,ship);
        }
    }

    // (createShip helper) determine the ships position from the input parameters
    private String[] generateShipPositions(int row, int col, int len, String dir) {
        String[] shipPosition = new String[len];
        for(int i = 0; i < len; i++) {
            shipPosition[i] = GameUtil.coordToStr(row) + Integer.toString(col);
            if (this.shipPositions.containsKey(shipPosition[i])) return null; // return null if the coordinate is already taken by another ship
            if (dir.equalsIgnoreCase("LEFT")) col++;
            else row++;
        }
        return shipPosition;
    }

    // attack the board at the given row (r) and column (c)
    public String attack(String r, String c) {

        int[] coord = GameUtil.strToCoord(r,c);
        int row = coord[0];
        int col = coord[1];

        String msg;
        String position = r + c; // create the symbol representing a board coordinate

        // determine the attack result
        if (row < 0 || col < 0 || row >= BOARD_SIZE || col >= BOARD_SIZE) msg = "Invalid Position";
        else if(this.board[row][col] != 'X') msg = "Already Taken";
        else if(shipPositions.containsKey(position)) msg = hitResult(row,col,position);
        else {
            board[row][col] = 'M';
            msg = "Miss";
        }

        return msg;
    }

    // decide whether a hit resulted in a simple "Hit", an entire ship "Sunk", or an endgame (i.e. "Win")
    private String hitResult(int row, int col, String position) {
        this.board[row][col] = 'H';
        String msg = this.shipPositions.get(position).hit(position);
        if (msg.equals("Hit")) return msg;
        this.numShips--; // if msg != Hit, ship was "Sunk"
        if (this.numShips < 1) msg = "Win"; // if the last ship was sunk, "Win"
        return msg;
    }

    public void printShips() {
        for(Ship s : this.ships) s.printPosition();
    }

    public void prettyPrint() {
        System.out.print("   ");
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(GameUtil.coordToStr(i) + " ");
            for(int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}