public class PlayerStatsTracker {
    //this class is here to keep track of all the player's stats, even across boards
    //there will be one instance made in Main, and it will persist for the duration of the game, across rounds

    //all the stats being kept track of:
    //health and maxHealth
    private int maxHealth;
    private int health;
    //mana and maxMana
    private int maxMana;
    private int mana;
    //the list of abilities the player can use
    private Ability[] abilities;

    //constructor with all the required things set
    public PlayerStatsTracker(int maxHealth, int health, int maxMana, int mana, Ability[] abilities) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.maxMana = maxMana;
        this.mana = mana;
        this.abilities = abilities;
    }

    //getters 
    public int getMaxHealth() {return maxHealth;}
    public int getHealth() {return health;}
    public int getMaxMana() {return maxMana;}
    public int getMana() {return mana;}
    public Ability[] getAbilities() {return abilities;}

    //setters
    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
    public void setHealth(int health) {this.health = health;}
    public void setMaxMana(int maxMana) {this.maxMana = maxMana;}
    public void setMana(int mana) {this.mana = mana;}
    public void setAbilities(Ability[] abilities) {this.abilities = abilities;}

    //shifts mana up/down, when spell is used
    public void shiftMana(int manaToShift) {mana += manaToShift;}
}
