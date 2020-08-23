import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NLP
{
    public Word_Map wmap= new Word_Map();
    public ArrayList<List<Integer>> ar=new ArrayList<>();
    public ArrayList<String> abc= new ArrayList<>();

    /*You should not use File_Map class in this file since only word hash map is aware of it.
    In fact, you can define the File_Map class as a nested class in Word_Map,
     but for easy evaluation we defined it separately.
     If you need to access the File_Map instances, write wrapper methods in Word_Map class.
    * */

    /**
     * Reads the dataset from the given dir and created a word map
     * @param dir directory
     * @throws FileNotFoundException if file is not found
     */
    public void readDataset(String dir) throws FileNotFoundException {
        File klasor = new File(dir);
        File[] files = null;
        if(klasor.isDirectory()){
            files=klasor.listFiles();
        }
        int l=0;
        for(int i=0; i<files.length; ++i) {
           // System.out.println("--------------------------------------------------"+String.valueOf(files[i]));
            Scanner file = new Scanner(new File(String.valueOf(files[i])));
            int kk=1;
            while (file.hasNext()) {
                String current = file.next();
                while(current.endsWith(".") || current.endsWith(",") || current.endsWith(")") || current.endsWith("\"") ||
                        current.endsWith("?") || current.endsWith("!") || current.endsWith(":") || current.endsWith(";") || current.endsWith("'") ||
                        current.endsWith(":") ||current.startsWith("(") ||current.startsWith("\"") ||current.startsWith("'"))
                    current = current.trim().replaceAll("\\p{Punct}", "");
                //System.out.println(current);

                if(abc.isEmpty()) { //if arraylis is empty add the key
                    abc.add(current);
                    List<Integer> list= new ArrayList<>();
                    list.add(kk); //adds the occurance to list
                    ar.add(list);
                }
                else{
                    if (abc.contains((String)current)){ //if arraylist contains this key, does not add key
                        ar.get(abc.indexOf(current)).add(kk); //adds occurances
                    }
                    else{ //if arraylist does not contains this key
                        abc.add(current); //adds key
                        List<Integer> list= new ArrayList<>();
                        list.add(kk); //adds the occurance to list
                        ar.add(list);
                    }
                }
               File_Map fm=new File_Map(String.valueOf(files[i]), ar.get(abc.indexOf(current)));
                wmap.put(current,fm); //go to put method
                kk++;
                l++;
            }
        }
    }

    /**
     * Finds all the bigrams starting with the given word
     * @param word which is searching
     * @return the list includes bigrams
     */
    public List<String> bigrams(String word){
        List<String> list=new ArrayList<>();
        Word_Map.Node node = wmap.getNode(0);
        int kl=0;
        while(node != null) {
            if((node.key).equals(word)) {
                list.add(node.getKey()+" "+node.next.getKey()); //adds the [index] and [index+]
                //node=node.next;
            }
            node = node.next;
            kl++;
        }
        for (int i=0 ; i<list.size(); ++i){
            System.out.println(list.get(i));
        }
        return list;
    }

    /**
     * Calculates the tfIDF value of the given word for the given file
     * @param word which is searching
     * @param fileName which is file
     * @return the TFIDF value
     * @throws FileNotFoundException if file is does not exist
     */
    public float tfIDF(String word, String fileName) throws FileNotFoundException {
        int num1=0; //Number of times term t appears in a document
        int num2=0; //Total number of terms in the document
        Word_Map.Node node = wmap.getNode(0);
        Scanner file = new Scanner(new File(fileName));
        while (file.hasNext()) {
            String current = file.next();
            if (current.equals(word)) {
                num1++;
            }
            num2++;
        }

        float TF=(float)(num1/num2);
        float IDF= (float) Math.log(num2/num1);

        return TF*IDF;
    }

    /**
     * Print the WordMap by using its iterator
     */
    public  void printWordMap(){
        wmap.listAll();
    }

}
