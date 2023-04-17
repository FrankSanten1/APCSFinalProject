public class Player extends Entity{
    
    public Player(int maxHealth, int health) {
        super(maxHealth, health, true);
    }

    public String toString() {
        return "\033[38;2;0;255;255m" + "[]";
    }
}
