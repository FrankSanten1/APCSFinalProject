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
    public static void main(String[] args) throws CloneNotSupportedException, InterruptedException{
        //set up all the necesary things
        Ability[] tempAbilityList = {new Spear(), new MagicMissile()}; //list of abilities to go into playerStats
        PlayerStatsTracker stats = new PlayerStatsTracker(15, 15, 3, 3, tempAbilityList); //initialize playerStats
        Scanner sc = new Scanner(System.in); //scanner, for inputs

        
        System.out.print("\033[H\033[2J\033[38;2;255;255;255m"); //make initial text white
        System.out.flush();
        System.out.println("Round 1/3");

        Thread.sleep(1500);

        Board playingField = new Board(11, 7);
        playingField.addEntityToSpace(new Player(stats), 3, 3);
        playingField.addEntityToSpace(new Swordsman(), 6, 1);
        playingField.addEntityToSpace(new Swordsman(), 6, 5);
        playingField.addEntityToSpace(new Swordsman(), 8, 3);

        playBoard(playingField, stats, sc);
        
        sc.close();
    }

    public static Point stringToVector(String str) {
        Point coords = new Point(); /* x, y */
        str = str.toUpperCase();
        if (str.equals("N")) {
            coords.setLocation(0, -1);
        } else if (str.equals("S")) {
            coords.setLocation(0, 1);
        } else if (str.equals("E")) {
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
        }
        return coords;
    }

    public static void clearAndPrintUI(Board b, PlayerStatsTracker stats) {
        //clears the terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        //prints out player health info and whether they will take damage next turn
        try {
            System.out.print("\033[38;2;255;255;255mHealth: \033[38;2;255;0;0m" + b.getPlayerSpace().getEntity().getHealth() + "/" + b.getPlayerSpace().getEntity().getMaxHealth());
            if (b.getPlayerSpace().getDamageNextTurn() != 0) {
                System.out.print("\033[38;2;128;128;255m   !!!IN DANGER!!! Will take " + b.getPlayerSpace().getDamageNextTurn() + " damage soon if not moved!");
            }
            lastPlayerHealth = b.getPlayerSpace().getEntity().getHealth();
            lastPlayerMaxHealth = b.getPlayerSpace().getEntity().getMaxHealth();
        } catch (NullPointerException e) {
            System.out.print("\033[38;2;255;255;255mHealth: \033[38;2;255;0;0m" + lastPlayerHealth + "/" + lastPlayerMaxHealth);
        }
        System.out.println();
        System.out.println("\033[38;2;255;255;255mMana: \033[38;2;0;0;255m" + stats.getMana() +"/" + stats.getMaxMana());
        System.out.println("\033[38;2;255;255;255mAction points: \033[38;2;0;255;0m" + actionPoints + "/3");
        System.out.println("\033[38;2;255;255;255mMovement points: \033[38;2;0;255;255m" + movementPoints);
        

        b.printBoard();
    }

    public static void useItem(int index, Board playingField, PlayerStatsTracker stats, Scanner sc) throws CloneNotSupportedException, InterruptedException{
        String input;
        if (actionPoints > 0) {
            if (stats.getMana() >= stats.getAbilities()[index].getManaUsage()) {
                clearAndPrintUI(playingField, stats);
                System.out.println(stats.getAbilities()[index].description());
                System.out.println(stats.getAbilities()[index].howToUse());
                System.out.println("Input \"X\" to cancel.");
                while (true) {
                    input = sc.nextLine();
                    input.replaceAll(" ", "");
                    if (input.toUpperCase().equals("X")) {
                        break;
                    } else {
                        try {
                            ArrayList<Board> animations = stats.getAbilities()[index].doThing(input, playingField, stats);
                            actionPoints--;
                            for (Board x : animations) {
                                clearAndPrintUI(x, stats);
                                Thread.sleep(250);
                            }
                            dieIfDead(playingField);
                            break;
                        } 
                        catch (AbilityInputDoesNotParseException e) {
                            clearAndPrintUI(playingField, stats);
                            System.out.println(stats.getAbilities()[index].wrongUseMessage());
                            System.out.println("Try again, or type \"X\" to cancel");
                        }
                    }
                }
            } else {
                clearAndPrintUI(playingField, stats);
                System.out.println("This ability requires "+ stats.getAbilities()[index].getManaUsage() +" mana to use. You can regain mana after this battle ends.");
                System.out.println("Type anything to continue.");
                sc.nextLine();
            }
        } else {
            clearAndPrintUI(playingField, stats);
            System.out.println("You have no action points remaining to use this ability. You must end your turn to gain more.");
            System.out.println("Type anything to continue.");
            sc.nextLine();
        }
    }

    public static void dieIfDead(Board b) {
        if (b.getPlayerSpace().getEntity().getHealth() <= 0) {
            lolYouActuallyDiedLMAO();
        }
    }

    public static void lolYouActuallyDiedLMAO() {
        throw new YouAreActualGarbageException("\"oof ow ouchie\" <-- you right now");
    }

    public static void playBoard(Board playingField, PlayerStatsTracker stats, Scanner sc) throws CloneNotSupportedException, InterruptedException{
        String input;
        while (playingField.enemiesRemain() && playingField.getPlayerSpace().getEntity().getHealth() > 0) { //this while loop repeats until the board is cleared of enemies
            actionPoints = 3;
            movementPoints = 0;
            while (true) { //this while loop repeats until the player's turn is over
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
                if (input.equals("1")) {
                    useItem(0, playingField, stats, sc);
                } else if (input.equals("2")) {
                    useItem(1, playingField, stats, sc);
                } else if (input.equals("3")) {
                    if (actionPoints > 0) {
                        actionPoints--;
                        movementPoints += 3;
                    } else {
                        clearAndPrintUI(playingField, stats);
                        System.out.println("You have no action points remaining to convert into movement points. You must end your turn to gain more. ");
                        System.out.println("Type anything to continue.");
                        sc.nextLine();
                    }
                } else if (input.equals("4")) {
                    break;
                } else if (input.equals("5")) {
                    clearAndPrintUI(playingField, stats);
                    System.out.println("I haven't added this yet. Seethe.");
                    System.out.println("Type anything to continue.");
                    sc.nextLine();
                } else if (input.equals("6")) {
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
                    sc.nextLine();
                } else if (!(stringToVector(input).equals(new Point()))) {
                    if (movementPoints > 0) {
                        movementPoints--;
                        playingField = playingField.getPlayerSpace().getEntity().move(playingField, stringToVector(input));
                    } else {
                        clearAndPrintUI(playingField, stats);
                        System.out.println("You have no movement points to use. Try using an action point to gain more movement points.");
                        System.out.println("Type anything to continue.");
                        sc.nextLine();
                    }
                }
            }
            
            ArrayList<Point> enemyCoords = playingField.findAllEnemyLocations();
            for (Point p : enemyCoords) {
                try {
                    if (((Enemy) playingField.getSpace(p).getEntity()).getPlacesToAttack().size() != 0) {
                        ArrayList<Board> anims = ((Enemy) playingField.getSpace(p).getEntity()).attack(playingField);
                        for (Board b : anims) {
                            clearAndPrintUI(b, stats);
                            Thread.sleep(250);
                        }
                    }
                } catch (NullPointerException e) {} //if there are no places to attack, just do nothing for this guy
            }
            dieIfDead(playingField);
            for (Point p : enemyCoords) {
                ArrayList<Board> anims = ((Enemy) playingField.getSpace(p).getEntity()).moveAndSetUpAttack(playingField);
                for (Board b : anims) {
                    clearAndPrintUI(b, stats);
                    Thread.sleep(250);
                }
                playingField = anims.get(anims.size()-1);
            }
        }
    }

}