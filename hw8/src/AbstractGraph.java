import java.io.IOException;
import java.util.Scanner;

/**
 * Abstract base class for MyGraphs. A MyGraph is a set
 * of vertices and a set of edges. Vertices are
 * represented by integers from 0 to n - 1. Edges
 * are ordered pairs of vertices.
 */
public abstract class AbstractGraph implements MyGraph {

    // Data Fields
    /** The number of vertices */
    private int numV;
    /** Flag to indicate whether this is a directed MyGraph */
    private boolean directed;

    public AbstractGraph(){
    }
    // Constructor
    /**
     * Construct a MyGraph with the specified number of vertices
     * and the directed flag. If the directed flag is true,
     * this is a directed MyGraph.
     * @param numV The number of vertices
     * @param directed The directed flag
     */
    public AbstractGraph(int numV, boolean directed) {
        this.numV = numV;
        this.directed = directed;
    }

    // Accessor Methods
    /**
     * Return the number of vertices.
     * @return The number of vertices
     */
    @Override
    public int getNumV() {
        return numV;
    }

    /**
     * Return whether this is a directed MyGraph.
     * @return true if this is a directed MyGraph
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    // Other Methods
    /**
     * Load the edges of a MyGraph from the data in an input file.
     * The file should contain a series of lines, each line
     * with two or three data values. The first is the source,
     * the second is the destination, and the optional third
     * is the weight.
     * @param scan that scanning object
     * @throws IOException if an I/O error occurs
     */
    public void loadEdgesFromFile(Scanner scan) throws IOException {
        while(scan.hasNext()) {
            int row = scan.nextInt();
            int column = scan.nextInt();
            double weight = 1.0;
            insert(new Edge(row, column, weight)); //inserts the edges which its row and column given the input
        }
    }

    /**
     * Factory method to create a MyGraph and load the data from an input
     * file. The first line of the input file should contain the number
     * of vertices. The remaining lines should contain the edge data as
     * described under loadEdgesFromFile.
     * @param scan The Scanner connected to the data file
     * @param isDirected true if this is a directed MyGraph,
     *                   false otherwise
     * @param type The string "Matrix" if an adjacency matrix is to be
     *             created
     * @return a graph
     * @throws IllegalArgumentException if type is not "Matrix"
     * @throws IOException if file can not open
     *
     */
    public static MyGraph createGraph(Scanner scan, boolean isDirected, String type) throws IOException {
        int numV = Integer.valueOf(scan.next());
        scan.nextLine();
        AbstractGraph returnValue = null;
        if (type.equalsIgnoreCase("Matrix")) {
            returnValue = new MatrixGraph(numV, isDirected);
        }  else {
            throw new IllegalArgumentException();
        }
        returnValue.loadEdgesFromFile(scan);
        return returnValue;
    }

    /**
     * Chech whether the matrix is transitive
     * @param i A index of martix
     * @param j A index of martix
     * @param k A index of martix
     * @return true if it is transitive
     */
    public boolean isItTransitive(int i, int j, int k){return true;}

    /**
     * After inserting matrix, it can be wrong values. This method checks whether matrix is true.
     * If there is lack, corrects.
     */
    public void insert_new(){}

    /**
     * Calculate number of populars.
     * @return the number of populars.
     */
    public int howManyPopular(){return 0;}
}

