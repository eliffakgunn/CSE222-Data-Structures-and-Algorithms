import java.util.*;

public class Word_Map implements Map, Iterable
{
    /**initial capacity*/
    final static int INITCAP=10;
    /**current capacity*/
    int CURRCAP = INITCAP;
    /**Load factor*/
    final static float LOADFACT = 0.75f;
    /**HashTable*/
    private Node table[];
    /**head node, first node*/
    private Node head=null;

    /** The number of keys */
    private int numKeys;

    /**No parameter constructor initialize the table*/
    public Word_Map() {
        this.table = new Node[INITCAP];
    }

    /**
     *This method provides using the Iterator methods next, hasNext and remove
     * @return type is Iterator
     */
    @Override
    public Iterator iterator() {
        return new WordIter();
    }

    /**
     * WordIter class implements Iterator class and overrides its methods
     */
    public static class WordIter implements Iterator{

        /** A reference to the next item. */
        private Node nextItem;
        /** A reference to the last item returned. */
        private Node lastItemReturned;
        /** The index of the current item. */
        private int index = 0;
        /**
         * Indicate whether movement forward is defined.
         * @return true if call to next will not throw an exception
         */
        @Override
        public boolean hasNext() {
            return nextItem != null;
        }

        /** Move the iterator forward and return the next item.
         @return The next item in the list
         @throws NoSuchElementException if there is no such object
         */
        @Override
        public String  next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.key;
        }
        public void remove() {
        }
    }
    /**Node builds block for the Word_Map*/
    static class Node {
        String key;
        File_Map value;
        Node next; //next node

        public Node(){}

        public Node(String key, File_Map value){
            this.key=key;
            this.value=value;
        }

        public Node(String key, File_Map value, Node next){
            this.key=key;
            this.value=value;
            this.next=next;
        }
        public String getKey(){
            return key;
        }
        public File_Map getValue(){
            return value;
        }

    }

    /**
     * size of map
     * @return size of map
     */
    @Override
    public int size() {
        return numKeys;
    }

    /**
     * Check whether map is empty
     * @return true if map i empty
     */
    @Override
    public boolean isEmpty() {
        return numKeys==0;
    }

    /**
     * Check whether this map contains a mapping for the specified key
     * @param key key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsKey(Object key) throws NullPointerException{
        if(key==null)
            throw new NullPointerException();
        if(hasKey((String) key)!=0 && getNode(hasKey((String)key)).value!=null) //if key contains key and this key contains a mapping
            return true;
        return false;
    }

    /**
     * Check whether this map maps one or more keys to the specified value
     * @param value value whose presence in this map is to be tested
     * @return true if this map maps one or more keys to the specified value
     * @throws NullPointerException if the specified value is null and this map does not permit null values
     */
    @Override
    /*Use linked structure instead of table index
    to perform search operation effectively
     * */
    public boolean containsValue(Object value) {
        Node node= getNode(0);
        int temp=0;
        while (true){
            if (node!=null && node.getValue()!=value)
                node=node.next;
            else{
                if (node==null){
                    temp=1;
                    break;
                }
                break;
            }
        }
        if (temp==0) //if this value mapped from a key
            return true;
        return false;
    }

    /**
     *  if this map contains a mapping from a key k to a value v,
     *  then this method returns v; otherwise it returns null.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    @Override
    public Object get(Object key) {
        // Find the first table element that is empty
        // or the table element that contains the key.
        int index = find(key);

        // If the search is successful, return the value.
        if (table[index] != null)
            return table[index].value;
        else
            return null; // key not found.
    }

    /**
     * Associates the specified value with the specified key in this map (optional operation).
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @throws NullPointerException if the specified key or value is null and this map does not permit null keys or values
     */
    @Override
    /*
    Use linear probing in case of collision
    * */
    public Object put(Object key, Object value) {
        // Find the first table element that is empty
        // or the table element that contains the key.
        int index = find(key);

        Node node=getNode(0); //firs node

        if (table[index] == null) {
            if(isEmpty()){ //adds first node
                table[index]=new Node ((String) key,(File_Map) value, head); //new node
                head=table[index]; //head is table[index] now
                numKeys++;
                return null;
            }
            while(true){ //if it is not empty
                if(node.next!=null) //keep going to the last node
                    node=node.next;
                else{
                    table[index] = new Node ((String) key,(File_Map) value,node.next); //create new node
                    node.next=table[index];
                    numKeys++;
                    break;
                }
            }
            // Check whether rehash is needed.
            double loadFactor =(double) (numKeys ) / table.length;
            if (loadFactor > LOADFACT)
                rehash();
            return null;
        }
        else{ //table[index] is not null
            if(hasKey((String) key)!=0){ //if this key is exist
                return ((File_Map) value).getList();
            }
            else{ //if this key is not exist
                int temp=index+1;
                while (true){
                    if(table[temp]!=null) //keep going to find empty index
                        temp++;
                    else
                        break;
                }
                while(true){
                    if(node.next!=null) //keep going to the last node
                        node=node.next;
                    else{
                        table[temp] = new Node ((String) key,(File_Map) value,node.next); //create new node
                        node.next=table[temp];
                        numKeys++;
                        break;
                    }
                }

                // Check whether rehash is needed.
                double loadFactor =(double) (numKeys ) / table.length;
                if (loadFactor > LOADFACT)
                    rehash();

                Node nod= new Node();
                for (int i=0; i<numKeys; ++i){ //find last node before adding the new node, returns it
                    if(table[i]==table[index])
                        nod=table[i];
                }
                return nod.value;
            }
        }
    }

    /**
     * Chech whether the specified key is exist
     * @param key searching key
     * @return zero if it does not exist, if it is exist return their index
     */
    private int hasKey(String key) {
        int temp=0;
        for (int i=0;i<table.length;++i) { //looks the table indexs
            if(table[i]!=null) {
                if (key.equals((String)(table[i].key))) {
                    temp = i;
                    break;
                }
            }
        }
        return temp;
    }

    // Helper Method
    /**
     * Find the node at a specified index
     * @param index The index of the node sought
     * @return The node at index or null if it does not exist
     */
    public Node getNode(int index) {
        Node node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * Removes the mapping for a key from this map if it is present
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @throws NullPointerException if the specified key is null and this map does not permit null keys
     */
    @Override
    /*You do not need to implement remove function
     * */
    public Object remove(Object key) { return null; }

    /**
     * Copies all of the mappings from the specified map to this map.
     * The effect of this call is equivalent to that of calling put(k, v) on this map
     * once for each mapping from key k to value v in the specified map.
     * The behavior of this operation is undefined if the specified map is modified while the operation is in progress
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map m) {
        Iterator iter = m.keySet().iterator();
        while (iter.hasNext()) { //puts all values and keys
            Object k = iter.next();
            Object v = m.get(k);
            put(k, v);
        }
    }

    /**
     * Removes all of the mappings from this map (optional operation). The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        for (int i=0; i<numKeys; ++i) //clean the table
            table[i]=null;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * @return a set view of the keys contained in this map
     */
    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Set keySet() {
        Set set= new HashSet();
        Node node=getNode(0);
        for(int i=0; i<numKeys; i++) { //create a Set and adds the fnames
            set.add(node.key);
            node=node.next;
        }
        return set;
    }

    /**
     * Returns a Collection view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are reflected in the collection, and vice-versa.
     * @return a collection view of the values contained in this map
     */
    @Override
    /*Use linked structure instead of table index
    for efficiency
     * */
    public Collection values() {
        Collection a= new ArrayList();
        Node node=getNode(0);
        for(int i=0; i<numKeys; i++) //create a Arraylist and adds the occurances
            a.add(node.value);
        return a;
    }

    /** Finds either the target key or the first empty slot in the
     search chain using linear probing.
     pre: The table is not full.
     @param key The key of the target object
     @return The position of the target or the first empty slot if
     the target is not in the table.
     */
    private int find(Object key) {
        // Calculate the starting index.
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length; // Make it positive.

        // Increment index until an empty slot is reached
        // or the key is found.
        while ( (table[index] != null)
                && (!key.equals(table[index].key))) {
            index++;
            // Check for wraparound.
            if (index >= table.length)
                index = 0; // Wrap around.
        }
        return index;
    }

    /** Expands table size when loadFactor exceeds LOAD_THRESHOLD
     post: The size of table is doubled and is an odd integer.
     Each nondeleted entry from the original table is
     reinserted into the expanded table.
     The value of numKeys is reset to the number of items
     actually inserted; numDeletes is reset to 0.
     */
    private void rehash() {
        // Save a reference to oldTable.
        Node [] oldTable = table;
        // Double capacity of this table.
        table = new Node[1000 * oldTable.length + 1];

        // Reinsert all items in oldTable into expanded table.
        numKeys = 0;

        for (int i = 0; i < oldTable.length; i++) {
            if ( (oldTable[i] != null)) {
                // Insert entry in expanded table
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }

    /**
     * Returns a Set view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * If the map is modified while an iteration over the set is in progress the results of the iteration are undefined.
     * @return a set view of the mappings contained in this map
     */
    @Override
    /*You do not need to implement entrySet function
     * */
    public Set<Entry> entrySet() {
        return null;
    }

    /**
     * Lists all fnames and occurances
     */
    public void listAll() {
        Node node = getNode(0);
        while(node != null) {
            System.out.println("\n\nkey: "+node.key+"\nvalue's fname: "+node.value.getFile()+"\nvalue's occurances: "+node.value.getList());
            node = node.next;
        }
    }
}
