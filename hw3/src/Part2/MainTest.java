package Part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainTest {

    public static void main(String[] args) throws IOException, InfixToPostfix.SyntaxErrorException {

        InfixToPostfix e = new InfixToPostfix();

        //open file and read
        File file = new File("file.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();

        while (line != null) {
            e.convert(line);
            line = reader.readLine();
        }
        reader.close(); //close the file
        System.out.println("POSTFIX FORM:");
        System.out.println(e.postfix);
        System.out.println("\nRESULT:");
        System.out.println(e.eval(e.postfix.toString()));
    }
}