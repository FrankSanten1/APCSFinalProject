import java.awt.Point;

class Entity implements Cloneable{
    private int maxHealth;
    private int health;
    private boolean blocksPathing;

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

    public String toString() {
        return "()";
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

    //setters
    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
    public void setHealth(int health) {this.health = health;}
    public void setIfBlocksPathing(boolean blocksPathing) {this.blocksPathing = blocksPathing;}

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

    public Point findSelfCoords(Board b) {
        for (int i = 0; i < b.getMainArray().length; i++) {
            for (int j = 0; j < b.getMainArray()[0].length; j++) {
                if (b.getMainArray()[i][j].getEntity() == this) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    public boolean canMove(Board b, Point direction) {
        Point selfCoords = findSelfCoords(b);
        if (b.getSpace(selfCoords.x + direction.x, selfCoords.y + direction.y).getEntity() == null) {
            return true;
        } else if (b.getSpace(selfCoords.x + direction.x, selfCoords.y + direction.y).getEntity().getIfBlocksPathing() == false) {
            return true;
        } else {
            return false;
        }
    }

    public Board move(Board b, Point direction) throws CloneNotSupportedException{
        Point selfCoords = findSelfCoords(b);
        b = b.clone();
        b.removeEntityFromSpace(selfCoords.x, selfCoords.y);
        b.addEntityToSpace(this, selfCoords.x + direction.x, selfCoords.y + direction.y);
        

        return b;
    }
}