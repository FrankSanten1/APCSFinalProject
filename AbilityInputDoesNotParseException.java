public class AbilityInputDoesNotParseException extends RuntimeException{
    //i need a way for the ability to tell the runner class if you gave it a trash input easily
    //i figure that this is a simple way to do so
    //basically, in main, since there's no one imput that is used for every ability, it has to be decided in the ability method
    //so main will start a try-catch, send the Ability the user's unfiltered input
    //and if the input isn't formatted right, then this is thrown, main catches it, and asks the user to try again. 

    public AbilityInputDoesNotParseException(String s) {
        super(s);
    }
    //there's probably a better way to do this, but I like this way, it's new and fun
}
