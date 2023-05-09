//oh god I have to comment all of this, don't i?
//i've forgotten to comment it all up until now
//well, time to suffer

//we need all of these
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

class GameRunner {
    //runner class, simple
    //has public static void main in it
    //deals with all the user-side stuff

    //sometimes, the player is replaced by a cosmeticX for a frame, and the clearAndPrintUI function can't acess their health/maxHealth if they're noth there
    //therefore, the se two variables store the health/max health the player had last frame so that the method can keep working
    private static int lastPlayerHealth;
    private static int lastPlayerMaxHealth;

    //action points and movement points probably could be in playerStats, but it's easier for them to just be here, sue me. 
    private static int actionPoints;
    private static int movementPoints;

    //Ahhh, Main.
    //you know what this does. This runs the entire thing. Connects all the disparate parts to make a working game. 
    //Currently, it's set up to run three different arena battles in succession, but eventually (after I submit this for the final) this will randomly generate scenarios
    public static void main(String[] args) throws CloneNotSupportedException, InterruptedException{
        //set up all the necesary things
        Ability[] tempAbilityList = {new Spear(), new MagicMissile()}; //list of abilities to go into playerStats
        PlayerStatsTracker stats = new PlayerStatsTracker(15, 15, 3, 3, tempAbilityList); //initialize playerStats
        Scanner sc = new Scanner(System.in); //scanner, for inputs

        //clear terminal
        System.out.print("\033[H\033[2J\033[38;2;255;255;255m"); 
        System.out.flush(); 
        //intro text
        System.out.println("You are a poor Roman farmer in the year 150 A.D. struggling to make ends meet.");
        System.out.println("You have heard stories of commoners becoming famous gladiators in the Colosseum, not far from where you live.");
        System.out.println("You decide that striving for glory in the Colosseum is your best shot at attaining wealth and fame, so you sign up for a battle.");
        System.out.println("Fight everyone you see, become the last man standing, and attain eternal glory. Good luck!");
        System.out.println("Type anything to continue.");
        sc.nextLine(); //waits until the user inputs something

        //clear terminal
        System.out.print("\033[H\033[2J\033[38;2;255;255;255m"); 
        System.out.flush();

        System.out.println("Round 1/3");
        Thread.sleep(1500); //sleep for 1.5 seconds

        Board playingField = new Board(11, 7); //make a new board
        playingField.addEntityToSpace(new Player(stats), 3, 3); //add player
        
        //add enemies
        playingField.addEntityToSpace(new Swordsman(), 6, 1);
        playingField.addEntityToSpace(new Swordsman(), 6, 5);
        playingField.addEntityToSpace(new Swordsman(), 8, 3);

        playBoard(playingField, stats, sc); //run the battle

        //reset mana after battle is done
        stats.setMana(3);

        //reset terminal
        System.out.print("\033[H\033[2J\033[38;2;255;255;255m"); 
        System.out.flush();

        System.out.println("Round 2/3");
        Thread.sleep(1500); //wait for 1.5 seconds

        playingField = new Board(11,7); //new board
        playingField.addEntityToSpace(new Player(stats), 3, 3); //add player

        //add enemies
        playingField.addEntityToSpace(new Swordsman(), 1, 1);
        playingField.addEntityToSpace(new Swordsman(), 1, 5);
        playingField.addEntityToSpace(new Swordsman(), 5, 3);
        playingField.addEntityToSpace(new ArmoredKnight(), 7, 1);
        playingField.addEntityToSpace(new ArmoredKnight(), 7, 5);

        playBoard(playingField, stats, sc); //run the battle

        stats.setMana(3); //reset mana after the battle

        //reset terminal
        System.out.print("\033[H\033[2J\033[38;2;255;255;255m"); 
        System.out.flush();

        System.out.println("Round 3/3");
        Thread.sleep(1500); //wait for 1.5 seconds

        playingField = new Board(11,7); //new board
        playingField.addEntityToSpace(new Player(stats), 5, 3); //add player

        //add enemies
        playingField.addEntityToSpace(new ArmoredKnight(), 2, 2);
        playingField.addEntityToSpace(new ArmoredKnight(), 2, 4);
        playingField.addEntityToSpace(new ArmoredKnight(), 8, 2);
        playingField.addEntityToSpace(new ArmoredKnight(), 8, 4);
        playingField.addEntityToSpace(new Swordsman(), 1, 3);
        playingField.addEntityToSpace(new Swordsman(), 9, 3);

        playBoard(playingField, stats, sc); //run the battle

        //you won!
        //victory screen
        //clear terminal
        System.out.print("\033[H\033[2J\033[38;2;255;255;255m"); 
        System.out.flush();
        System.out.println("Congratulations! You have beaten all the arena's challengers and emerged victorious.\nTHE END.");
        
        sc.close();
    }

