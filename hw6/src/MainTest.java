import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTest {

    public static void main(String[] arg) throws IOException {



        NLP nlp = new NLP();
        System.out.println("READING THE DATAS.");
        nlp.readDataset("dataset");
        System.out.println("WordMap is printing:");
        nlp.printWordMap();

        System.out.println("\n***********************BIGRAM TEST******************");
        System.out.println("nBigram-Orleans:");
        nlp.bigrams("Orleans");
        System.out.println("\n*********************tdIDF TEST*********************");
        System.out.println("tdIDF-for,0000026:");
        nlp.tfIDF("for","dataset\\0000026");

        NLP nlp2 = new NLP();

        Scanner scan=new Scanner(new File("input.txt"));
        while (scan.hasNext()){
            if(scan.next().equals("bigram"))
                nlp2.bigrams(scan.next());
            if(scan.next().equals("tdidf"))
                nlp2.tfIDF(scan.next(),scan.next());
        }



    }
}

