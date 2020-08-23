import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriortyQueue{
    /** Collects the pixels. */
    private TwoDArray[] arr;
    /** Comparator object used to go to compare methods. */
    Comparator<TwoDArray> comparator = null;
    /** Current size of the queue. */
    private int size = 0;
    /** Current capacity of the queue. */
    private int capacity =10;

    /** Default constructor initializes the array. */
    public PriortyQueue(){
        arr=new TwoDArray[capacity];
    }

    /**
     * Creates a heap-based priority queue with the specified initial
     * capacity that orders its elements according to the specified
     * comparator.
     * @param capacity The initial capacity for this priority queue
     * @param comp The comparator used to order this priority queue
     * @throws IllegalArgumentException if capacityis less than 1
     */
    public PriortyQueue(int capacity, Comparator<TwoDArray> comp) {
        if (capacity< 1) {
            throw new IllegalArgumentException();
        }
        arr = new TwoDArray[capacity+ 1];
        comparator = comp;
    }

    /**
     * Insert an item into the priority queue.
     * @pre The ArrayList arr is in heap order.
     * @post The item is in the priority queue and
     *       arr is in heap order.
     * @param item The item to be inserted
     * @throws NullPointerException if the item to be inserted is null.
     */
    public boolean offer(TwoDArray item) {
        // Add the item to the heap.
        add(item);
        // child is newly inserted item.
        int child = size-1;
        int parent = (child - 1) / 2; // Find childs parent.
        // Reheap
        while (parent >= 0&& comparator.compare(arr[parent],
                arr[child]) < 0 ) {
            swap(parent, child);
            child = parent;
            parent = (child - 1) / 2;
        }
        return true;
    }

    //HELPER METHOD
    /**
     *Adds the pixels to array.
     * @param item which is added
     * @return if add is successful returns true
     */
    public boolean add(TwoDArray item) {
        if (size == capacity) {
            reallocate();
        }
        arr[size] = item;
        size++;
        return true;
    }
    //HELPER METHOD
    /** Method to reallocate the array containing the queue data
     Postcondition: The size of the data array has been doubled
     and all of the data has been copied to the new array
     */
    private void reallocate() {
        TwoDArray[] temp = new TwoDArray[2 * capacity];
        capacity*=2;
        System.arraycopy(arr, 0, temp, 0, arr.length);
        arr = temp;
    }

    /**
     * Remove an item from the priority queue
     * @pre The ArrayList arr is in heap order.
     * @post Removed smallest item, arr is in heap order.
     * @return The item with the smallest priority value or null if empty.
     */
    public TwoDArray poll() {
        if (isEmpty()) {
            return null;
        }
        // Save the top of the heap.
        TwoDArray result = arr[0];
        // If only one item then remove it.
        if (arr.length == 1)
            remove_element(0);

        // Remove the last item from the array and place it into
        // the first position.
        set_element(0, remove_element(size - 1));
        // The parent starts at the top.
        int parent = 0;
        while (true) {
            int leftChild = 2 * parent + 1;
            if (leftChild >= arr.length) {
                break; // Out of heap.
            }

            int rightChild = leftChild + 1;
            int maxChild = leftChild; // Assume leftChild is bigger.
            // See whether rightChild is smaller.

            if (rightChild < size && comparator.compare(arr[leftChild],
                    arr[rightChild]) < 0) {
                maxChild = rightChild;
            }

            //If the element of mixChilds index null, break the loop
            if (arr[maxChild] == null) {
                break; // Out of heap.
            }

            // Move bigger child up heap if necessary.
            if (comparator.compare(arr[parent],
                    arr[maxChild]) < 0) {
                swap(parent, maxChild);
                parent = maxChild;
            } else { // Heap property is restored.
                break;
            }
        }
        size--;
        return result;
    }

    //HELPER METHOD

    /**
     * This method same as poll method. There is a difference: If array is empty throws NoSuchElementException.
     * @return removed element.
     */
    public TwoDArray remove() {
        if (isEmpty())
            throw new NoSuchElementException();

        return poll();
    }

    /**
     * Returns the top of queue without removing
     * If array is empty returns null
     * @return the data which is on the top
     */
    public TwoDArray peek(){
        if(isEmpty())
            return null;
        return arr[0];
    }

    /**
     * Returns the top of queue without removing
     * If array is empty throws NoSuchElementException
     * @return the data which is on the top
     */
    public TwoDArray element(){
        if(isEmpty())
            throw new NoSuchElementException();
        return arr[0];
    }

    //HELPER METHOD

    /**
     * removes the element which in specified index
     * @param index which is specified index
     * @return removed element
     */
    public TwoDArray remove_element(int index) {
        //System.out.println(index);
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        TwoDArray returnValue = arr[index];
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }
        return returnValue;
    }

    //HELPER METHOD

    /**
     * Sets the the specified index
     * @param index that sets
     * @param item that sets
     * @return the element which sets
     */
    public TwoDArray set_element(int index, TwoDArray item) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        TwoDArray oldValue = arr[index];
        arr[index] = item;
        return oldValue;
    }

    //HELPER METHOD
    /**
     * Contols the array whether is empty
     * @return true if it is empty
     */
    public boolean isEmpty(){
        return size==0;
    }

    //HELPER METHOD
    /**
     * Swaps the elements in the specified index
     * @param obj1 first object that swaps
     * @param obj2 second object that swaps
     */
    public void swap(int obj1, int obj2)
    {
        TwoDArray temp = arr[obj1];
        arr[obj1] = arr[obj2];
        arr[obj2] = temp;
    }

    public TwoDArray[] getArr(){
        return arr;
    }
    public int getSize(){
        return size;
    }

}
