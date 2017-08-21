/**
 * Created by colinbiafore on 8/20/17.
 */


import org.junit.Assert;
import org.junit.Test;

public class GameTest {

    @Test
    public void shipOutOfBoundsTest() {

        Board b1 = new Board();
        Assert.assertEquals(true,b1.createShip(10,"A","0","LEFT"));
        Assert.assertEquals(false,b1.createShip(11,"B","0","LEFT"));
        Assert.assertEquals(true,b1.createShip(5,"C","5","LEFT"));
        Assert.assertEquals(false,b1.createShip(6,"J","6","LEFT"));
    }

    @Test
    public void shipCollisionTest() {

        Board b1 = new Board();
        Assert.assertEquals(true,b1.createShip(5,"A","0","LEFT"));
        Assert.assertEquals(false,b1.createShip(5,"A","2","DOWN"));
        Assert.assertEquals(true,b1.createShip(5,"B","2","DOWN"));
        Assert.assertEquals(false,b1.createShip(3,"D","1","LEFT"));
    }

    @Test
    public void gameTest1() {
        Board b = new Board();

        Assert.assertEquals(true,b.createShip(4,"A","0","LEFT"));
        Assert.assertEquals(true,b.createShip(3,"C","0","LEFT"));
        Assert.assertEquals(true,b.createShip(2,"E","0","LEFT"));

        b.printShips();

        Assert.assertEquals("Miss",b.attack("E","3"));
        Assert.assertEquals("Hit",b.attack("A","0"));
        Assert.assertEquals("Already Taken",b.attack("A","0"));
        Assert.assertEquals("Hit",b.attack("A","1"));
        Assert.assertEquals("Hit",b.attack("A","2"));
        Assert.assertEquals("Sunk",b.attack("A","3"));
        Assert.assertEquals("Hit",b.attack("C","0"));
        Assert.assertEquals("Hit",b.attack("C","1"));
        Assert.assertEquals("Sunk",b.attack("C","2"));
        Assert.assertEquals("Hit",b.attack("E","0"));
        Assert.assertEquals("Win",b.attack("E","1"));

        b.prettyPrint();
    }



}
