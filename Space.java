class Space implements Cloneable{
    private Entity entityInSpace;
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