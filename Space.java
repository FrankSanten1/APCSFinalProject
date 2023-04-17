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

    //way to deep copy the space
    public Space clone() throws CloneNotSupportedException{
        Space spaceCopy = (Space) super.clone();//shallow copy of the space

        if (this.getEntity() != null) { //if it has an entity:
            spaceCopy.setEntity(this.getEntity().clone()); //copy it as well to achieve deep copy
        } else { //if there is no entity:
            spaceCopy.setEntity(null); //just set it to null
        }
        
        return spaceCopy; //return successfully deep copied space
    }

    //quick and simple way to remove an entity from the space
    public void removeEntity() {
        entityInSpace = null;
    }

    //getters and setters
    public void setEntity(Entity newEntity) {entityInSpace = newEntity;}
    public Entity getEntity() {return entityInSpace;}
    public int getDamageNextTurn() {return damageNextTurn;}
    public void setDamageNextTurn(int damageNextTurn) {this.damageNextTurn = damageNextTurn;}

    //easier way to add/subtract from damageNextTurn
    public void shiftDamageNextTurn(int shiftAmount) {
        damageNextTurn += shiftAmount;
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