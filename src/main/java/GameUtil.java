/**
 * Created by colinbiafore on 8/20/17.
 * Utility methods that could be used throughout the Battleship application
 */
public class GameUtil {

    // Singleton
    private GameUtil() {}

    public static int[] strToCoord(String r, String c) {

        int[] coord = new int[2];
        int row = 0;

        for(int i = 0; i < r.length(); i++) {
            int power = r.length() - i - 1;
            row += (Math.pow(26,power) * (r.charAt(i) - 64));
        }

        int col = Integer.parseInt(c);
        coord[0] = row - 1;
        coord[1] = col;

        return coord;
    }

    public static String coordToStr(int n) {

        n++;
        StringBuilder sb = new StringBuilder();

        while(n > 0){
            n--;
            char ch = (char) (n % 26 + 'A');
            n /= 26;
            sb.append(ch);
        }

        sb.reverse();
        return sb.toString();
    }

}