    //converts a string of compass directions into a Point with that direction
    public static Point stringToVector(String str) {
        Point coords = new Point(); //will contain the direction
        str = str.toUpperCase(); //just in case the user entered lowercase
        if (str.equals("N")) { //if it's north:
            coords.setLocation(0, -1); //point that would make you go up
        } else if (str.equals("S")) { //if it's south:
            coords.setLocation(0, 1); //point aiming down
        } else if (str.equals("E")) { //etc, etc for every compass direction
            coords.setLocation(1, 0);
        } else if (str.equals("W")) {
            coords.setLocation(-1, 0);
        } else if (str.equals("NE")) {
            coords.setLocation(1, -1);
        } else if (str.equals("NW")) {
            coords.setLocation(-1, -1);
        } else if (str.equals("SE")) {
            coords.setLocation(1, 1);
        } else if (str.equals("SW")) {
            coords.setLocation(-1, 1);
        } //if it was none of them, coords remains as the default Point value: (0,0)
        return coords; //return it
    }

    //This, as the name might state, clears the terminal and prints the UI. Simple stuff. 
    public static void clearAndPrintUI(Board b, PlayerStatsTracker stats) {
        //clears the terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        //prints out player health info and whether they will take damage next turn
        //sometimes, a player is not present in the board, so it has to fall back on what the previous health values of the player were. 
        try { //if there is no player, the error is caught
            System.out.print("\033[38;2;255;255;255mHealth: \033[38;2;255;0;0m" + b.getPlayerSpace().getEntity().getHealth() + "/" + b.getPlayerSpace().getEntity().getMaxHealth()); //print out player health / maxHealth
            if (b.getPlayerSpace().getDamageNextTurn() != 0) { //if in danger next turn: 
                System.out.print("\033[38;2;128;128;255m   !!!IN DANGER!!! Will take " + b.getPlayerSpace().getDamageNextTurn() + " damage soon if not moved!"); //print that too
            }
            //now, if it hasn't thrown an exception yet, there should be a player in the board
            //therefore, store its health and maxHealth just in case there isn't one next frame. 
            lastPlayerHealth = b.getPlayerSpace().getEntity().getHealth(); //store health
            lastPlayerMaxHealth = b.getPlayerSpace().getEntity().getMaxHealth(); //store maxHealth
        } catch (NullPointerException e) { //if the player does not exist (ex. covered up by a cosmetic X in this particular frame)
            System.out.print("\033[38;2;255;255;255mHealth: \033[38;2;255;0;0m" + lastPlayerHealth + "/" + lastPlayerMaxHealth); //print the health it had last frame
        }
        System.out.println(); //next line
        System.out.println("\033[38;2;255;255;255mMana: \033[38;2;0;0;255m" + stats.getMana() +"/" + stats.getMaxMana()); //print out mana stats
        System.out.println("\033[38;2;255;255;255mAction points: \033[38;2;0;255;0m" + actionPoints + "/3"); //print out action points remaining
        System.out.println("\033[38;2;255;255;255mMovement points: \033[38;2;0;255;255m" + movementPoints); //print out movement points remaining
        
        b.printBoard(); //print out the actual game board itself to cap it all off
    }

