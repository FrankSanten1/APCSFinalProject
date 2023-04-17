public class Blocker extends Entity{
    
    public Blocker() {
        super(99999, 99999, true);
    }

    public void affectHealth(int amount) {
        //and lo, there was nothing.
    }

    public void affectMaxHealth(int amount) {
        //this guy is invulnerable
    }

    public String toString() {
        return "\033[38;2;170;170;170m" + "##";
    }
}
