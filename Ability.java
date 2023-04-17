import java.util.ArrayList;

public class Ability {
    //this class won't really have meaningful objects
    //it's mostly just so that I can have an arrayList of subclasses of this
    //so that I can call do() on each one of them and each does something different

    //This is the method that will be overridden for each weapon
    public ArrayList<Board> doThing(Board currentSituation, PlayerStatsTracker stats) {
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
}
