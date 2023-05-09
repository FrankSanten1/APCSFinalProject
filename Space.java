import java.awt.Color;

class Space implements Cloneable{
    //each object of this class represents a tile in the playing field

    //holds the entity present in the space, or null if there isn't one
    private Entity entityInSpace;

    //also holds a representation of how much damage will be dealt by enemies to anything in this tile next turn
    //well, techmically the damage won't be done to enemies. ok rephrase: damage dealt by enemies to the player and anything it spawns
    private int damageNextTurn;

    //for cosmetic purposes
    //if a space is highlighted, it shines brighter momentarily
    private boolean isHighlighted;

    //different kind of highlight
    //it's way too late in the night to be overhauling this ragtag highlight system, but i ought to make it more robust once I have the time
    private boolean isBeingInspected;

    //simple constructor
    public Space() {
        entityInSpace = null;
        damageNextTurn = 0;
    }
    //constructor that pre-bakes in an entity into the space when it's made
    public Space(Entity entityInSpace) {
        this.entityInSpace = entityInSpace;
        damageNextTurn = 0;
    }

    //toString is a bit more complex than normal
    public String toString() {
        if (entityInSpace == null) { //if there is no entity in the space: make it look like <>
            if (isBeingInspected) { //if it's being inspected:
                return "\033[38;2;0;255;0m" + "<>"; //make it look green
            }
            if (isHighlighted) { //if it's highlighted (probably to indicate that an enemy has just set up an attack against it)
                return "\033[38;2;255;0;255m" + "<>"; //make it bright purple
            }
            if (damageNextTurn > 0) { //if not highlighted but still in danger next turn:
                return "\033[38;2;128;0;128m" + "<>"; //less harsh purple
            }
            //if not in danger whatsoever
            return "\033[38;2;0;0;0m" + "<>"; //dark grey
        } else { //if there is an entity in the space:
            return entityInSpace.toString(); //return what that entity should look like
        }
    }

    //way to deep copy the space
    public Space clone() throws CloneNotSupportedException{
        Space spaceCopy = (Space) super.clone();//shallow copy of the space

        if (this.getEntity() != null) { //if it has an entity:
            spaceCopy.setEntity(this.getEntity().clone()); //copy it as well to achieve deep copy
        } else { //if there is no entity:
            spaceCopy.setEntity(null); //just set it to null
        }
        
        return spaceCopy; //return successfully deep copied space
    }

    //quick and simple way to remove an entity from the space
    public void removeEntity() {
        entityInSpace = null;
    }

    //getters and setters
    public void setEntity(Entity newEntity) {entityInSpace = newEntity;}
    public Entity getEntity() {return entityInSpace;}
    public int getDamageNextTurn() {return damageNextTurn;}
    public void setDamageNextTurn(int damageNextTurn) {this.damageNextTurn = damageNextTurn;}
    public boolean getIsHighlighted() {return isHighlighted;}
    public void setIsHighlighted(boolean isHighlighted) {this.isHighlighted = isHighlighted;}
    public boolean getIsBeingInspected() {return isBeingInspected;}
    public void setIsBeingInspected(boolean isBeingInspected) {this.isBeingInspected = isBeingInspected;}

    //ah, these two methods
    //a nice bandaid fix to a problem that I will be fixing later
    //and by later, I mean after I submit this final project, because for now it works well enough
    //the first of these methods make everything in the square green, and then the other sets it back to normal
    public void makeInspectingColor() {
        setIsBeingInspected(true);
        if (entityInSpace != null) {
            entityInSpace.setColorOverride(new Color(0, 255, 0));
        }
    }
    public void clearInspectingColor() {
        setIsBeingInspected(false);
        if (entityInSpace != null) {
            entityInSpace.setColorOverride(null);
        }
    }

    //way to add/subtract from damageNextTurn while updating the player's inDanger state
    public void shiftDamageNextTurn(int shiftAmount) {
        damageNextTurn += shiftAmount; //of course, do the action necesary

        if (entityInSpace instanceof Player) { //if this space had the player in it: 
            if (damageNextTurn == 0) { //if it will take no damage next turn: 
                ((Player) entityInSpace).setInDanger(false); //the player is in no danger and should appear as such
            } else { //if the tile has some damage it will take next turn: 
                ((Player) entityInSpace).setInDanger(true); //the player is in danger and should appear as such
            }
        }
    }

    //returns whether this space can be walked upon
    //ok i just checked and this method is in use in exactly zero places but i'm still keeping it just in case its needed later
    public boolean spaceIsWalkable() {
        if (entityInSpace == null) { //if there is no entity here: 
            return true; //yes you can step here
        } else if (entityInSpace.getIfBlocksPathing() == false) { //if there is an entity but it has no collision:
            return true; //yes you can step here
        } else { //otherwise, if there is an entity with collision: 
            return false; //nope, this space is occupied and not walkable
        }
    }
    
    //makes whatever is in this square take damage
    //used whenever the player attacks
    public void damageEntityHere(int damage) {
        if (entityInSpace != null) { //if there's an entity: 
            entityInSpace.affectHealth(-1 * damage); //damage that boy
        }
    } //if the entity dies, the corspe will be dealt with by whatever called this method hopefully

    //this is like damageEntityHere, but it doesn't damage enemies
    //because if enemies damaged thenselves that would make the game way too easy
    //the guys are not known for their stellar aim
    public void enemyAttacksHere(int damage) {
        if (entityInSpace != null) { //if there is an entity to damage:
            if (!(entityInSpace instanceof Enemy)) { //if that enemy is not an enemy: 
                entityInSpace.affectHealth(-1 * damage); //damage it
            }
        }
    } 

    //returns a description of what's happening in that space
    public String inspect() {
        if (entityInSpace == null) { //if there is no entity in the space: 
            //just say it's empty and say how much damage you'll take there if you stand there
            return "This space is empty. \nAt the end of this turn, this space will cause any non-enemy entity within it to recieve " + damageNextTurn + " damage.";
        }
        //if there is an entity in the space:
        return entityInSpace.inspect(); //pass on the responsibility to them to describe themselves
    }
}

//Hi Mr. J! If you see this, that means that you have to drink some water. now. this is an order. drink some water right now or I will know.  
//Btw, I asked my brother if he wanted to give you a message here, and he said: Mr. J, if you're reading this, please, please send help. This is Frank's brother, and he has trapped me in my basement and i cannot get out. 
//my brother is such a silly guy!

//(we don't even have a basement in our house lol)