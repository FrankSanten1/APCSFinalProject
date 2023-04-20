import java.util.ArrayList;
import java.awt.Point;

class Enemy extends Entity {
    //This will be the superclass of all enemies
    //objects of this won't eb created, but objects its subclasses will be
    //polymorphism poggers

    //how much damage it will do
    private int attackPower;

    //stores coordinates of places it will damage next turn
    private ArrayList<Point> placesToAttack;

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

    

    //getters and setters for the attackPower stat
    public int getAttackPower() {return attackPower;}
    public void setAttackPower(int attackPower) {this.attackPower = attackPower;}

    //Takes in the current board, chooses places to attack next turn
    //Increases damageNextTurn on relevant spaces and placesToAttack for this object, returns modified board
    public Board setUpAttack(Board currentSituation) {
        return currentSituation;
        //b/c this is just the superclass, doesn't do anything. This will be overridden in the subclasses.
    }

    //takes in the current board, moves guy to wherever he needs to be
    //returns an arrayList of places he moved, play through them for smooth animation
    //last board in the arrayList that was returned will be where the gameplay picks up next turn
    //need to work on a good pathing AI for this
    public ArrayList<Board> moveStage(Board currentSituation) {
        ArrayList<Board> animations = new ArrayList<Board>();
        animations.add(currentSituation);
        return animations;
        //b/c this is just the superclass, doesn't do anything. This will be overridden in the subclasses.
    }

    //takes in the current board, damages everything on the tiles it set up last turn with the setUpAttack() method
    //damages all tiles in the placesToAttack arrayList, reduces the damage indicators on the spaces there as well
    //returns an arrayList of animations just in case I want it to look pretty later
    //last board in the arrayList that was returned will be where the gameplay picks up next turn
    public ArrayList<Board> attack(Board currentSituation) {
        ArrayList<Board> animations = new ArrayList<Board>();
        animations.add(currentSituation);
        return animations;
        //b/c this is just the superclass, doesn't do anything. This will be overridden in the subclasses.
    }

    public Point findDirectionToPlayer(Board b) {
        Point selfCoords = findSelfCoords(b);
        Point playerCoords = b.getPlayerCoords();

        Point direction = new Point();
        direction.x = playerCoords.x - selfCoords.x;
        direction.y = playerCoords.y - selfCoords.y;
        return direction;
    }
}