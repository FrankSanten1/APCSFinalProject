public class Player extends Entity{

    private PlayerStatsTracker stats;
    
    public Player(PlayerStatsTracker stats) {
        super(stats.getMaxHealth(), stats.getHealth(), true);
        this.stats = stats;
    }

    public String toString() {
        return "\033[38;2;0;255;255m" + "[]";
    }
}
