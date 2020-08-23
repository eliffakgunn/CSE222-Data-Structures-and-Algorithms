import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainTest {

    public static void main( String [] arg) throws IOException {

        Scanner file = new Scanner( new File("input.txt")); //reading input
        MyGraph graph;
        graph = new MatrixGraph();
        graph=AbstractGraph.createGraph(file,true,"Matrix"); //creating graph

        for(int i=0; i<graph.getNumV(); ++i){ //If there is lack, corrects.
            graph.insert_new();
        }

        System.out.println("\nMatrix of people:");
        graph.print();
        System.out.println("Number of people who are considered popular by every other person:\n"+graph.howManyPopular());
    }
}
