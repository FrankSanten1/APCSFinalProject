import java.awt.Point;

public class Player extends Entity{

    private PlayerStatsTracker stats;
    private boolean inDanger;

    public boolean getInDanger() {return inDanger;}
    public void setInDanger(boolean inDanger) {this.inDanger = inDanger;}
    
    public Player(PlayerStatsTracker stats) {
        super(stats.getMaxHealth(), stats.getHealth(), true);
        this.stats = stats;
    }

    public String toString() {
        if (getColorOverride() != null) {
            return colorOverrideToAnsi() + "[]";
        }
        if (inDanger == false) {
            return "\033[38;2;0;255;255m" + "[]";
        }
        return "\033[38;2;128;128;255m" + "[]";
    }

    public void affectHealth(int amount) {
        super.affectHealth(amount);
        stats.setHealth(getHealth());
    }

    public void affectMaxHealth(int amount) {
        super.affectMaxHealth(amount);
        stats.setMaxHealth(getMaxHealth());
    }

    public Board move(Board b, Point direction) throws CloneNotSupportedException{
        Point selfCoords = findSelfCoords(b);
        b = b.clone();
        b.removeEntityFromSpace(selfCoords.x, selfCoords.y);
        b.addEntityToSpace(this, selfCoords.x + direction.x, selfCoords.y + direction.y);
        
        if (b.getSpace(selfCoords.x + direction.x, selfCoords.y + direction.y).getDamageNextTurn() == 0) {
            inDanger = false;
        } else {
            inDanger = true;
        }

        return b;
    }
}
