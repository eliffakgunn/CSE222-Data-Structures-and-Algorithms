import java.util.*;

public class File_Map implements Map
{
    /*
    For this hashmap, you will use arraylists which will provide easy but costly implementation.
    Your should provide and explain the complexities for each method in your report.
    * */
    /**Collect the file names*/
    ArrayList<String> fnames =new ArrayList<String>();
    /**Collect the occurances*/
    ArrayList<List<Integer>> occurances= new ArrayList<>();
    /**file name*/
    String file;
    /**Occurances list*/
    List<Integer> list= new ArrayList<>();

    /** The number of keys */
    private int numKeys2=0;

    /**
     *Constructor that takes file name and specified words occurances
     * @param fnames files name
     * @param occurances specified words occurances
     */
    public File_Map(String fnames, List<Integer> occurances){
        file=fnames;
        list=occurances;
    }

    /**
     * Gets file.
     * @return the file
     */
    public String getFile(){
        return file;
    }

    /**
     * Gets list of integers
     * @return list of integers
     */
    public List<Integer> getList(){
        return list;
    }

    /**
     * Gets filenames arraylist
     * @return filenames arraylist
     */
    public ArrayList<String> getFnames(){
        return fnames;
    }

    /**
     * Gets occurances list
     * @return occurances list
     */
    public ArrayList<List<Integer>> getOccurances(){
        return occurances;
    }

    /**
     * size of map
     * @return size of map
     */
    @Override
    public int size() {
        return numKeys2;
    }

    /**
     * Check whether map is empty
     * @return true if map i empty
     */
    @Override
    public boolean isEmpty() {
        return numKeys2==0;
    }

    /**
     * Check whether this map contains a mapping for the specified key
     * @param key key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    @Override
    public boolean containsKey(Object key){
        if(key==null)
            throw new NullPointerException();
        if(fnames.contains(key) && occurances.size()>fnames.indexOf(key)) //if fnames contains key and this key contains a mapping
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
    public boolean containsValue(Object value) throws NullPointerException {
        if(value==null)
            throw new NullPointerException();
        int temp=0;
        int i=0;
        while (true) {
            if(occurances.get(i).contains(value)){ //if this value mapped from a key
                temp=1;
                break;
            }
            if(i==occurances.size()-1) //if i=occurances.size(), occurances.get(i) throws NullPointerException
                break;
            ++i;
        }
        if(temp==1)
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
    public Object get(Object key){
        if(!containsKey(key)) //if fnames contains the key
            return null;
        return occurances.get(fnames.indexOf(key)); //returns value to which the specified key is mapped
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
    /*Each put operation will extend the occurance list*/
    public Object put(Object key, Object value) throws NullPointerException {
        if(key == null || value == null)
            throw new NullPointerException();
        if(!fnames.contains(key)){ //if fnames does not contain the key
            fnames.add(String.valueOf(key)); //adds the key to fnames
            List<Integer> list = new ArrayList<>();
            list.add((Integer) value);
            occurances.add(list); //adds the list to occurances
            return null;
        }
        else{ //if fnames contains the key does not add the key to fnames
            List<Integer> list = new ArrayList<>();
            list= occurances.get(fnames.indexOf(key));
            occurances.get(fnames.indexOf(key)).add((Integer) value); //adds the list to occurances
            return list;
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key.
     * @throws NullPointerException if the specified key is null and this map does not permit null keys
     */
    @Override
    public Object remove(Object key) throws NullPointerException {
        if(!containsKey(key))
            return null;
        List<Integer> list=occurances.get(fnames.indexOf(key));
        occurances.remove(fnames.indexOf(key)); //removes all mappings
        return list;
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     * The effect of this call is equivalent to that of calling put(k, v) on this map
     * once for each mapping from key k to value v in the specified map.
     * The behavior of this operation is undefined if the specified map is modified while the operation is in progress
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map m) {
        Iterator iter = m.keySet().iterator(); //puts all values and keys
        while (iter.hasNext()) {
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
        fnames.clear(); //clears fnames
        occurances.clear(); //clears occurances
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set keySet() {
        Set<String> set= new HashSet<>();
        for (int i=0; i< numKeys2; ++i) //create a Set and adds the fnames
            set.add(fnames.get(i));
        return set;
    }

    /**
     * Returns a Collection view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are reflected in the collection, and vice-versa.
     * @return a collection view of the values contained in this map
     */
    @Override
    public Collection values() {
        Collection a=new ArrayList();
        for (int i=0; i< numKeys2; ++i) //create a Arraylist and adds the occurances
            a.add(occurances.get(i));
        return a;
    }

    /**
     * Returns a Set view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * If the map is modified while an iteration over the set is in progress the results of the iteration are undefined.
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Entry> entrySet() {
        if (fnames.size() != occurances.size())
            throw new IllegalStateException();
        ArrayList list = new ArrayList(); //creating a arraylist and adds the set view of the mappings
        for (int i=0; i<fnames.size(); i++) {
            list.add(new Entry(fnames.get(i), occurances.get(i)));
        }
        return new MapSet(list);
    }
    /** The set of Map.Entry objects returned from entrySet(). */
    private class MapSet extends AbstractSet {
        List list;
        MapSet(ArrayList l) {
            list = l;
        }
        public Iterator iterator() {
            return list.iterator();
        }
        public int size() {
            return list.size();
        }
    }

    /**
     * Inner entry class has key and value data field that indicates key and value.
     */
    private class Entry {
        private Object key;
        private Object value;

        Entry(Object k, Object v) {
            key = k;
            value = v;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

    /**
     * Lists all fnames and occurances
     */
    public void listAll() {
        for (int i=0; i< 5; ++i) {
            System.out.println(fnames.get(i) + "\n" + occurances.get(i));
        }
    }
}
