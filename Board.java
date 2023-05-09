import java.util.ArrayList;
import java.awt.Point;

class Board implements Cloneable{
    //this class is for the playing field that the player fights enemies on
    // 2D grid of spaces
    
    //has an array of Spaces, which hold data about what's going on in that square
    private Space[][] mainArray;

    public Board(int width, int height) {
        //initialize the mainArray with an array of spaces sized appropriately
        mainArray = new Space[height][width];

        //fill spaces with empty instances of the space class instead of just null
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mainArray[j][i] = new Space();
            }
        }
        
        //set up blockers on the edges of the board
        for (int i = 0; i < width; i++) {
            mainArray[0][i].setEntity(new Blocker());
            mainArray[mainArray.length - 1][i].setEntity(new Blocker());
        }
        for (int i = 1; i < (height - 1); i++) {
            mainArray[i][0].setEntity(new Blocker());
            mainArray[i][mainArray[0].length - 1].setEntity(new Blocker());
        }
        
    }

    public void printBoard() {
        //just print out every space
        for (Space[] x : mainArray) {
            for (Space y : x) {
                System.out.print(y); //they have toString methods custom, so it works
            }
            System.out.println("");
        }
        System.out.print("\033[38;2;255;255;255m"); //make all text after it white
    }

    
    public Board clone() throws CloneNotSupportedException{
        //makes a deep copy of the current board
        //useful when taking a snapshot in time of a board that should not change when that other board is changed
        //basically, just the clone() method only creates a different pointer for this object, it still uses the same pointers for all the objects it has
        //primitive types are separated automatically by clone(), but not objects like spaces or arrays
        Board boardCopy = (Board) super.clone(); //shallow copy the board

        //but the mainArray isn't deep copied, so make a new one with the same dimensions
        boardCopy.setMainArray(new Space[this.getMainArray().length][this.getMainArray()[0].length]);

        //fill that mainArray with deep copies of all the spaces that the previous one had
        for (int i = 0; i < boardCopy.getMainArray().length; i++) { //loop through entire array
            for (int j = 0; j < boardCopy.getMainArray()[0].length; j++) {
                boardCopy.setSpaceAt(this.getMainArray()[i][j].clone(), i, j); //make a deep copy of the space at each location
            }
        }
        //shaboom shabang, you should have yourself a deeply copied board, all elements in completely separate memory locations from the original board
        return boardCopy;
    }

    //sets the space at those coords to the space specified
    public void setSpaceAt(Space newSpace, int y, int x) {
        mainArray[y][x] = newSpace;
    }
    
    //returns the space at those coords. There's an x/y version and a Point version
    //i like the Point class. it's quite useful.
    public Space getSpace(int xPosition, int yPosition) {
        return mainArray[yPosition][xPosition];
    }
    public Space getSpace(Point position) {
        return mainArray[position.y][position.x];
    }

    //returns the array of spaces in this class
    public Space[][] getMainArray() {
        return mainArray;
    }

    //sets the array of spaces to a new one
    //unsure if I'll ever use this, since I can just make a new board, but it doesn't harm anything
    public void setMainArray(Space[][] mainArray) {
        this.mainArray = mainArray;
    }

    //adds an entity to a specified space on the board
    public void addEntityToSpace(Entity entityToAdd, int xPosition, int yPosition) {
        mainArray[yPosition][xPosition].setEntity(entityToAdd);
    }

    //removes an entity, if there is one, from a space on the board
    public void removeEntityFromSpace(int xPosition, int yPosition) {
        mainArray[yPosition][xPosition].removeEntity();
    }

    //finds the space that the player is in, and returns it
    public Space getPlayerSpace() {
        for (int i = 0; i < mainArray.length; i++) { //loop through all of the mainArray
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Player) { //the first time you see an entity that is a player:
                    return mainArray[i][j]; //return the space there
                }
            }
        }
        return null;
    }
    //theoretically, there is always exactly one player, so this should work in any scenario. 

    //finds the space that the player is in, and returns its coordinates. 
    public Point getPlayerCoords() {
        for (int i = 0; i < mainArray.length; i++) { //loop through all of the mainArray
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Player) { //first time you see a player
                    return new Point(j, i); //return its coords
                }
            }
        }
        return null;
    }

    //returns true if there are enemies left, false if otherwise
    public boolean enemiesRemain() {
        for (int i = 0; i < mainArray.length; i++) { //loop through all of the mainArray
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Enemy) { //if an enemy is found:
                    return true; //then say that there are still enemies remaining
                }
            }
        }
        return false; //if you go through the entire array without finding enemies, there are no more enemies. 
    }

    //finds the locations of every enemy on the board, and returns an arrayList of their coordinates
    public ArrayList<Point> findAllEnemyLocations() {
        ArrayList<Point> coords = new ArrayList<Point>(); //create an arrayList
        for (int i = 0; i < mainArray.length; i++) { //loop through the entire array
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Enemy) { //if an enemy is found:
                    coords.add(new Point(j, i)); //add its coords to the arrayList
                }
            }
        }
        return coords; //return all the coords of the enemies found
    }

    //this method removes entities that have died from the board
    //it ignores the player, as the player has a special case for when they die
    public void cleanDeadEntities() {
        for (int i = 0; i < mainArray.length; i++) { //loop through all of the mainArray
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Entity) { //if there's an entity there
                    if (!(mainArray[i][j].getEntity() instanceof Player)) { //if it's not the player
                        if (mainArray[i][j].getEntity().getHealth() <= 0) { //if they should be dead
                            //they shall be removed!
                            if (mainArray[i][j].getEntity() instanceof Enemy) { //if they're an enemy, you gotta remove any attack indicators from tiles they were attacking
                                ArrayList<Point> coords = ((Enemy) mainArray[i][j].getEntity()).getPlacesToAttack(); //get all the places they were gonna attack
                                if (coords != null) { //make sure that the list of coords isn't null before proceeding
                                    for (Point p : coords) { //loop through the list of coords they indicated they were to attack
                                        getSpace(p).shiftDamageNextTurn(((Enemy) mainArray[i][j].getEntity()).getAttackPowerInstance() * -1); //remove the damageNextTurn indicators that they caused, since they're dead now
                                    }
                                }
                            }
                            mainArray[i][j].removeEntity(); //and of course, remember to remove the entity
                        }
                    }
                }
            }
        }
    }
    //excellent triangle of code, i must say

    //methods that didn't end up being used, here just in case I need them later
    //feel free to ignore this part, none of it matters anymore
    /*
    public boolean canMovePlayer(int xMovement, int yMovement) {
        Point playerCoords = getPlayerCoords();
        if (mainArray[playerCoords.y + yMovement][playerCoords.x + xMovement].spaceIsWalkable()) {
            return true;
        } else {
            return false;
        }
    }

    public void movePlayer(int xMovement, int yMovement) {
        Point playerCoords = getPlayerCoords();
        mainArray[playerCoords.y + yMovement][playerCoords.x + xMovement].setEntity(mainArray[playerCoords.y][playerCoords.x].getEntity());
        mainArray[playerCoords.y][playerCoords.x].removeEntity();
    }
    */
    /* 
    public boolean canMoveEntity(int xPositionOfEntity, int yPositionOfEntity, int xMovement, int yMovement) {
        if (mainArray[yPositionOfEntity + yMovement][xPositionOfEntity + xMovement].spaceIsWalkable()) {
            return true;
        } else {
            return false;
        }
    }

    public void moveEntity(int xPositionOfEntity, int yPositionOfEntity, int xMovement, int yMovement) {
        mainArray[yPositionOfEntity + yMovement][xPositionOfEntity + xMovement].setEntity(mainArray[yPositionOfEntity][xPositionOfEntity].getEntity());
        mainArray[yPositionOfEntity][xPositionOfEntity].removeEntity();
    }
    */
}