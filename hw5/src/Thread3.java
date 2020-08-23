/**
 * will remove from PQEUX the color pixels and print them on screen one after the other, up
 * until a total of WxH pixels are printed.
 */
public class Thread3 implements Runnable {
    PriortyQueue p3 =new PriortyQueue();
    private boolean exit;


    public Thread3(){
        exit=false;
    }

    Thread3(PriortyQueue queue){
        p3=queue;
    }
    @Override
    public void run(){
        synchronized (p3) {
            //while (!exit){
                System.out.println("Thread3-PQEUX: ["+p3.poll().toString()+"]");
              //  p3.poll();
            }
            // }
    }

    public synchronized void start(){
        run();
    }

    public void stopp(){
        exit=true;
    }
}
