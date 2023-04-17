class Entity implements Cloneable{
    private int maxHealth;
    private int health;
    boolean blocksPathing;

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

    public Entity clone() throws CloneNotSupportedException {
        Entity entityCopy = (Entity) super.clone();
        return entityCopy;
    }

    //getters
    public int getMaxHealth() {return maxHealth;}
    public int getHealth() {return health;}
    public boolean getIfBlocksPathing() {return blocksPathing;}
    //setters
    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
    public void setHealth(int health) {this.health = health;}
    public void setIfBlocksPathing(boolean blocksPathing) {this.blocksPathing = blocksPathing;}

    public void affectHealth(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void affectMaxHealth(int amount) {
        maxHealth += amount;
        health += amount;
    }
}