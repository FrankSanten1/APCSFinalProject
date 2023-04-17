public class PlayerStatsTracker {
    private int maxHealth;
    private int health;
    private int maxMana;
    private int mana;
    private Ability[] abilities;

    public PlayerStatsTracker(int maxHealth, int health, int maxMana, int mana, Ability[] abilities) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.maxMana = maxMana;
        this.mana = mana;
        this.abilities = abilities;
    }

    public int getMaxHealth() {return maxHealth;}
    public int getHealth() {return health;}
    public int getMaxMana() {return maxMana;}
    public int getMana() {return mana;}
    public Ability[] getAbilities() {return abilities;}

    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
    public void setHealth(int health) {this.health = health;}
    public void setMaxMana(int maxMana) {this.maxMana = maxMana;}
    public void setMana(int mana) {this.mana = mana;}
    public void setAbilities(Ability[] abilities) {this.abilities = abilities;}
}
