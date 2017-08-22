
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

    private final int BOARD_SIZE;
    private final String LEFT = "LEFT";
    private final String DOWN = "DOWN";
    private char[][] board;
    private int numShips;
    private Map<Position,Ship> shipPositions;
    private List<Ship> ships;

    public Board(int size) {

        this.BOARD_SIZE = size;
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
    public boolean createShip(int length, Position p, String direction) {

        // check for invalid ship parameters
        if (!(direction.equalsIgnoreCase(LEFT) || direction.equalsIgnoreCase(DOWN))) return false;
        if (p.row < 0 || p.col < 0
                || (p.row + length > BOARD_SIZE && direction.equalsIgnoreCase(DOWN))
                || (p.col + length > BOARD_SIZE && direction.equalsIgnoreCase(LEFT))) return false;


        // determine the ships position from input parameters
        Position[] shipPosition = generateShipPositions(p,length,direction);
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
        for(Position position : ship.getPosition()) {
            this.shipPositions.put(position,ship);
        }
    }

    // (createShip helper) determine the ships position from the input parameters
    private Position[] generateShipPositions(Position p, int len, String dir) {
        Position[] shipPosition = new Position[len];
        int row = p.row;
        int col = p.col;
        for(int i = 0; i < len; i++) {
            shipPosition[i] = new Position(row,col);
            if (this.shipPositions.containsKey(shipPosition[i])) return null; // return null if the coordinate is already taken by another ship
            if (dir.equalsIgnoreCase(LEFT)) col++;
            else row++;
        }
        return shipPosition;
    }


    // public attack method, row and col are converted to a Position and passed to the private attack method
    public String attack(String row, String col) {
        int[] coords = GameUtil.strToCoord(row,col);
        return attack(new Position(coords[0],coords[1]));
    }

    // attack the board at the Position specified
    private String attack(Position p) {

        String msg;

        // determine the attack result
        if (p.row < 0 || p.col < 0 || p.row >= BOARD_SIZE || p.col >= BOARD_SIZE) msg = "Invalid Position";
        else if(this.board[p.row][p.col] != 'X') msg = "Already Taken";
        else if(shipPositions.containsKey(p)) msg = hitResult(p);
        else {
            board[p.row][p.col] = 'M';
            msg = "Miss";
        }

        return msg;
    }

    // decide whether a hit resulted in a simple "Hit", an entire ship "Sunk", or an endgame (i.e. "Win")
    private String hitResult(Position p) {
        this.board[p.row][p.col] = 'H';
        String msg = this.shipPositions.get(p).hit(p);
        if (msg.equals("Hit")) return msg;
        this.numShips--; // if msg != Hit, ship was "Sunk"
        if (this.numShips < 1) msg = "Win"; // if the last ship was sunk, "Win"
        return msg;
    }

    public int getNumShips() { return this.numShips; }
    public List<Ship> getShips() { return this.ships; }

    public void printShips() {
        System.out.println("–––––––––––––– SHIP POSITIONS –––––––––––––––");
        System.out.println();
        for(Ship s : this.ships) s.printPosition();
        System.out.println();
    }

    public void prettyPrint() {
        System.out.println("––––––––––––– BATTLESHIP BOARD ––––––––––––––");
        System.out.print("   ");
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(String.format("%4s",i));
        }
        System.out.println("");
        for(int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(String.format("%3s",GameUtil.coordToStr(i)));
            for(int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(String.format("%4s",board[i][j]));
            }
            System.out.println("");
        }
        System.out.println("–––––––––––––––––––––––––––––––––––––––––––––");
        System.out.println();
    }
}