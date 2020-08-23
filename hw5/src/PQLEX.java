import java.util.Comparator;

/**
 * Class that makes lexicographical comparison from discrete math
 */
public class PQLEX implements Comparator<TwoDArray> {

    @Override
    public int compare(TwoDArray obj1, TwoDArray obj2) {
        if(obj1.red > obj2.red){
            return 1;
        }
        else if(obj1.red == obj2.red){
            if(obj1.green > obj2.green)
                return 1;
            else if(obj1.green == obj2.green){
                if(obj1.blue > obj2.blue)
                    return 1;
                else if(obj1.blue < obj2.blue)
                    return -1;
                else
                    return 0;
            }
            else
                return -1;
        }
        else
            return -1;
    }
}
