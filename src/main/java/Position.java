/**
 * Created by colinbiafore on 8/21/17.
 */
public class Position {


    final int row;
    final int col;

    Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object anotherPosition) {
        if (!(anotherPosition instanceof Position)) return false;
        Position p = (Position) anotherPosition;
        return p.row == this.row && p.col == this.col;
    }

    @Override
    public String toString() {
        return Integer.toString(row) + "," + Integer.toString(col);
    }

    @Override
    public int hashCode() {
        return 0;
    }





}
