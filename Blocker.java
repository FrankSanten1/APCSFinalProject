public class Blocker extends Entity{
    //this is what makes up the walls of the arena
    //basically cosmetic, but it's really easy to just make them an entity and it solves a surprising amount of problems. 

    public Blocker() {
        super(99999, 99999, true); //i m m e n s e
        //probably don't have to give that much health to him since he's already invulnerable (see below)
        //eh, just in case
    }

    public void affectHealth(int amount) {
        //and lo, there was nothing.
        //basically, i overrode the method used to damage entities so that it does nothing
        //man is not physically able to take damage
    }

    public void affectMaxHealth(int amount) {
        //this guy is invulnerable
    }

    public String toString() {
        return "\033[38;2;170;170;170m" + "##"; //white hashtags
    }
}
