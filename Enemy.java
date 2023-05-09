import java.util.ArrayList;
import java.awt.Point;

class Enemy extends Entity {
    //This will be the superclass of all enemies
    //objects of this won't be created, but objects its subclasses will be
    //polymorphism poggers

    //cosmetic: draws attention to enemy when it is acting
    private boolean isHighlighted;

    //how much damage it will do
    private int attackPower;

    //stores coordinates of places it will damage next turn
    private ArrayList<Point> placesToAttack;

    //how much attack they had when they set up their attack
    private int attackPowerInstance;
    //so that alchemist doesn't buff an enemy you thought was going to leave you at 1hp, that seems like some bull

    //simple constructors
    //complex:
    public Enemy(int maxHealth, int health, int attackPower) {
        super(maxHealth, health, true);
        this.attackPower = attackPower;
    }

    //basic:
    public Enemy() {
        super(1, 1, true);
        this.attackPower = 1;
    }

    

    //getters and setters
    public int getAttackPower() {return attackPower;}
    public void setAttackPower(int attackPower) {this.attackPower = attackPower;}
    public int getAttackPowerInstance() {return attackPowerInstance;}
    public void setAttackPowerInstance(int attackPowerInstance) {this.attackPowerInstance = attackPowerInstance;}
    public boolean getIsHighlighted() {return isHighlighted;}
    public void setIsHighlighted(boolean isHighlighted) {this.isHighlighted = isHighlighted;}
    public ArrayList<Point> getPlacesToAttack() {return placesToAttack;}
    public void setPlacesToAttack(ArrayList<Point> placesToAttack) {this.placesToAttack = placesToAttack;}


    //Takes in the current board, chooses places to attack next turn
    //Increases damageNextTurn on relevant spaces and placesToAttack for this object, returns modified board
    public ArrayList<Board> setUpAttack(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> animations = new ArrayList<Board>();
        animations.add(currentSituation);
        return animations;
        //b/c this is just the superclass, doesn't do anything. This will be overridden in the subclasses.
    }

    //takes in the current board, moves guy to wherever he needs to be
    //returns an arrayList of places he moved, play through them for smooth animation
    //last board in the arrayList that was returned will be where the gameplay picks up next turn
    //need to work on a good pathing AI for this
    public ArrayList<Board> moveStage(Board currentSituation) throws CloneNotSupportedException {
        ArrayList<Board> animations = new ArrayList<Board>();
        animations.add(currentSituation);
        return animations;
        //b/c this is just the superclass, doesn't do anything. This will be overridden in the subclasses.
    }

    //takes in the current board, damages everything on the tiles it set up last turn with the setUpAttack() method
    //damages all tiles in the placesToAttack arrayList, reduces the damage indicators on the spaces there as well
    //returns an arrayList of animations just in case I want it to look pretty later
    //last board in the arrayList that was returned will be where the gameplay picks up next turn
    public ArrayList<Board> attack(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> animations = new ArrayList<Board>();
        animations.add(currentSituation);
        return animations;
        //b/c this is just the superclass, doesn't do anything. This will be overridden in the subclasses.
    }

    //since the move and setUpAttack commands are almost always used in tandem, i decided to make a method that does that easier
    public ArrayList<Board> moveAndSetUpAttack(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> moveFrames = moveStage(currentSituation); //gets all the animations from moving
        ArrayList<Board> attackFrames = setUpAttack(moveFrames.get(moveFrames.size()-1)); //gets all the animations from setting up an attack after moving
        attackFrames.remove(0); //there's one dead frame that comes from attack that just becomes redundant when combined with the dead frame at the end of moveFrames, so it's getting removed
        for (Board x : attackFrames) { //loop through all the remaining attackFrames
            moveFrames.add(x); //add them to moveFrames
        }
        return moveFrames; //return moveFrames, newly combined with attackFrames
    }

    //finds the amount of x/y distance from the player that this enemy is, and returns it in point form as if the enemy was at the origin of a graph and the player was the point
    public Point findDirectionToPlayer(Board b) {
        Point selfCoords = findSelfCoords(b); //find the enemy's coords
        Point playerCoords = b.getPlayerCoords(); //find the player's coords

        Point direction = new Point(); //make a point
        //now, the point's x/y values will be set so that they are the distance from the enemy to the player
        direction.x = playerCoords.x - selfCoords.x; //set X value 
        direction.y = playerCoords.y - selfCoords.y; //set Y value
        return direction; //return that bad boy
    }

    //the enemies grow more and more red the more damaged they are
    //this returns the ANSI escape code that corresponds to how red they should be based off their health
    public String healthToColor() {
        double ratio = (double) this.getHealth() / this.getMaxHealth(); //decomal value of their percentage of health remaining
        int num = (int) (ratio * 255); //multiply ratio by 255, making it 255 if health is max and 0 if health is minimum
        return "\033[38;2;255;"+ num +";"+ num +"m"; //make all color values other than red slowly recede as health is lower
    }

    //wow a toString
    //prints the model desired in the color that is appropriate
    //will be overridden in all other classes
    public String toString() {
        if (getColorOverride() != null) { //if there is a colorOverride:
            return colorOverrideToAnsi() + "!!"; //change color to that
        }
        return healthToColor() + "!!"; //if no color override, just do standard color for this amount of health        
    }

    //shows information about this enemy
    public String inspect() {
        return "Enemy\nHealth: \033[38;2;255;0;0m" + getHealth() + "/" + getMaxHealth() + "\033[38;2;255;255;255m\nIf this message is popping up, I goofed up the code somewhere.";
    }

}