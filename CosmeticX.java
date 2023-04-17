public class CosmeticX extends Entity{
    //This class is just for looksies
    //whenever an enemy/player attacks, these X's will appear on the spaces that were attacked for one frame, cool effect imo

    //X's will be colored uniquely (because y'know, cosmetics?):
    private int red;
    private int green;
    private int blue;

    public CosmeticX() {
        red = 255;
        green = 255;
        blue = 255;
    }

    //getters and setters:
    public int getRed() {return red;}
    public int getGreen() {return green;}
    public int getBlue() {return blue;}

    public void setRed(int red) {this.red = red;}
    public void setGreen(int green) {this.green = green;}
    public void setBlue(int blue) {this.blue = blue;}

    //"\033[38;2;R;G;Bm" that's the ANSI escape code for changing RGB values of text in the terminal
    //method that determines ANSI escape code string from the RGB values:
    public String ansiColorCode() {
        return "\033[38;2;"+ red +";"+ green +";"+ blue +"m";
    }

    //toString that returns "X"s in the color specified
    public String toString() {
        return ansiColorCode() + "XX";
    }
}
