import java.util.ArrayList;
import java.awt.Point;

public class Swordsman extends Enemy {
    
    public Swordsman() {
        super(10, 10, 3);
    }

    
    public ArrayList<Board> moveStage(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> animations = new ArrayList<Board>(); //will hold every step taken
        animations.add(currentSituation); //start with the beginning
        for (int i = 0; i < 3; i++) { //loop as many times as it will move
            //The direction to the player will be important for finding where to move
            Point directionToPlayer = findDirectionToPlayer(animations.get(animations.size()-1));
            //the direction desired is just the direction to the player, but each direction will be a max of 1
            Point directionDesired = new Point();
            //Setting values for directionDesired:
            try {
                //divides directionToPlayer by the absolute value of it to make it
                //1 if directionToPlayer was positive, and -1 if directionToPlayer was negative
                directionDesired.x = (directionToPlayer.x) / (Math.abs(directionToPlayer.x));
            } catch (Exception e) { //throws an error if it tries to divide by zero
                //if it's dividing by zero, just set it to zero
                directionDesired.x = 0;
            }
            //same exact thing for y as happened above for x
            try {
                directionDesired.y = (directionToPlayer.y) / (Math.abs(directionToPlayer.y));
            } catch (Exception e) {
                directionDesired.y = 0;
            }

            //booleans showing whether you can move in the desired x or y direction
            boolean canMoveX = this.canMove(animations.get(animations.size()-1), new Point(directionDesired.x, 0));
            boolean canMoveY = this.canMove(animations.get(animations.size()-1), new Point(0, directionDesired.y));

            //directionToMove will be where it actually ends up moving
            //if left alone, will remain as (0, 0)
            Point directionToMove = new Point();

            if (Math.abs(directionToPlayer.x) > Math.abs(directionToPlayer.y)) { //When it wants to move on the x axis more:
                if (canMoveX) { //check x first
                    directionToMove.x = directionDesired.x; //if able, move that way
                } else if (canMoveY) { //then check y
                    directionToMove.y = directionDesired.y; //if able, move
                }
            } else if (Math.abs(directionToPlayer.y) > Math.abs(directionToPlayer.x)) { //When it wants to move on the y axis more:
                if (canMoveY) { //check y first
                    directionToMove.y = directionDesired.y; //if able, move that way
                } else if (canMoveX) { //then check x
                    directionToMove.x = directionDesired.x; //if able, move
                }
            } else { //When it has no preference for moving on x or y
                double rand = Math.random(); //randomize it!
                if (rand > 0.5) { //half chance it will prefer x
                    if (canMoveX) { //same code as above for x
                        directionToMove.x = directionDesired.x;
                    } else if (canMoveY) {
                        directionToMove.y = directionDesired.y;
                    }
                } else { //half chance it will prefer y
                    if (canMoveY) { //same code as above for y
                        directionToMove.y = directionDesired.y;
                    } else if (canMoveX) {
                        directionToMove.x = directionDesired.x;
                    }
                }
            }
            //directionToMove should be set to a value now
            //now, to add it to the arrayList of things that happened
            if (!(directionToMove.equals(new Point()))) { //if it actually did decide to move:
                //add a frame of this enemy moving that way
                animations.add(this.move(animations.get(animations.size()-1), directionToMove));
            }
            //now, return back to the top of the for loop and potentially do it again
        }
        //once all moves are done, return what happened
        return animations;
    }
    
    public String toString() {
        return this.healthToColor() + "{}";
    }
}
