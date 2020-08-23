/**
 * will remove from PQLEX the color pixels and print them on screen one after the other, up
 * until a total of WxH pixels are printed.
 */
public class Thread2 implements Runnable {

    PriortyQueue p2 =new PriortyQueue();
    private boolean exit;

    public Thread2(){
        exit=false;
    }
    Thread2(PriortyQueue queue){
        p2=queue;
    }
    @Override
    public void run(){
        synchronized (p2) {
           // while (!exit){
                System.out.println("Thread2-PQLEX: ["+p2.poll().toString()+"]");
               // p2.poll();
           // }
        }
    }
    public synchronized void start(){
           run();
    }

    public void stopp(){
        exit=true;
    }
}
