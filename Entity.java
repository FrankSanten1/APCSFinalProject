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
}