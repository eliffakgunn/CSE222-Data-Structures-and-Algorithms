package Exp; /**
 * @author akgun
 */

import Exp.Experiment;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Exp.ExperimentList keeps track of some machine learning experiments and their results
 */

public class ExperimentList implements Iterable<Experiment> {

    /** A reference to the head of the list. */
    private Node head_experiment=null;

    /** This data field stores the size of the list. */
    private int size=0;
    private int size2=0; //for addNewExp method

    /**
     * Nested class
     *Node builds block for the Exp.ExperimentList
     */
    public class Node{
        /**
         * data includes Exp.Experiment data fields
         */
        Experiment data;
        /**
         * indicates next experimet(Exp1,Exp2...)
         */
        Node nextExp;
        /**
         * indicates next days(Day1,Day2...)
         */
        Node nextDay;

        /**
         * Constructor that takes data and new experiment information
         * @param data - The data value
         * @param nextExp - indicate nex experiment
         */
        public Node(Experiment data, Node nextExp){
            this.data=data;
            this.nextExp=nextExp;
        }

        /**
         * Constructor that takes data, next experiment and next day information
         * @param data The data value
         * @param nextExp indicate next experiment
         * @param nextDay indicate next day
         */
        public Node(Experiment data, Node nextExp, Node nextDay){
            this.data=data;
            this.nextExp=nextExp;
            this.nextDay=nextDay;
        }

        /**
         * default constructor does nothing
         */
        public Node() {}


        /**
         * Construct a node with the given data value
         * @param data - The data value
         */
        public Node(Experiment data) {
            this(data, null);
        }

        /**
         * Returns the data of experiments
         * @return type is E
         */
        public Experiment getData() {
            return data;
        }
    }

