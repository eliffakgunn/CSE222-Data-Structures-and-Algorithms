import java.util.Comparator;

/**
 * Class that comparates the pixels according to their euclid norms
 */
public class PQEUC implements Comparator<TwoDArray> {
PriortyQueue q= new PriortyQueue();

    @Override
    public int compare(TwoDArray obj1, TwoDArray obj2) {
        double res1=Math.sqrt(Math.pow(obj1.red,2) + Math.pow(obj1.green,2) + Math.pow(obj1.blue,2));
        double res2=Math.sqrt(Math.pow(obj2.red,2) + Math.pow(obj2.green,2) + Math.pow(obj2.blue,2));

        if(res1 == res2)
            return 0;
        else if(res1 < res2 )
            return -1;
        else
            return 1;
    }
}
