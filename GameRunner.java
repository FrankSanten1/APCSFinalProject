import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

class GameRunner {
    public static void main(String[] args) throws CloneNotSupportedException{

        Board thingy = new Board(12, 8);
        PlayerStatsTracker stats = new PlayerStatsTracker(10, 10, 5, 5, null);
        thingy.addEntityToSpace(new Swordsman(), 4, 6);
        thingy.addEntityToSpace(new Player(stats), 1, 1);
        thingy.printBoard();
        ArrayList<Board> theList = ((Enemy) thingy.getSpace(4, 6).getEntity()).moveStage(thingy);
        for (Board x : theList) {
            x.printBoard();
        }
        
        //throw new YouAreActualGarbageException("ur trash on godddddddddd");
        /* 
        Board thingy = new Board(12, 8);
        Scanner sc = new Scanner(System.in);
        Board thingier = thingy.clone();
        thingy.addEntityToSpace(new Player(10, 10), 2, 1);
        
        thingy.printBoard();
        thingier.printBoard();
        Board thingiest = thingy.clone();
        thingy.getSpace(1, 1).getEntity().setHealth(5);
        System.out.println(thingy.getSpace(2, 1).getEntity().getHealth());
        System.out.println(thingiest.getSpace(2, 1).getEntity().getHealth());
        */
        /* 
        while (true) {
            System.out.print("\033[H\033[2J");  
            System.out.flush(); 
            thingy.printBoard();
            
            Point temp = stringToVector(sc.next());
            if (thingy.canMovePlayer(temp.x, temp.y) == false) {
                System.out.println("you suck haha");
            } else {
                thingy.movePlayer(temp.x, temp.y);
            }
            
        }
         */
        //sc.close();
    }

    public static Point stringToVector(String str) {
        Point coords = new Point(); /* x, y */
        str = str.toUpperCase();
        if (str.equals("N")) {
            coords.setLocation(0, -1);
        } else if (str.equals("S")) {
            coords.setLocation(0, 1);
        } else if (str.equals("E")) {
            coords.setLocation(1, 0);
        } else if (str.equals("W")) {
            coords.setLocation(-1, 0);
        } else if (str.equals("NE")) {
            coords.setLocation(1, -1);
        } else if (str.equals("NW")) {
            coords.setLocation(-1, -1);
        } else if (str.equals("SE")) {
            coords.setLocation(1, 1);
        } else if (str.equals("SW")) {
            coords.setLocation(-1, 1);
        }
        return coords;
    }
}