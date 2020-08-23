/**
 *Class that contains pixels. This class is alsa a type.
 */
public class TwoDArray {
    int red;
    int green;
    int blue;

    public TwoDArray(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    int getRed() { return red; }

    int getGreen() { return green; }

    int getBlue() { return blue; }

    @Override
    public String toString(){
        return "Red: "+getRed()+", Green: "+getGreen()+", Blue: "+getBlue();
    }




}
