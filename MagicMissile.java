import java.util.ArrayList;
import java.awt.Point;

public class MagicMissile extends Ability {
    //this is the magic missile, one of the starter weapons
    
    //the magic missile gets a point and attacks it for 8 damage, using 1 mana
    //this does that, and returns an ArrayList of animations for it doing that
    public ArrayList<Board> doThing(String input, Board currentSituation, PlayerStatsTracker stats) throws CloneNotSupportedException{
        input = input.replaceAll(" ", ""); //removes all spaces from the input string, makes it easier to deal with
        
        //the kind of input this wants is 1,2 or 5,3
        int index = input.indexOf(","); //find the comma to separate the two numbers
        if (index == -1) { //if there is no comma:
            throw new AbilityInputDoesNotParseException("denied"); //throw an error at Main and it will ask the user to try again
            //Main has try-catches for these kinds of errors, don't worry, this is meant to happen if the player puts something in that they shouldn't
        }
        //if the code makes it here, there is a comma

        Point coords; //make a point to store the coords in
        //for some reason i need to initialize it out here instead of in the try-catch
        //something about local variables, idk. 
        try {
            coords = new Point(Integer.parseInt(input.substring(0, index)), Integer.parseInt(input.substring(index + 1))); //atempt to parse the strings before and after the comma as integers
        } catch (NumberFormatException e) { //if this fails
            throw new AbilityInputDoesNotParseException("parse bad"); //complain to Main
        }
        //if it makes it this far, that means that the coords inputted are indeed integers
        try { //run an experiment
            currentSituation.getSpace(coords); //is this point a valid point on the board?
        } catch (Exception e) { //if it's not: 
            throw new AbilityInputDoesNotParseException("point no good"); //then it's probably a point that's offscreen, tell main to deal with the issue
        }
        //if it makes it this far, hooray! it is a valid input, and we can move on with our lives!

        stats.shiftMana(-1 * getManaUsage()); //take the hit of mana cost
        ArrayList<Board> animations = new ArrayList<Board>(); //initialize the animations arrayList
        Board temp = currentSituation.clone(); //a temporary board, to be filled with cosmetic Xs
        temp.getSpace(coords).setEntity(new CosmeticX(0, 0, 255)); //set the coordinate you are attacking on temp to a blue X
        animations.add(temp); //add it as a frame of animation
        currentSituation.getSpace(coords).damageEntityHere(8); //now, in the real board, damage that square for 8
        currentSituation.cleanDeadEntities(); //make sure any corpses of enemies are dealt with
        animations.add(currentSituation.clone()); //add the results as a frame of animation

        return animations; //ofc return the final animations. 
    }

    //returns what the ability is called
    public String name() {
        return "Magic Missile";
    }

    //returns a description of what to input to make it work
    public String howToUse() {
        return "Type in the coordinates where you wish to attack. For example, the top-left corner would be \"1,1\", the point on the fourth column and the third row would be \"4,3\", etc";
    }

    //returns a message calling you silly for putting in an incorrect input
    public String wrongUseMessage() {
        return "You must type in a coordinate, with numbers separated by a comma. For example: \"3, 5\", or \"2, 1\". The coordinate must fall somewhere on the battlefield.";
    }

    //returns a brief description of what the ability does
    public String description() {
        return "This weapon deals eight damage to one square anywhere. Costs 1 mana to use. ";
    }

    //this uses 1 mana when used, so make note
    public int getManaUsage() {
        return 1;
    }
}
