import java.util.Iterator;
import java.util.NoSuchElementException;

/** A MatrixGraph is an implementation of the Graph
 *   abstract class that uses an array to represent the
 *   edges.
 */

public class MatrixGraph extends AbstractGraph {

    // Data field
    /** The two dimensional array to represen an edge */
    private double[][] edges;

    public MatrixGraph(){
        super();
    }
    // Consructors
    /** Construct a graph with the specified number of
     vertices and directionality
     @param numV - The number of vertices
     @param directed - The directionality flag
     */
    public MatrixGraph(int numV, boolean directed) {
        super(numV, directed);
        edges = new double[numV][numV];
            for (int i = 0; i != numV; ++i) {
                for (int j = 0; j != i + 1; ++j) {
                    edges[i][j] = 0.0;
                }
            }
    }

    // Implementation of abstract methods
    /** Insert a new edge into the graph
     @param edge - The new edge
     */
    public void insert(Edge edge) {
        setEdgeValue(edge.getSource(), edge.getDest(), edge.getWeight());
    }

    /** Determine if an edge exists
     @param source - The source vertix
     @param dest - The destination vertix
     @return true if there is an edge from u to v
     */
    public boolean isEdge(int source, int dest) {
        return Double.POSITIVE_INFINITY != getEdgeValue(source, dest);
    }

    /** Get the edge between two vertices. If an
     edge does not exist, an Edge with a weight
     of POSITIVE_INFINITY is returned.
     @param source - The source
     @param dest - The destination
     @return the edge between these two vertices
     */
    public Edge getEdge(int source, int dest) {
        return new Edge(source, dest, getEdgeValue(source, dest));
    }

    /** Return an iterator to the edges connected
     to a given vertix.
     @param source - The source vertix
     @return an EdgeIterator to the vertices
     contcted to source
     */
    public Iterator < Edge > edgeIterator(int source) {
        return new Iter(source);
    }

    /** Method to set the value of an edge
     @param source - The source vertix
     @param dest - The destination vertix
     @param wt - The weight
     */
    private void setEdgeValue(int source, int dest, double wt) {
            edges[source-1][dest-1] = wt;
    }

    /** Method to get the value of an edge
     *  @param source - The source vertix
     *  @param dest - The destination vertix
     *  @return The weight of this edge or
     *  POSITIVE_INFINITY if no edge exists
     */
    private double getEdgeValue(int source, int dest) {
        if (isDirected() | source <= dest) {
            return edges[source][dest];
        }
        else {
            return edges[dest][source];
        }
    }

    // Iter class
    /** An iterator to the edges.  An Edge iterator is
     *  similar to an Iterator except that its
     *  next method will always return an edge
     */
    private class Iter
            implements Iterator < Edge > {
        // Data fields
        /** The source vertix for this iterator */
        private int source;

        /** The current index for this iterator */
        private int index;

        // Constructor
        /** Construct an EdgeIterator for a given vertix
         *  @param source - The source vertix
         */
        public Iter(int source) {
            this.source = source;
            index = -1;
            advanceIndex();
        }

        /** Return true if there are more edges
         *  @return true if there are more edges
         */
        public boolean hasNext() {
            return index != getNumV();
        }

        /** Return the next edge if there is one
         *  @throws NoSuchElementException - there are no
         *  more edges
         *  @return the next Edge in the iteration
         */
        public Edge next() {
            if (index == getNumV()) {
                throw new NoSuchElementException();
            }
            Edge returnValue = new Edge(source, index,
                    getEdgeValue(source, index));
            advanceIndex();
            return returnValue;
        }

        /** Remove is not implememted
         *  @throws UnsupportedOperationException if called
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        /** Advance the index to the next edge */
        private void advanceIndex() {
            do {
                index++;
            }
            while (index != getNumV() && Double.POSITIVE_INFINITY == getEdgeValue(source, index));
        }
    }

    /**
     * Prints the matrix
     */
    public void print(){
        for(int i=0; i<edges.length; ++i) {
            for (int j = 0; j < edges.length; ++j) {
                System.out.print(edges[i][j]+"  ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Chech whether the matrix is transitive
     * @param i A index of martix
     * @param j A index of martix
     * @param k A index of martix
     * @return true if it is transitive
     */
    public boolean isItTransitive(int i, int j, int k){
        if(edges[i-1][j-1] == 1.0 && edges[j-1][k-1] == 1.0) // if edges[i][j] is exist and edges[j][k] is exist, also edges[i][k] is exist.
            return true;
        return false;
    }

    /**
     * After inserting matrix, it can be wrong values. This method checks whether matrix is true.
     * If there is lack, corrects.
     */
    public void insert_new(){
        for (int i=0; i<edges.length; ++i){
            for(int j=0; j<edges.length; ++j) {
                for(int k=0; k<edges.length; ++k){
                    if (i != k && isItTransitive(i+1, j+1, k+1)) //checks whether transitive is exist
                            edges[i][k] = 1.0;
                }
            }
        }
    }

    /**
     * Calculate number of populars.
     * @return the number of populars.
     */
    public int howManyPopular() {
        int[] popular = new int[edges.length]; //collects the popular persons
        for (int i=0; i< popular.length; ++i) //initialize the array
            popular[i]=-1;

        int index = 0; //number of popular person

        for (int i = 0; i < edges.length; ++i) {
            int temp=0; //keeps people thinking that the people mentioned are popular by how many people.
            for (int j = 0; j < edges.length; ++j) {
                if (edges[j][i] == 1.0)
                    temp++;
            }

            if (temp==edges.length-1){ //people who are considered popular by every other person.
                popular[index]=i;
                index++;
            }
        }
        return index;
    }

}
