import java.util.ArrayList;
import java.awt.Point;

public class Spear extends Ability{
    public ArrayList<Board> doThing(String input, Board currentSituation, PlayerStatsTracker stats) throws CloneNotSupportedException{
        input = input.replaceAll(" ", ""); //remove all spaces just in case
        input = input.toUpperCase(); //makes it all uppercase just in case

        ArrayList<Point> pointsToAttack = new ArrayList<Point>();
        Point playerCoords = currentSituation.getPlayerCoords();

        if (input.equals("N")) {
            pointsToAttack.add(new Point(playerCoords.x, playerCoords.y - 1));
            pointsToAttack.add(new Point(playerCoords.x, playerCoords.y - 2));
        } else if (input.equals("E")) {
            pointsToAttack.add(new Point(playerCoords.x + 1, playerCoords.y));
            pointsToAttack.add(new Point(playerCoords.x + 2, playerCoords.y));
        } else if (input.equals("S")) {
            pointsToAttack.add(new Point(playerCoords.x, playerCoords.y + 1));
            pointsToAttack.add(new Point(playerCoords.x, playerCoords.y + 2));
        } else if (input.equals("W")) {
            pointsToAttack.add(new Point(playerCoords.x - 1, playerCoords.y));
            pointsToAttack.add(new Point(playerCoords.x- 2, playerCoords.y));
        } else if (input.equals("NE")) {
            pointsToAttack.add(new Point(playerCoords.x + 1, playerCoords.y - 1));
            pointsToAttack.add(new Point(playerCoords.x + 2, playerCoords.y - 2));
        } else if (input.equals("NW")) {
            pointsToAttack.add(new Point(playerCoords.x - 1, playerCoords.y - 1));
            pointsToAttack.add(new Point(playerCoords.x - 2, playerCoords.y - 2));
        } else if (input.equals("SE")) {
            pointsToAttack.add(new Point(playerCoords.x + 1, playerCoords.y + 1));
            pointsToAttack.add(new Point(playerCoords.x + 2, playerCoords.y + 2));
        } else if (input.equals("SW")) {
            pointsToAttack.add(new Point(playerCoords.x - 1, playerCoords.y + 1));
            pointsToAttack.add(new Point(playerCoords.x - 2, playerCoords.y + 2));
        } else {
            throw new AbilityInputDoesNotParseException("hmm...bruh");
        }

        ArrayList<Board> animations = new ArrayList<Board>();

        for (int i = 0; i < pointsToAttack.size(); i++) { //remove the points that go off board
            try {
                currentSituation.getSpace(pointsToAttack.get(i)); //try to access the point
            }  
            catch (ArrayIndexOutOfBoundsException e) { //if it fails:
                pointsToAttack.remove(i); //take it off the list
            }
        }

        Board temp = currentSituation.clone();
        for (Point p : pointsToAttack) {
            temp.getSpace(p).setEntity(new CosmeticX());
        }
        animations.add(temp);
        
        for (Point p : pointsToAttack) {
            currentSituation.getSpace(p).damageEntityHere(5);
        }
        currentSituation.cleanDeadEntities();
        animations.add(currentSituation.clone());

        return animations;
    }

    public String name() {
        return "Spear";
    }

    public String howToUse() {
        return "Type in the cardinal or ordinal direction you wish to attack. \nFor example, \"NE\", meaning northeast, if you wish to attack up and right, or \"S\", meaning south, if you wish to attack downward.";
    }

    public String wrongUseMessage() {
        return "That is not one of the eight cardinal or ordinal direction. Try again with something like \"W\" for west, or \"SE\" for southeast.";
    }

    public String description() {
        return "This weapon deals five damage to two spaces in any straight or diagonal line from the player.";
    }
}
