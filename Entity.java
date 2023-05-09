import java.awt.Point;
import java.awt.Color;

class Entity implements Cloneable{
    //this is an Entity. it represents any thing that can exist on the board within a space
    //this is a very general superclass, never meant to appear as a straight entity
    //examples of entitie: player, enemies, any objects the player puts down, blockers, etc
    //very nice 

    //maxHealth and health of course, entities need the capacity to die
    private int maxHealth;
    private int health;

    private boolean blocksPathing; //is true for most, false for things like bear traps that I could add in the future
    private Color colorOverride = null; //color override makes the entity a different color than is standard

    //entity will never be made itself, but it needs constructors so other classes can call super() and such
    public Entity() {
        maxHealth = 1;
        health = 1;
        blocksPathing = true;
    }
    public Entity(int maxHealth, int health, boolean blocksPathing) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.blocksPathing = blocksPathing;
    }

    //simple toString
    //will never matter anyway, all the subclasses override this
    public String toString() {
        if (colorOverride != null) { //if there's a color override
            return colorOverrideToAnsi() + "()"; //use it
        }
        return "()"; //otherwise just don't care, this will never be called
    }

    //copies the Entity, mostly just for ease of deep copying other things like space and board
    public Entity clone() throws CloneNotSupportedException {
        Entity entityCopy = (Entity) super.clone(); //make a shallow copy, make sure to parse it as entity
        return entityCopy; //return, nice and simple
        //since entities don't have anything other than primitiveTypes, it works out
    }

    //getters
    public int getMaxHealth() {return maxHealth;}
    public int getHealth() {return health;}
    public boolean getIfBlocksPathing() {return blocksPathing;}
    public Color getColorOverride() {return colorOverride;}

    //setters
    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
    public void setHealth(int health) {this.health = health;}
    public void setIfBlocksPathing(boolean blocksPathing) {this.blocksPathing = blocksPathing;}
    public void setColorOverride(Color colorOverride) {this.colorOverride = colorOverride;}

    //This will be the main method for changing health of entities
    //the setters will be for simply setting an amount (if they are ever necesary), but this is what will be used commonly whenever health must be affected
    //so this will probably be overridden a lot if an enemy has something special happen when they take damage(for example if the enemy has armor, or if they don't take damage, etc) 
    public void affectHealth(int amount) {
        health += amount; //add/subtract the amount

        //make sure they don't have more health than max health, if so then make them even 
        if (health > maxHealth) { 
            health = maxHealth;
        }
    }

    //same basic premise for affectMaxHealth as for affectHealth, could be overridden
    public void affectMaxHealth(int amount) {
        maxHealth += amount;
        health += amount; //make sure you increase health alongside max health
    }

    //finds this entity in a board and returns its coordinates in a point
    public Point findSelfCoords(Board b) {
        for (int i = 0; i < b.getMainArray().length; i++) { //loops through the array it was provided
            for (int j = 0; j < b.getMainArray()[0].length; j++) {
                if (b.getMainArray()[i][j].getEntity() == this) { //once it finds itself
                    return new Point(j, i); //return its own coords
                }
            }
        }
        return null; //if it never found itself, return nothing
        //dang, that's deep
    }

    //checks if it can move a certain direction. returns true if it can, false if not
    public boolean canMove(Board b, Point direction) {
        Point selfCoords = findSelfCoords(b); //find itself
        if (b.getSpace(selfCoords.x + direction.x, selfCoords.y + direction.y).getEntity() == null) { //if there is no entity in the space it wants to go to
            return true; //it can indeed move there
        } else if (b.getSpace(selfCoords.x + direction.x, selfCoords.y + direction.y).getEntity().getIfBlocksPathing() == false) { //if the entity it wants to step on has no collision
            return true; //step right on it, into the probably a bear trap you go
        } else { //if there is an entity and it has collision
            return false; //it cannot move that way
        }
    }

    //pretty self explanatory
    //moves this entity in the direction specified
    public Board move(Board b, Point direction) throws CloneNotSupportedException{
        Point selfCoords = findSelfCoords(b); //find self
        b = b.clone(); //clone the board just in case, you never know what tomfoolery can be caused by failure to clone
        b.removeEntityFromSpace(selfCoords.x, selfCoords.y); //where you previously were shall be gone
        b.addEntityToSpace(this, selfCoords.x + direction.x, selfCoords.y + direction.y); //adds self back to the new position
        
        return b; //returns the board of course
    }

    //takes the RGB value of colorOverride and converts it into a usuable ANSI escape code for color
    //nice, quality of life
    public String colorOverrideToAnsi() {
        return "\033[38;2;"+ colorOverride.getRed() +";"+ colorOverride.getGreen() +";"+ colorOverride.getBlue() +"m";
    }
}