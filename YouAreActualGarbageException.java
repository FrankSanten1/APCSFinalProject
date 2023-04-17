public class YouAreActualGarbageException extends RuntimeException{
//funni class
//throw a custom error when the player dies
//not a bug, it's a feature, t r u s t
//hopefully Mr. J finds this as funny as I do
//technically, it is an error in my code, but it's intentional
//hopefully it's allowed

    public YouAreActualGarbageException(String s) {
        super(s); //asks for a RuntimeException named "YouAreActualGarbageException" to be thrown, with the explanation text as whatever s is
    }
}
