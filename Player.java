import java.awt.Point;

public class Player extends Entity{
    //this is the class for entities on the board that represent the player
    //the positioning of this guy determines many things, like what enemies damage you or how attacks work

    private PlayerStatsTracker stats; //playerStatsTracker keeps track of all the stats carried over from last battle
    private boolean inDanger; //this is true if the player is in a space that will be damaged next turn, this affects some things cosmetically

    //getter and setter for inDanger of course
    public boolean getInDanger() {return inDanger;}
    public void setInDanger(boolean inDanger) {this.inDanger = inDanger;}
    
    //constructor, just takes a stats object and uses it to fill in all statistics necesary
    public Player(PlayerStatsTracker stats) {
        super(stats.getMaxHealth(), stats.getHealth(), true);
        this.stats = stats;
    }

    //how it will look
    //usually looks like a tinted []
    public String toString() {
        if (getColorOverride() != null) { //if there's a color override: 
            return colorOverrideToAnsi() + "[]"; //make it that color
        }
        if (inDanger == false) { //if it's not in danger
            return "\033[38;2;0;255;255m" + "[]"; //teal color
        }
        //if it is in danger:
        return "\033[38;2;128;128;255m" + "[]"; //purple color
    }

    //override of the affectHealth method
    public void affectHealth(int amount) {
        super.affectHealth(amount); //does the same thing, but also...
        stats.setHealth(getHealth()); //affects the health of stats, so that the damage will carry over to the next player instance created
    }

    //same deal with this one as with the last one, override
    public void affectMaxHealth(int amount) {
        super.affectMaxHealth(amount); //still affects max health
        stats.setMaxHealth(getMaxHealth()); //also affects all future players' max healths
    }

    //override of move
    public Board move(Board b, Point direction) throws CloneNotSupportedException{
        //does everything that move in Entity does
        Point selfCoords = findSelfCoords(b);
        b = b.clone();
        b.removeEntityFromSpace(selfCoords.x, selfCoords.y);
        b.addEntityToSpace(this, selfCoords.x + direction.x, selfCoords.y + direction.y);
        
        //also, if it's moving into/out of danger, update the inDanger var
        if (b.getSpace(selfCoords.x + direction.x, selfCoords.y + direction.y).getDamageNextTurn() == 0) {
            inDanger = false;
        } else {
            inDanger = true;
        }

        return b;
    }
}