    /**
     * ExperimentIter class implements Iterator class and overrides its methods
     */
    public static class ExperimentIter implements Iterator<Experiment> {

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
        public Experiment next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.nextExp;
            index++;
            return lastItemReturned.data;
        }
        public void remove() {
        }
    }
    /**
     * This method provides using the Iterator methods next, hasNext and remove
     * @return type is Iterator
     */
    @Override
    public Iterator<Experiment> iterator() {
        return new ExperimentIter();
    }

    /**
     * Gets size of the list
     * @return type is int
     */
    public int size(){
        return size;
    }
    /** Add a new item to the end of the list
     * If list does not have given day, creates new day and node and adds the experiment end of the list
     * Else list has given day, goes end of the day and adds the experiment end of the list
     *@param data The item to be added.
     *@return returns true if add is successful.
     */
    public boolean addExp (Experiment data) {
        if (size == 0)
            addFirst(data, null); //if size is zero, creates firs node
        else
        {
            Node nod=getNode(0); //indicates first node, this node walks on the list

            while(true)
            {
                if(nod.getData().getDay()!=data.getDay() && nod.nextDay!=null) //if the day of node is not given day and
                    // nextDay is not null, nod is next node now
                    nod=nod.nextDay;
                else if(nod.getData().getDay()!=data.getDay() && nod.nextDay==null) //if the day of node is not given day and
                // nextDay is  null
                {
                    Node new_node=new Node(data,null); //create new node that its next experiment is null
                    getNode(size-1).nextExp=new_node; //next experiment the node which end of the list is new node
                    nod.nextDay=new_node;
                    break;
                }
                else //if the day of node is given day
                {
                    while(true)
                    {
                        if(nod.getData().getDay()==data.getDay() && nod.nextExp!=null) //if the day of node is given day and
                            // next experiment is not null, nod is next experiment now
                            nod=nod.nextExp;
                        else// else create a new node that its next experiment is next experiment of day
                        {
                            Node temp=nod.nextExp;
                            Node new_node=new Node(data,temp);
                            nod.nextExp=new_node; //next exp of node is new node now
                            break;
                        }
                    }
                    break;
                }
            }
        }
        size++;
        return true;
    }

    /**
     *Inserts datas to given day and index
     * If the index is zero,  datas inserted front of the day
     * If day is 1, head experiment is given days experiment now
     * If day is not 1, walks on the list to the given day
     * @param day indicates day of data
     * @param index index where insert the data
     * @param data which inserts to given index
     * @return return true when set is successful
     */
    public boolean setExp (int day,int index,Experiment data)
    {
        if(hasDay(day)==false)
            throw new NoSuchElementException("This day is not exist.");

        Node nod=getNode(0); //indicates first node, this node walks on the list

        if(index<0 || index>size)
            throw new IndexOutOfBoundsException("index is not valid");

        else if(index==0)
        {
            if(size==0)//Insert at head.
                addFirst(data,null);
            else if(head_experiment.getData().getDay()==day) //If day is 1, head experiment is given days experiment now
            {
                head_experiment=new Node(data,nod,nod.nextDay);
                nod.nextDay=null;
            }
            else //day different from 1
            {
                while (true)
                {
                    Node temp=nod.nextDay;

                    if(nod.nextDay!=null && nod.nextDay.getData().getDay()!=day) //if next day of node is not null and its day
                        //is not equal given day, nod is next day now
                        nod=nod.nextDay;
                    else
                    {
                        Node new_node;
                        if (nod.nextDay.nextDay != null) {/////////////////////////////////////

                            new_node = new Node(data, nod.nextDay, nod.nextDay.nextDay); //creates new node thet its next experiment is nex day of node
                            //and its next day is next day of next day of the node
                            nod.nextDay = new_node; //the nodes next day is new node now

                        }else{ //nod.nextDay.nextDay is  null
                            new_node=new Node(data,nod.nextDay,null); //create a new node and add end of the day
                            nod.nextDay=new_node;
                        }
                        while(true)
                        {
                            if(nod.nextExp!=null && nod.nextExp.getData().getDay()!=day) //to link bore with new node, comes previous node
                                nod=nod.nextExp;
                            else
                            {
                                nod.nextExp=new_node; //the node's next day is new node now
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        else{//index different from zero
            while(true) {
                if (nod.getData().getDay() != day && nod.nextDay != null) //to link previous node, keep going when comes previous day
                    nod = nod.nextDay;
                else {
                    for (int i = 0; i < index - 1; ++i) ////to link previous node, keep going when comes previous example
                        nod = nod.nextExp;
                    nod.nextExp = new Node(data, nod.nextExp);
                    break;
                }
            }
        }
        ++size;
        return true;
    }

    /**
     * Removes the given day from the list
     * @param day day which removes
     */
    public void removeDay(int day){

        if(hasDay(day)==false) //If given day is not exist throws exception
            throw new NoSuchElementException("This day is not exist.");

        Node nod=getNode(0); //indicates first node, this node walks on the list

        if(size==0)
            throw new NoSuchElementException("Empty list");
        else{
            if (getNode(searchFirstExperiment(day)). nextDay == null&& nod.getData().getDay() == day) { // if List includes 1 day
                head_experiment=null;
                size=0;
                System.out.println("List is empty now.");
            }
            else if(getNode(searchFirstExperiment(day)). nextDay == null && nod.getData().getDay() != day)
            {
                while (true)
                {
                    if (nod.nextDay.getData().getDay() != day && nod.nextDay == null) //it walks on the list to next day of nod is equal the given day
                        nod = nod.nextDay; //nod is next node now
                    else
                    {
                        nod.nextDay = null;

                        while (true)
                        {
                            if (nod.nextExp.getData().getDay() != day) //it walks on the day, experiment by experiment to next experiment is equal the given day
                                nod = nod.nextExp;
                            else
                            {
                                nod.nextExp = null;
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            else {//the next experimetn of node of given day is not null
                if(nod.getData().getDay() == day){ //given day is first day
                    head_experiment=head_experiment.nextDay;
                }
                else{
                    while(true){
                        if(nod.nextDay.getData().getDay()!=day) //it walks on the list, day by day to next day is equal the given day
                            nod=nod.nextDay;
                        else { //when the nod front of the given day
                            nod.nextDay=nod.nextDay.nextDay;
                            Node temp=nod.nextDay;
                            while (true) {
                                if (nod.nextExp.getData().getDay() != day) //it walks on the day, experiment by experiment to next day is equal the given day
                                    nod = nod.nextExp;
                                else {
                                    nod.nextExp=temp;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Removes the experiment from given day and index
     * @param day which removes the data
     * @param index which indicates index of removes data
     */
    public void removeExp(int day, int index){

        if(hasDay(day)==false) //If given day is not exist throws exception
            throw new NoSuchElementException("This day is not exist.");

        Node nod=getNode(0); //indicates first node, this node walks on the list

        if(index<0 || index>size)
            throw new IndexOutOfBoundsException("index is not valid");
        else if(index==0){ //remove first experiments of days
            if(size==0)
                throw new NoSuchElementException("List is already emty!");
            else if(head_experiment.getData().getDay()==day) //removes on the head experiment
                head_experiment=head_experiment.nextExp; //head experiment is equal next experiment now
            else{
                while(true){
                    if(nod.nextDay!=null && nod.nextDay.getData().getDay() !=day) //it walks on the list, day by day to next day is equal the given day
                        nod=nod.nextDay;
                    else{
                        Node temp = nod.nextDay;
                        if(nod.nextDay.nextDay != null){
                            nod.nextDay=temp.nextExp;
                            while(true){
                                if(nod.nextExp.getData().getDay()!=day) //it walks on the day, experiment experiment to next day is equal the given day
                                    nod=nod.nextExp;
                                else{
                                    nod.nextExp=temp.nextExp;
                                    nod.nextExp.nextDay=temp.nextDay;
                                    temp.nextDay=null;
                                    temp.nextExp=null;
                                    break;
                                }
                            }
                        }
                        else{ //nod.nextDay.nextDay == null
                            nod.nextDay=temp.nextExp;

                            while (true){
                                if(nod.nextExp.getData().getDay()!=day) //it walks on the day, experiment experiment to next day is equal the given day
                                    nod=nod.nextExp;
                                else{
                                    nod.nextDay=null;
                                    nod.nextExp=temp.nextExp;
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        else{ //index is different from zero
            if(nod.getData().getDay()==day) //remove experimenr from firs day
            {
                for(int i=0; i<index-1; i++) //to come indicate index
                    nod=nod.nextExp;

                nod.nextExp=nod.nextExp.nextExp;
            }
            else{//remove experimenr from other days
                while(true){
                    if(nod.getData().getDay()!=day) //it walks on the list, day by day to next day is equal the given day
                        nod=nod.nextDay;
                    else{
                        for(int i=0; i<index-1; i++) //it walks on the day, experiment by experiment to next day is equal the given day
                            nod=nod.nextExp;

                        nod.nextExp=nod.nextExp.nextExp;
                        break;
                    }
                }

            }
        }
        size--;
    }

    /**
     * list all completed experiments in a given day
     * @param day which listed
     * @return type is ExperimentList
     */
    public ExperimentList listExp(int day){

        if(hasDay(day)==false) //If given day is not exist throws exception
            throw new NoSuchElementException("This day is not exist.");

        Node nod=getNode(0); //indicates first node, this node walks on the list

        ExperimentList exp= new ExperimentList();

        while (true){
            if (nod.getData().getDay() != day) //it walks on the list, day by day to next day is equal the given day
                nod = nod.nextDay;
            else {
                if (nod.nextDay != null) {
                    int i = 0;

                    while (nod.getData().getDay() == day) { //when day of nod equal the given day
                        if (getExp(day,i).getCompleted()) //if competed is true, adds the data
                            exp.addExp(getExp(day, i));
                        i++;
                        nod = nod.nextExp;
                    }
                } else {
                    int i = 0;
                    while (nod != null) { //looks the datat up to nod is null
                        if (getExp(day, i).getCompleted()) //if competed is true, adds the data
                            exp.addExp(getExp(day, i));
                        nod = nod.nextExp;
                        i++;
                    }
                }
                break;
            }
        }
        return exp;
    }

    /**
     *sorts all the experiments in the list according to the accuracy,
     * the original list does not change
     * @return Experiment data
     */
    public ExperimentList orderExperiments() {

        ExperimentList ex= new ExperimentList();
        /*
        int i=0;
        while(i<size2){
            addNewExp(get(i));
            ++i;
        }
         */
/*
        Node node = getNode(0);
        int i = 0;
        while (i < size()) {
            if (size2 == 0) { //if size is zero adds first
                addFirst(get(i), null);

            } else {
                System.out.println("node" + node.getData().getAccuracy());
                System.out.println("ttttttttttt");
                System.out.println("data" + getNode(i).getData().getAccuracy());
                if (node.getData().getAccuracy() < get(i).getAccuracy()) { //adds the datas according to their accuracy value
                    System.out.println("iffffffffff");

                    if (/*node.getData().getAccuracy()<=data.getAccuracy() &&*/// node.nextExp == null) { //adds second element
            /*            System.out.println("qq");
                        Node new_node = new Node(get(i), null);
                        node.nextExp = new_node;
                    } else {
                        System.out.println("cc");
                        while (true) {
                            if (node.nextExp!= null && node.nextExp.getData().getAccuracy()<=get(i).getAccuracy()) {
                                System.out.println("dd");
                                node = node.nextExp;
                            } else {
                                System.out.println("ee");
                                Node new_node = new Node(get(i), node.nextExp);
                                node.nextExp = new_node;
                                break;
                            }
                        }
                    }
                } else { //node.getData().getAccuracy()>data.getAccuracy()
                    System.out.println("elseeeeeeeeeee");
                    head_experiment = new Node(get(i), node); //new node become head
                }
            }
            size2++;
            i++;
        }
        return head_experiment.getData();*/
        return ex;
    }

    //HELPER FUNCTION
    /**
     * Adds the datas according to their accuracy values
     * @param data includes accuracy values
     * @return type is Experiment
     */
    public Experiment addNewExp(Experiment data) {
        Node node = getNode(0);

        if (size2 == 0){ //if size is zero adds first
            addFirst(data, null);
        }
        else
        {
            if(node.getData().getAccuracy()<data.getAccuracy()){ //adds the datas according to their accuracy value

                if(/*node.getData().getAccuracy()<=data.getAccuracy() &&*/ node.nextExp== null){ //adds second element
                    Node new_node = new Node(data, null);
                    node.nextExp = new_node;
                }
                else{
                    while(true){
                        if( node.nextExp!= null && node.nextExp.getData().getAccuracy()<=data.getAccuracy()){
                            node=node.nextExp;
                        }
                        else{
                            Node new_node = new Node(data,node.nextExp);
                            node.nextExp=new_node;
                            break;
                        }
                    }
                }
            }
            else{ //node.getData().getAccuracy()>data.getAccuracy()
                head_experiment = new Node(data,node); //new node become head
            }
        }
        size2++;
        return head_experiment.getData();
    }

    /**
     * Sorts the experiments in a given day according to the accuracy
     * Sorts the datas like bobble sort to increases
     * The list will change
     * @param day which datas sorted
     */
    public void orderDay(int day){

        if(hasDay(day)==false)
            throw new NoSuchElementException("This day is not exist.");

        Experiment temp= new Experiment();

        for(int i=searchFirstExperiment(day); i<searchFirstExperiment(day)+sizeOfDay(day); i++) {
            for (int j = searchFirstExperiment(day); j < searchFirstExperiment(day) + sizeOfDay(day); j++) {
                if(getNode(i).getData().getAccuracy()<getNode(j).getData().getAccuracy()){
                    temp=getNode(i).getData();
                    removeExp(day,i-searchFirstExperiment(day)); //remove data because if doesnt remove we have more datas
                    setExp(day,j-searchFirstExperiment(day),temp);
                }
            }
        }

       /*int t=0;
        Experiment temp= new Experiment();
        NodeExp<Experiment> nod=getNode(0);
System.out.println(sizeOfDay(day));
        for (int i = 0; i < sizeOfDay(day); i++) {
            for (int j = 1; j < (sizeOfDay(day) - i+1); j++) {
                if(getNode(searchFirstExperiment(day)+j-1).getData().getAccuracy()>getNode(searchFirstExperiment(day)+j).getData().getAccuracy()){
                   System.out.println("zzzzzzzzzzz");

                   temp=getNode(searchFirstExperiment(day)+j-1).getData();
                  // setExp(day,j-1,getNode(searchFirstExperiment(day)+j).getData());
                    removeExp(day,j-1);
                    System.out.println(day);
                    System.out.println(j);
                    t=j;
                   // setExp(day,j,temp);
                    //removeExp(day,j+1);
               }
            }
        }
        setExp(day,t,temp);*/

    }

   /* int n = arr.length;
    int temp = 0;
         for(int i=0; i < n; i++){
        for(int j=1; j < (n-i+1); j++){
            if(arr[j-1] > arr[j]){
                //swap elements
                temp = arr[j-1];
                arr[j-1] = arr[j];
                arr[j] = temp;
            }

        }
    }*/

    /**
     * Returns size of given day
     * @param day which check the size
     * @return type is int
     */
    public int sizeOfDay(int day){
        Node nod=getNode(0);
        int i=0;

        if(nod.getData().getDay()==day){ //day is equal first day
            while(nod.getData().getDay()==day){
                nod=nod.nextExp;
                ++i;
            }
        }
        else{
            while (true){
                if(nod.getData().getDay()!=day) //it walks on the list, day by day to next day is equal the given day
                    nod=nod.nextDay;
                else{
                    if(nod.nextDay!=null) {
                        while (nod.getData().getDay() == day) {
                            nod = nod.nextExp;
                            i++;
                        }
                    }else {
                        while (nod != null) {
                            nod = nod.nextExp;
                            i++;
                        }
                    }
                    break;
                }
            }
        }
        return i;
    }

    // Helper Method
    /**
     * Find the node at a specified index
     * @param index The index of the node sought
     * @return The node at index or null if it does not exist
     */
    public Node getNode(int index) {
        Node node = head_experiment;
        for (int i = 0; i < index && node != null; i++) {
            node = node.nextExp;
        }
        return node;
    }
    //Helper Method
    /** Insert an item as the first item of the list.
     *	@param item The item to be inserted
     *  @param next_data indicate the Node
     */
    public void addFirst(Experiment item, Node next_data) {
        if(next_data==null){
            head_experiment = new Node(item, head_experiment);
            head_experiment.nextDay=null;
            head_experiment.nextExp=null;
        }
    }
    //helper method
    /**
     * Get the data value at index
     * @param index The index of the element to return
     * @return The data at index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Experiment get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node node = getNode(index);
        return node.data;
    }
    //helper method
    /**
     * If the list includes given day, returns true
     * @param day which check
     * @return type is boolean
     */
    private boolean hasDay(int day)
    {
        for (int i=0;i<size;++i)
            if(day==get(i).getDay())
                return true;
        return false;
    }
    //helper method
    /**
     * Returns index that first index of given day
     * @param day checks
     * @return type is int
     */
    public int searchFirstExperiment(int day) {
        for (int i=0;i<=size;++i)
            if (getNode(i).getData().getDay()==day)
                return i;
        return -1;
    }

    /**
     * Get the experiment with the given day and position
     * @param day that gets data
     * @param index that indicate which experiment
     * @return data which given day and index
     */
    public Experiment getExp(int day, int index){
        Node nod=getNode(0);
        while(true){

            if(nod!=null && nod.getData().getDay()!=day){ //it walks on the list, day by day to next day is equal the given day
                nod=nod.nextDay;
            }
            else{
                for(int i=0; i<=index-1; ++i) //it walks on the day experiment by experiment to next day is equal the given day
                    nod=nod.nextExp;
                break;
            }
        }
        return nod.getData();
    }

    public void listAll()
    {
        System.out.println("List experiment view:");
        Node last = head_experiment;
        while( last != null) {
            System.out.println(last.data.toString());
            last = last.nextExp;
        }
        System.out.println("List day view:");
        last = head_experiment;
        while( last != null) {
            System.out.println(last.data.toString());
            last = last.nextDay;
        }
    }

}






