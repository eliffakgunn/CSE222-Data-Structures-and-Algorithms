import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        BufferedImage img = null;
        File f = null;

        // read image
        try {
            f = new File("C:\\\\Users\\\\user\\\\Desktop\\\\1801042251\\\\images.jpg");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();

        TwoDArray[][] array = new TwoDArray[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                Color c = new Color(img.getRGB(i, j));
                array[i][j] = new TwoDArray(c.getRed(), c.getGreen(), c.getBlue());

            }
        }

        PQEUC p1 = new PQEUC();
        PriortyQueue pqeuc = new PriortyQueue();
        pqeuc.comparator = p1;

        PQLEX p2 = new PQLEX();
        PriortyQueue pqlex = new PriortyQueue();
        pqlex.comparator = p2;

        int k = 0;
        int temp=0;

       /*Firt Thread1 offer to the queues 100 pixel and other threads does nothing*/
        for(int i=0;i<width;++i){
            for(int j=0; j<height; ++j){
                Thread1 t1 = new Thread1(pqeuc, array[i][j]);
                Thread1 t_1 = new Thread1(pqlex, array[i][j]);
                Thread t=new Thread(t1);
                t.start();
                t_1.start();
                /*t1.stopp();
                t_1.stopp();*/
                k++;
                if(k==100){
                    temp=1;
                    break;
                }
            }
            if (temp==1){
                break;
            }
        }
        /*Thread1 keeps offer the pixels to the other threads and other threads starts the polling.*/
        int mm=width*height;
        int temp2=0;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                        Thread1 t1 = new Thread1(pqeuc, array[i][j]);
                        Thread1 t_1 = new Thread1(pqlex, array[i][j]);
                         t1.start();
                         t_1.start();
                        Thread2 t2 = new Thread2(pqlex);
                        Thread3 t3 = new Thread3(pqeuc);
                        t2.start();
                        t3.start();
                        /*t1.stopp();
                        t_1.stopp();
                        t2.stopp();
                        t3.stopp();*/

                    mm--;
                    if(mm==9){
                        temp2=1;
                        break;
                    }
            }
            if(temp2==1){
                break;
            }
        }
        /**Polls the rest of the queues*/
        int tt=0;
        int temp3=0;
        for (int i = 0; i < 100; ++i) {
                Thread2 t2 = new Thread2(pqlex);
                Thread3 t3 = new Thread3(pqeuc);
                t2.start();
                t3.start();
        }
            System.out.println("Last size of PQEUC: "+pqeuc.getSize());
            System.out.println("Last size of PQLEX: "+pqlex.getSize());
    }
}