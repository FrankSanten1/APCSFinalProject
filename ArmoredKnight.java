import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;

public class ArmoredKnight extends Enemy{
    //basically a slightly buffed swordsman
    //many of the methods are just copied over and slightly modified
    //much healthier, slightly more damage, more area attacked, slower
    
    public ArmoredKnight() {
        super(20, 20, 4);
    }

    //this is called whenever the enemy enters the stage when it moves towards the player
    //armored knights move 2 times
    //basically copied from Swordsman, it's just the for loop goes one less time
    public ArrayList<Board> moveStage(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> animations = new ArrayList<Board>(); //will hold every step taken
        setIsHighlighted(true);
        animations.add(currentSituation); //start with the beginning
        for (int i = 0; i < 2; i++) { //loop as many times as it will move
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

    //this runs whenever an enemy is setting up where they will attack for next turn
    //usually right after moveStage
    //the armored knight attacks the five tiles in a u-shape that are closest to the player
    //very similar to swordsman attack, slightly modified area
    public ArrayList<Board> setUpAttack(Board currentSituation) throws CloneNotSupportedException{
        
        ArrayList<Board> animations = new ArrayList<Board>(); //of course, make the animations arrayList
        setIsHighlighted(true); //highlight him to imply it's his turn
        setAttackPowerInstance(getAttackPower()); //save the attackPower at this current time
        animations.add(currentSituation.clone()); //first frame is just a rest frame when the enemy is highlighted
        ArrayList<Point> placesToAttack = new ArrayList<Point>(); //initialize an array that will be filled soon

        //of course, needs a few things about the board to make descisions about which direction it will attack
        Point selfCoords = findSelfCoords(currentSituation); //needs to know where itself is
        Point directionToPlayer = findDirectionToPlayer(currentSituation); //needs to know what way the player is

        if (Math.abs(directionToPlayer.x) > Math.abs(directionToPlayer.y)) { //if the player is mostly towards a horizontal direction: 
            placesToAttack.add(new Point(selfCoords.x, selfCoords.y + 1)); //add the points one above and below to the placesToAttack
            placesToAttack.add(new Point(selfCoords.x, selfCoords.y - 1));
            placesToAttack.add(new Point(selfCoords.x + (directionToPlayer.x / Math.abs(directionToPlayer.x)), selfCoords.y)); //add three tiles that are in the direction of the player
            placesToAttack.add(new Point(selfCoords.x + (directionToPlayer.x / Math.abs(directionToPlayer.x)), selfCoords.y + 1));
            placesToAttack.add(new Point(selfCoords.x + (directionToPlayer.x / Math.abs(directionToPlayer.x)), selfCoords.y - 1));
        } else if (Math.abs(directionToPlayer.x) < Math.abs(directionToPlayer.y)) { //if the player is mostly towards a vertical direction:
            placesToAttack.add(new Point(selfCoords.x + 1, selfCoords.y)); //add both horizontal points
            placesToAttack.add(new Point(selfCoords.x - 1, selfCoords.y));
            placesToAttack.add(new Point(selfCoords.x, selfCoords.y + (directionToPlayer.y / Math.abs(directionToPlayer.y)))); //add three tiles that are in the direction of the player, vertical this time
            placesToAttack.add(new Point(selfCoords.x + 1, selfCoords.y + (directionToPlayer.y / Math.abs(directionToPlayer.y))));
            placesToAttack.add(new Point(selfCoords.x - 1, selfCoords.y + (directionToPlayer.y / Math.abs(directionToPlayer.y))));
        } else { //if neither x nor y is bigger: 
            double rand = Math.random(); //randomize it
            if (rand > 0.5) { //half chance it attacks horizontally
                placesToAttack.add(new Point(selfCoords.x, selfCoords.y + 1)); //works same as above
                placesToAttack.add(new Point(selfCoords.x, selfCoords.y - 1));
                placesToAttack.add(new Point(selfCoords.x + (directionToPlayer.x / Math.abs(directionToPlayer.x)), selfCoords.y));
                placesToAttack.add(new Point(selfCoords.x + (directionToPlayer.x / Math.abs(directionToPlayer.x)), selfCoords.y + 1));
                placesToAttack.add(new Point(selfCoords.x + (directionToPlayer.x / Math.abs(directionToPlayer.x)), selfCoords.y - 1));
            } else { //half chance it attacks vertically
                placesToAttack.add(new Point(selfCoords.x + 1, selfCoords.y)); //works same as above
                placesToAttack.add(new Point(selfCoords.x - 1, selfCoords.y));
                placesToAttack.add(new Point(selfCoords.x, selfCoords.y + (directionToPlayer.y / Math.abs(directionToPlayer.y))));
                placesToAttack.add(new Point(selfCoords.x + 1, selfCoords.y + (directionToPlayer.y / Math.abs(directionToPlayer.y))));
                placesToAttack.add(new Point(selfCoords.x - 1, selfCoords.y + (directionToPlayer.y / Math.abs(directionToPlayer.y))));
            }
        }
        
        setPlacesToAttack(placesToAttack); //stores placesToAttack in an instance variable for later
        currentSituation = currentSituation.clone(); //make currentSituation a copy to ensure no shenanigans come around when changing it

        for (Point x : placesToAttack) { //loops through the places to attack
            currentSituation.getSpace(x).shiftDamageNextTurn(getAttackPower()); //adds damage indicators for next turn
        }
        Board temp = currentSituation.clone(); //creates a temporary board
        for (Point x : placesToAttack) { //loops through points 
            temp.getSpace(x).setIsHighlighted(true); //sets all points in temp to be highlighted
            if (temp.getSpace(x).getEntity() != null) { //if an entity is in that space: 
                temp.getSpace(x).getEntity().setColorOverride(new Color(255, 0, 255)); //highlight them too
            }
        }
        animations.add(temp); //add in the temporary board as a frame
        animations.add(currentSituation.clone()); //add in another clone of the currentSituation
        temp = currentSituation.clone(); //reuse temp for something else
        ((Enemy)temp.getSpace(selfCoords).getEntity()).setIsHighlighted(false); //unhighlight the player
        animations.add(temp); //add it as a frame

        return animations; //whoo, done
    }

    //damages all the points that it selected to damage last round
    //honestly this would be a method in Enemy, but I want to make some enemies have more unique attack animations later on
    //again, this method is shamelessly ripped straight from the swordsman class, you should really just read  that one first honestly
    public ArrayList<Board> attack(Board currentSituation) throws CloneNotSupportedException{
        ArrayList<Board> animations = new ArrayList<Board>(); //of course, the animations list
        setIsHighlighted(true); //highlight the guy who's attacking
        animations.add(currentSituation.clone()); //add that as a first frame
        for (Point x : getPlacesToAttack()) { //loop through all the places selected to be attacked in the last setUpAttack
            currentSituation.getSpace(x).enemyAttacksHere(getAttackPowerInstance()); //damage whatever is in that space
            currentSituation.getSpace(x).shiftDamageNextTurn(-1 * getAttackPowerInstance()); //take off the damage indicators
        }
        Board temp = currentSituation.clone(); //temp board to be filled with Xs
        for (Point x : getPlacesToAttack()) { //loop through places to attack
            temp.getSpace(x).setEntity(new CosmeticX(255, 0, 0)); //set up red cosmetic Xs there for cool effect
        }
        animations.add(temp); //add temp as a frame
        animations.add(currentSituation.clone()); //add normal board as a frame
        setIsHighlighted(false); //stop highlighting
        animations.add(currentSituation); //final frame where the enemy is unhighlighted

        return animations; //yayyyyy
    }

    //simple toString
    //armored knights are ||. colored appropriately. 
    public String toString() {
        if (getColorOverride() != null) { //if you have a color override: 
            return colorOverrideToAnsi() + "||"; //use it
        }
        if (getIsHighlighted()) { //if it's supposed to be highlighted: 
            return "\033[38;2;0;255;128m" + "||"; //highlight it
        } //otherwise: 
        return this.healthToColor() + "||"; //just use standard color for the health this guy's at
    }

    //give info on the armored knight
    public String inspect() {
        return "Armored Knight\nHealth: \033[38;2;255;0;0m" + getHealth() + "/" + getMaxHealth() + "\033[38;2;255;255;255m\nDamage: \033[38;2;255;0;255m"+ getAttackPower() +"\033[38;2;255;255;255m\nMoves two tiles towards you, then attacks five tiles around itself. \nThis swordsman got their hands on plate armor and a saber. Generally more dangerous than your common foe.";
    }
}
