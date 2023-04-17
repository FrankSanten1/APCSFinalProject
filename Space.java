class Space implements Cloneable{
    //each object of this class represents a tile in the playing field

    //holds the entity present in the space, or null if there isn't one
    private Entity entityInSpace;

    //also holds a representation of how much damage will be dealt by enemies to anything in this tile next turn
    //well, techmically the damage won't be done to enemies. ok rephrase: damage dealt by enemies to the player and anything it spawns
    private int damageNextTurn;

    public Space() {
        entityInSpace = null;
        damageNextTurn = 0;
    }

    public Space(Entity entityInSpace) {
        this.entityInSpace = entityInSpace;
        damageNextTurn = 0;
    }

    public String toString() {
        if (entityInSpace == null) {
            return "\033[38;2;0;0;0m" + "<>";
        } else {
            return entityInSpace.toString();
        }
    }

    public Space clone() throws CloneNotSupportedException{
        Space spaceCopy = (Space) super.clone();

        if (this.getEntity() != null) {
            spaceCopy.setEntity(this.getEntity().clone());
        } else {
            spaceCopy.setEntity(null);
        }
        

        return spaceCopy;
    }

    public void removeEntity() {
        entityInSpace = null;
    }

    public void setEntity(Entity newEntity) {
        entityInSpace = newEntity;
    }

    public Entity getEntity() {
        return entityInSpace;
    }

    public boolean spaceIsWalkable() {
        if (entityInSpace == null) {
            return true;
        } else if (entityInSpace.getIfBlocksPathing() == false) {
            return true;
        } else {
            return false;
        }
    }
}