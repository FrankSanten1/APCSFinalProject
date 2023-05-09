import java.util.ArrayList;

public class Ability {
    //this class won't really have meaningful objects
    //it's mostly just so that I can have an arrayList of subclasses of this
    //so that I can call do() on each one of them and each does something different

    //This is the method that will be overridden for each weapon
    public ArrayList<Board> doThing(String input, Board currentSituation, PlayerStatsTracker stats) throws CloneNotSupportedException{
        //currentSituation will be whatever the board currently is
        //it also takes in the PlayerStatsTracker so that it can reduce mana/increase life/other things

        //How it will go for most weapons:
        ArrayList<Board> animations = new ArrayList<Board>(); //create an arrayList of boards, holding all stages of the animation

        //this part would usually be longer, playing out every step of the animation and making copies of the board and adding them into the arrayList
        //however, since this is the superclass that doesn't do anything in particular, it just returns the same board it was given
        animations.add(currentSituation);

        //hand back whatever it produced, last frame of animations will be used for continuing play
        return animations;
    }

    //this method just returns the name of the ability it belongs to
    //for example, in the Spear class, this is overridden to return "Spear"
    public String name() {
        return "";
    }

    //this method will be called whenever the user selects an ability to use, and should be told what to input to determine how it's used. 
    //ex. for spear: it's "Type in the cardinal or ordinal direction you wish to attack. \nFor example, "NE", meaning northeast, if you wish to attack up and right, or "S", meaning south, if you wish to attack downward."
    public String howToUse() {
        return "";
    }

    //this method will be called whenever the user inputs something for where to aim the ability that is not a valid input
    //ex. if you inputted "nne" for spear:
    //returns "That is not one of the eight cardinal or ordinal direction. Try again with something like "W" for west, or "SE" for southeast."
    public String wrongUseMessage() {
        return "";
    }

    //this method will return a description of what the ability does. 
    //for example, for spear: "This weapon deals five damage to two spaces in any straight or diagonal line from the player. "
    public String description() {
        return "";
    }

    //some abilities cost mana
    //if so, they override this method to show how much mana they cost
    public int getManaUsage() {
        return 0;
    }
}
