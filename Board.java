import java.util.ArrayList;
import java.awt.Point;

class Board implements Cloneable{
    private Space[][] mainArray;

    public Board(int width, int height) {
        //initialize the board with an array
        mainArray = new Space[height][width];

        //fill spaces with empty instances of the space class instead of just null
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mainArray[j][i] = new Space();
            }
        }
        
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
        for (Space[] x : mainArray) {
            for (Space y : x) {
                System.out.print(y);
            }
            System.out.println("");
        }
        System.out.print("\033[38;2;255;255;255m");
    }

    
    public Board clone() throws CloneNotSupportedException{
        Board boardCopy = (Board) super.clone();
        boardCopy.setMainArray(new Space[this.getMainArray().length][this.getMainArray()[0].length]);

        for (int i = 0; i < boardCopy.getMainArray().length; i++) {
            for (int j = 0; j < boardCopy.getMainArray()[0].length; j++) {
                boardCopy.setSpaceAt(this.getMainArray()[i][j].clone(), i, j);
            }
        }
        return boardCopy;
    }

    public void setSpaceAt(Space newSpace, int y, int x) {
        mainArray[y][x] = newSpace;
    }
    

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

    public Space getSpace(int xPosition, int yPosition) {
        return mainArray[yPosition][xPosition];
    }

    public Space[][] getMainArray() {
        return mainArray;
    }

    public void setMainArray(Space[][] mainArray) {
        this.mainArray = mainArray;
    }

    public void addEntityToSpace(Entity entityToAdd, int xPosition, int yPosition) {
        mainArray[yPosition][xPosition].setEntity(entityToAdd);
    }

    public Space getPlayerSpace() {
        for (int i = 0; i < mainArray.length; i++) {
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Player) {
                    return mainArray[i][j];
                }
            }
        }
        return null;
    }

    public Point getPlayerCoords() {
        for (int i = 0; i < mainArray.length; i++) {
            for (int j = 0; j < mainArray[0].length; j++) {
                if (mainArray[i][j].getEntity() instanceof Player) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

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
}