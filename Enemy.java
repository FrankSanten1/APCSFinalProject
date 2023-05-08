import java.util.ArrayList;
import java.awt.Point;

class Enemy extends Entity {
    //This will be the superclass of all enemies
    //objects of this won't eb created, but objects its subclasses will be
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

    public ArrayList<Board> moveAndSetUpAttack(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> moveFrames = moveStage(currentSituation);
        ArrayList<Board> attackFrames = setUpAttack(moveFrames.get(moveFrames.size()-1));
        attackFrames.remove(0);
        for (Board x : attackFrames) {
            moveFrames.add(x);
        }
        return moveFrames;
    }

    public Point findDirectionToPlayer(Board b) {
        Point selfCoords = findSelfCoords(b);
        Point playerCoords = b.getPlayerCoords();
        //System.out.println(selfCoords);
        //b.printBoard();

        Point direction = new Point();
        direction.x = playerCoords.x - selfCoords.x;
        direction.y = playerCoords.y - selfCoords.y;
        return direction;
    }

    public String healthToColor() {
        double ratio = (double) this.getHealth() / this.getMaxHealth();
        int num = (int) (ratio * 255);
        return "\033[38;2;255;"+ num +";"+ num +"m";
    }

    public String toString() {
        if (getColorOverride() != null) {
            return colorOverrideToAnsi() + "!!";
        }
        return healthToColor() + "!!";
    }

}