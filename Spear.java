import java.util.ArrayList;
import java.awt.Point;

public class Spear extends Ability{
    //damaging ability

    //deals 5 damage to 2 spaces in any direction from the player
    public ArrayList<Board> doThing(String input, Board currentSituation, PlayerStatsTracker stats) throws CloneNotSupportedException{
        //first, clean up the user inputs so that something doesn't break if the caps are wrong or if spaces are included
        input = input.replaceAll(" ", ""); //remove all spaces just in case
        input = input.toUpperCase(); //makes it all uppercase just in case

        ArrayList<Point> pointsToAttack = new ArrayList<Point>(); //arrayList to hold the two points that will be attacked
        Point playerCoords = currentSituation.getPlayerCoords(); //of course, get the player's coords

        //find the points to attack based off the input
        if (input.equals("N")) { //if they said north, add the points above to pointsToAttack
            pointsToAttack.add(new Point(playerCoords.x, playerCoords.y - 1));
            pointsToAttack.add(new Point(playerCoords.x, playerCoords.y - 2));
        } else if (input.equals("E")) { //if they said east, add the points to the right to pointsToAttack
            pointsToAttack.add(new Point(playerCoords.x + 1, playerCoords.y));
            pointsToAttack.add(new Point(playerCoords.x + 2, playerCoords.y));
        } else if (input.equals("S")) { //etc, etc for every compass orientation
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
        } else { //if it didn't match with any other compass orientation: 
            throw new AbilityInputDoesNotParseException("hmm...bruh"); //throw an error that Main will handle telling it that the input was garbage
        }

        ArrayList<Board> animations = new ArrayList<Board>(); //initialize the arrayList that will store the animation frames

        for (int i = 0; i < pointsToAttack.size(); i++) { //remove the points that go off board
            try {
                currentSituation.getSpace(pointsToAttack.get(i)); //try to access the point
            }  
            catch (ArrayIndexOutOfBoundsException e) { //if it fails:
                pointsToAttack.remove(i); //take it off the list
            }
        }

        Board temp = currentSituation.clone(); //board that will get cosmetic Xs on it
        for (Point p : pointsToAttack) { //loop through points to attack
            temp.getSpace(p).setEntity(new CosmeticX()); //set Xs onto temp where attacks will be
        }
        animations.add(temp); //add temp as a frame of animation
        
        for (Point p : pointsToAttack) { //loop through points again
            currentSituation.getSpace(p).damageEntityHere(5); //damage them on the main array
        }
        currentSituation.cleanDeadEntities(); //clean up any enemy corpses, remove
        animations.add(currentSituation.clone()); //add as an animation frame

        return animations; //return of course
    }

    //returns what the ability is called
    public String name() {
        return "Spear";
    }

    //returns a description of what to input when using
    public String howToUse() {
        return "Type in the cardinal or ordinal direction you wish to attack. \nFor example, \"NE\", meaning northeast, if you wish to attack up and right, or \"S\", meaning south, if you wish to attack downward.";
    }

    //if you goof up an input, this is called in Main
    public String wrongUseMessage() {
        return "That is not one of the eight cardinal or ordinal direction. Try again with something like \"W\" for west, or \"SE\" for southeast.";
    }

    //a simple description of the weapon
    public String description() {
        return "This weapon deals five damage to two spaces in any straight or diagonal line from the player.";
    }
}