    //this method is called when an item is used
    //it makes sure that the item's exceptions are handled properly, the player has enough action points/mana, etc.
    public static void useItem(int index, Board playingField, PlayerStatsTracker stats, Scanner sc) throws CloneNotSupportedException, InterruptedException{
        String input; //will contain all the sc.nextLine()s
        if (actionPoints > 0) { //make sure you have an action point to use
            if (stats.getMana() >= stats.getAbilities()[index].getManaUsage()) { //make sure that you have enough mana to use it
                //confirmed, you are allowed to use the ability
                //print out how it works and how to use
                clearAndPrintUI(playingField, stats);
                System.out.println(stats.getAbilities()[index].description());
                System.out.println(stats.getAbilities()[index].howToUse());
                System.out.println("Input \"X\" to cancel.");

                //loop until a good input is attained
                while (true) {
                    input = sc.nextLine(); //get input
                    input.replaceAll(" ", ""); //remove spaces
                    if (input.toUpperCase().equals("X")) { //if it's an X meaning cancel:
                        break; //get out of the while loop and the function altogether
                    } else { //if it isn't a cancel:
                        try { //attempt to have the ability run with that input
                            //make it do its thing
                            ArrayList<Board> animations = stats.getAbilities()[index].doThing(input, playingField, stats);
                            //if the input was bad, it should have errored and been caught by now. 
                            actionPoints--; //remove an action point for using an action
                            for (Board x : animations) { //loop through the animations produced by attacking:
                                //print out each frame every 1/4 second
                                clearAndPrintUI(x, stats);
                                Thread.sleep(250);
                            }
                            dieIfDead(playingField);//make sure that if the player somehow offed themself in this time, they lose
                            break; //get out of the function
                        } 
                        catch (AbilityInputDoesNotParseException e) { //if the input ends up being bad for the ability in question:
                            //tell them what went wrong, go back to the start of the loop and try again
                            clearAndPrintUI(playingField, stats);
                            System.out.println(stats.getAbilities()[index].wrongUseMessage());
                            System.out.println("Try again, or type \"X\" to cancel");
                        }
                    }
                }
            } else { //if you don't have enough mana:
                //it tells you, and exits the function after you input something
                clearAndPrintUI(playingField, stats);
                System.out.println("This ability requires "+ stats.getAbilities()[index].getManaUsage() +" mana to use. You can regain mana after this battle ends.");
                System.out.println("Type anything to continue.");
                sc.nextLine();
            }
        } else { //if you don't have enough action points: 
            //it tells you, and exits the function after you input something
            clearAndPrintUI(playingField, stats); 
            System.out.println("You have no action points remaining to use this ability. You must end your turn to gain more.");
            System.out.println("Type anything to continue.");
            sc.nextLine();
        }
    }

    //method to check if the player is dead
    //if they are, call a method that throws an error
    public static void dieIfDead(Board b) {
        if (b.getPlayerSpace().getEntity().getHealth() <= 0) { //if the player has 0 or less health: 
            lolYouActuallyDiedLMAO(); //die
        }
    }

    //this method does very little, but I find it funny when it pops up in the error hirearchy when you lose. So it stays.
    //its only porpose is to be funny when the error message pops up
    public static void lolYouActuallyDiedLMAO() {
        throw new YouAreActualGarbageException("\"oof ow ouchie\" <-- you right now"); //throw an error
    }

