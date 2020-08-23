package Exp;

import Exp.Experiment;
import Exp.ExperimentList;

public class MainTest {
    public static void main(String[] arg) {

        ExperimentList.ExperimentIter iter = new ExperimentList.ExperimentIter();
        ExperimentList list = new ExperimentList();
        ExperimentList list2 = new ExperimentList();

        /***********************************************DATAS****************************************/

        Experiment exp1 = new Experiment("Exp1.1", 1, "09:00", true, (float)0.01);
        Experiment exp2 = new Experiment("Exp1.2", 1, "12:15", true, (float)0.08);
        Experiment exp3 = new Experiment("Exp1.3", 1, "15:30", false, (float)0.03);
        Experiment exp4 = new Experiment("Exp2.1", 2, "10:12", false, (float)0.1);
        Experiment exp5 = new Experiment("Exp2.2", 2, "14:17", true, (float)0.05);
        Experiment exp51 = new Experiment("Exp2.3", 2, "16:48", true, (float)0.04);
        Experiment exp6 = new Experiment("Exp3.1", 3, "13:01", false, (float)0.06);
        Experiment exp61 = new Experiment("Exp3.2", 3, "16:08", false, (float)0.25);
        Experiment exp7 = new Experiment("Exp4.1", 4, "11:25", true, (float)0.007);
        Experiment exp8 = new Experiment("Exp4.2", 4, "13:36", false, (float)0.8);
        Experiment exp9 = new Experiment("Exp4.3", 4, "17:48", true, (float)0.04);

        System.out.println("List size before adding is: "+list.size() + "\n");

        //////////ADDING DATAS//////////////
        System.out.println("**********Testing for addExp method**********\n");

        list.addExp(exp1);
        list.addExp(exp2);
        list.addExp(exp3);
        list.addExp(exp4);
        list.addExp(exp5);
        list.addExp(exp51);
        list.addExp(exp6);
        list.addExp(exp61);
        list.addExp(exp7);
        list.addExp(exp8);
        list.addExp(exp9);

        System.out.println("List datas after adding: \n");

        ExperimentList.Node node = list.getNode(0);
        while (node != null) {
            System.out.println(node.getData().toString());
            node = node.nextExp;
        }

        System.out.println("\nList size after adding is: "+list.size() + "\n");

        System.out.println("**********Testing for getExp method**********\n");

        System.out.println("Data of 2. experiment in 1. day is:\n"+ list.getExp(1,1));
        System.out.println("\nData of 3. experiment in 2. day is:\n"+ list.getExp(2,2));
        System.out.println("\nData of 1. experiment in 3. day is:\n"+ list.getExp(3,0));
        System.out.println("\nData of 3. experiment in 4. day is:\n"+ list.getExp(4,2));


        System.out.println("\n**********Testing for removeExp method**********\n");

        list.removeExp(1,2);
        list.removeExp(2,0);
        list.removeExp(3,1);
        list.removeExp(4,2);


        System.out.println("List after remove experiments for \n3. experiment in Day 1,\n1. experiment in Day 2,"
                + "\n2. experiment in Day 3 and \n3. experiment in Day 4 is:\n");

        ExperimentList.Node node2 = list.getNode(0);
        while (node2 != null) {
            System.out.println(node2.getData().toString());
            node2 = node2.nextExp;
        }

        System.out.println("\n**********Testing for for setExp method**********\n");


        list.setExp(1,2,exp61);
        list.setExp(3,1,exp4);
        list.setExp(4,2,exp9);
        System.out.println("List after insert datas:\n");

        ExperimentList.Node node3 = list.getNode(0);
        while (node3 != null) {
            System.out.println(node3.getData().toString());
            node3 = node3.nextExp;
        }

        System.out.println("\n**********Testing for removeDay method**********\n");


        System.out.println("List after remove Day 4:\n");

        list.removeDay(4);

        ExperimentList.Node node4 = list.getNode(0);
        while (node4 != null) {
            System.out.println(node4.getData().toString());
            node4 = node4.nextExp;
        }

        System.out.println("\n**********Testing for listExp method**********\n");
        System.out.println("Lets create a new list to test this method.\n");

        list2.addExp(exp1);
        list2.addExp(exp2);
        list2.addExp(exp3);
        list2.addExp(exp4);
        list2.addExp(exp5);
        list2.addExp(exp51);
        list2.addExp(exp6);
        list2.addExp(exp61);
        list2.addExp(exp7);
        list2.addExp(exp8);
        list2.addExp(exp9);

        System.out.println("Our list includes these datas:\n");

        ExperimentList.Node node6 = list2.getNode(0);
        while (node6 != null) {
            System.out.println(node6.getData().toString());
            node6 = node6.nextExp;
        }

        System.out.println("-------------------------------------------------------");
        System.out.println("List all completed experiments in Day 1:\n");

        ExperimentList.Node node7 = list2.listExp(1).getNode(0);
        while (node7 != null) {
            System.out.println(node7.getData().toString());
            node7 = node7.nextExp;
        }
        System.out.println("-------------------------------------------------------");
        System.out.println("List all completed experiments in Day 3:\n");

        ExperimentList.Node node8 = list2.listExp(3).getNode(0);
        while (node8 != null) {
            System.out.println(node8.getData().toString());
            node8 = node8.nextExp;
        }

        System.out.println("-------------------------------------------------------");
        System.out.println("List all completed experiments in Day 4:\n");

        ExperimentList.Node node9 = list2.listExp(4).getNode(0);
        while (node9 != null) {
            System.out.println(node9.getData().toString());
            node9 = node9.nextExp;
        }

        System.out.println("\n**********Testing for OrderDay method**********\n");
        System.out.println("List before sorting Day 1 is:\n");

        ExperimentList.Node node71 = list2.getNode(0);
        while (node71 != null) {
            System.out.println(node71.getData().toString());
            node71 = node71.nextExp;
        }

        System.out.println("\nList after sorting Day 1 is:\n");

        list2.orderDay(1);

        ExperimentList.Node node12 = list2.getNode(0);
        while (node12 != null) {
            System.out.println(node12.getData().toString());
            node12 = node12.nextExp;
        }

        System.out.println("\n**********Testing for orderExp method**********\n");
        System.out.println("I make a helper method that collections the datas according to their accuracy values " +
                "but when i invoke this method,\nfrom orderExp method i take mistake. If i invoke this method from main i dont take mistake." +
                "\nSo addNewExp is a method that collections the datas according to their accuracy values:\n");

        list2.addNewExp(exp1);
        list2.addNewExp(exp2);
        list2.addNewExp(exp3);
        list2.addNewExp(exp4);
        list2.addNewExp(exp5);
        list2.addNewExp(exp51);
        list2.addNewExp(exp6);
        list2.addNewExp(exp61);
        list2.addNewExp(exp7);
        list2.addNewExp(exp8);
        list2.addNewExp(exp9);

        ExperimentList.Node node22 = list2.getNode(0);
        while (node22 != null) {
            System.out.println(node22.getData().toString());
            node22 = node22.nextExp;
        }

       /* list2.orderExperiments();

       ExperimentList.Node node29 = list2.getNode(0);
        while (node29 != null) {
            System.out.println(node29.getData().toString());
            node29 = node29.nextExp;
        }*/

    }
}