    //this takes a board and runs until either the player dies or they kill all the enemies
    //this is where the main batch of gameplay is done
    public static void playBoard(Board playingField, PlayerStatsTracker stats, Scanner sc) throws CloneNotSupportedException, InterruptedException{
        String input; //will take all sc.nextInt()s
        while (playingField.enemiesRemain()) { //this while loop repeats until the board is cleared of enemies
            //every repetition of the while loop is one turn for the player
            actionPoints = 3; ///you get 3 action points every turn
            movementPoints = 0; //you start with 0 movement points every turn but you can get more by spending action points
            while (true) { //this while loop repeats until the player's turn is over
            //every repetition of this while loop is one action that the player does during their turn
                //print out UI and options for things to do
                clearAndPrintUI(playingField, stats);
                System.out.println("Choose your action");
                System.out.println("1. Spear");
                System.out.println("2. Magic Missile");
                System.out.println("3. Gain Movement Points");
                System.out.println("4. End turn");
                System.out.println("5. Inspect a Space");
                System.out.println("6. Help");
                System.out.println("Type any cardinal or ordinal direction to move (ex. NW for northwest, S for south)");
                input = sc.nextLine();
                input = input.replaceAll(" ", "");
                if (input.equals("1")) { //Using item 1
                    useItem(0, playingField, stats, sc); //use first item in list in stats
                } else if (input.equals("2")) { //using item 2
                    useItem(1, playingField, stats, sc); //use second item in list in stats
                } else if (input.equals("3")) { //Gain Movement Points
                    if (actionPoints > 0) { //if you have an action point to spare: 
                        actionPoints--; //remove that action point
                        movementPoints += 3; //add 3 movement points for your troubles
                    } else { //if you have no action points: 
                        //tell them, then continue
                        clearAndPrintUI(playingField, stats);
                        System.out.println("You have no action points remaining to convert into movement points. You must end your turn to gain more. ");
                        System.out.println("Type anything to continue.");
                        sc.nextLine();
                    }
                } else if (input.equals("4")) { //end turn
                    break; //breaks out of the while loop for looping through player actions, gets down to the enemy actions
                } else if (input.equals("5")) { //inspect a space
                    //ask them to choose where to inspect
                    clearAndPrintUI(playingField, stats);
                    System.out.println("Please input the coordinates of the space you wish to inspect. \nFor example, the top-left corner would be \"1,1\", the point on the fourth column and the third row would be \"4,3\", etc");
                    System.out.println("Input \"X\" to cancel.");
                    //loops until the player either cancels or gives a proper input:
                    while (true) {
                        //get input
                        input = sc.nextLine();
                        input = input.replaceAll(" ", ""); //clean input of spaces
                        input = input.toUpperCase(); //make sure its all uppercase
                        if (input.equals("X")) { //if it's an X
                            break; //they wanted to cancel, break out of this while loop and return to the player's turn
                        } else if (input.indexOf(",") != -1){ //if there actually is a comma present, as is necesary:
                            try { //attempt to parse parts of input as integers, as they should be
                                Point coords = new Point(Integer.parseInt(input.substring(0, input.indexOf(","))), Integer.parseInt(input.substring(input.indexOf(",") + 1))); //parse string into point
                                //that line above is a risk of a nullFormatException, caught below. 
                                try { //attempt to use those coordinates on a space
                                    playingField.getSpace(coords).makeInspectingColor();
                                    //above line has risk of nullPointerException, caught below
                                    //at this point, if it was going to error, it would have
                                    clearAndPrintUI(playingField, stats); //clear and print out board with newly highlighted square
                                    System.out.println(playingField.getSpace(coords).inspect()); //print inspection text for that space
                                    System.out.println("Type anything to continue.");
                                    sc.nextLine(); //wait to continue
                                    playingField.getSpace(coords).clearInspectingColor(); //make sure to strip the color off the tile before inspection ends
                                    break; //breaks out of while loop, player's turn continues
                                } catch (ArrayIndexOutOfBoundsException e) {  //if the coords do not appear on the board:
                                    //tell them of their silliness
                                    clearAndPrintUI(playingField, stats);
                                    System.out.println("Coordinate given does not land on the array, try again. \nFor example, the top-left corner would be \"1,1\", the point on the fourth column and the third row would be \"4,3\", etc");
                                    System.out.println("Input \"X\" to cancel.");
                                    //go to top of while loop, get another input to try
                                }
                            } catch (NumberFormatException e) { //when the parse into a Point goes wrong
                                //tell them so
                                clearAndPrintUI(playingField, stats);
                                System.out.println("Unable to parse given numbers, try again. \nFor example, the top-left corner would be \"1,1\", the point on the fourth column and the third row would be \"4,3\", etc");
                                System.out.println("Input \"X\" to cancel.");
                                //go to top of while loop, get another input to try
                            }
                        } else { //if it is not a cancel and there is no comma present:
                            //tell them they goofed
                            clearAndPrintUI(playingField, stats);
                            System.out.println("No comma detected, try again. \nFor example, the top-left corner would be \"1,1\", the point on the fourth column and the third row would be \"4,3\", etc");
                            System.out.println("Input \"X\" to cancel.");
                            //go to top of while loop, get another input to try
                        }
                    }
                } else if (input.equals("6")) { //help
                    //long and lengthy guide on how the game works
                    clearAndPrintUI(playingField, stats);
                    System.out.println("This is an arena battler game. Your objective is to kill all the enemies while losing as little health as possible. ");
                    System.out.println("Your player looks like \033[38;2;0;255;255m[]\033[38;2;255;255;255m. The walls of the arena look like \033[38;2;170;170;170m##\033[38;2;255;255;255m. Blank spaces on the board are represented by \033[38;2;0;0;0m<>\033[38;2;255;255;255m. Most other things are enemies.");
                    System.out.println("Enemies get more red as their health decreases. For example, a swordsman at full health will look like {}, but a swordsman on the brink of death will look like \033[38;2;255;0;0m{}\033[38;2;255;255;255m.");
                    System.out.println("Every turn you are allotted three action points, which can either be spent on movement or on abilities. You can regain action points by ending your turn.");
                    System.out.println("Spending one action point on movement will get you three movement points, which can be spent to move one tile in any straight or diagonal direction. ");
                    System.out.println("Spending an action point on an ability will cause its effects. Abilities' effects can vary wildly, but the most common effect is to damage enemies in a certain area.");
                    System.out.println("The first time you end your turn in a battle, every enemy will move towards you and set up an attack against you.");
                    System.out.println("Every subsequent time you end your turn, they will execute the attacks they set up last turn, then move and set up more for the next turn.");
                    System.out.println("Spaces that look like \033[38;2;128;0;128m<>\033[38;2;255;255;255m are spaces that will be attacked at the end of your turn. If the player looks like \033[38;2;128;128;255m[]\033[38;2;255;255;255m, that means that you will take damage if you end your turn there.");
                    System.out.println("Some abilities cost mana to use, which replenishes when you beat a stage. Health does not replenish. If you lose health, that health cannot be recovered, even between stages.");
                    System.out.println("Inspecting a tile is very useful. It does not require an action point, and can tell you a lot about the entity on that tile and their behaviors.");
                    System.out.println("If you inspect an empty tile, it will tell you how much damage you would sustain if you ended your turn there. If you are in an impossible situation, this can help reduce losses.");
                    System.out.println("Enter anything to continue.");
                    sc.nextLine(); //wait for input before proceeding
                } else if (!(stringToVector(input).equals(new Point()))) { //if the input is a compass direction (means they want to move the player):
                    if (movementPoints > 0) { //if they have enough movementPoints: 
                        Point wayToMove = stringToVector(input); //get which way they should move
                        if (playingField.getPlayerSpace().getEntity().canMove(playingField, wayToMove)) { //make sure they can move
                            movementPoints--;
                            playingField = playingField.getPlayerSpace().getEntity().move(playingField, wayToMove);
                        } else { //if they can't move, 
                            //inform them they are the silliest goose
                            clearAndPrintUI(playingField, stats);
                            System.out.println("You cannot move that way, there is an obstruction. Try using an action point to gain more movement points.");
                            System.out.println("Type anything to continue.");
                            sc.nextLine();
                        }
                    } else { //if you don't have enough move points
                        //tell em how to get more
                        clearAndPrintUI(playingField, stats);
                        System.out.println("You have no movement points to use. Try using an action point to gain more movement points.");
                        System.out.println("Type anything to continue.");
                        sc.nextLine();
                    }
                }
            }
            //PLAYER TURN IS OVER
            //ENEMY TURN TIME
            
            //find an arrayList of all the coordinates of the enemies
            ArrayList<Point> enemyCoords = playingField.findAllEnemyLocations();
            for (Point p : enemyCoords) { //loop through them all
                try { //have to have a try-catch b/c sometimes the placesToAttack is null
                    if (((Enemy) playingField.getSpace(p).getEntity()).getPlacesToAttack().size() != 0) { //if the placesToAttack contains at least one place:
                        ArrayList<Board> anims = ((Enemy) playingField.getSpace(p).getEntity()).attack(playingField); //have them attack the places they set up last turn
                        for (Board b : anims) { //play all the attacking animations
                            clearAndPrintUI(b, stats);
                            Thread.sleep(250); //1/4 second delay between each frame
                        }
                    }
                } catch (NullPointerException e) {} //if there are no places to attack, just do nothing for this guy
            }
            //if the player was killed when the enemies attacked: 
            dieIfDead(playingField); //do what must be done

            for (Point p : enemyCoords) { //loop through enemy coords again
                ArrayList<Board> anims = ((Enemy) playingField.getSpace(p).getEntity()).moveAndSetUpAttack(playingField); //enemies move towards player and rear up attacks for next turn
                for (Board b : anims) { //play all animations for each enemy
                    clearAndPrintUI(b, stats);
                    Thread.sleep(250); //1/4 second delay between frames
                }
                playingField = anims.get(anims.size()-1); //set the playing field to the last frame every time
            }
            //TURN OVER
            //If no enemies remain after this, the method stops
            //if there are still enemies, it goes back to the player's turn
        }
    }

}