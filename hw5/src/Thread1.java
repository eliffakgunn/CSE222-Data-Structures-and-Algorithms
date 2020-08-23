/**
 *responsible for reading the pixels from the image starting from the top-left corner and
 * advancing column first until the bottom-right pixel. Every color pixel read, will be inserted to each
 * of the 3 priority queues (named PQLEX, PQEUC and PQBMX: one per ordering relation). As soon
 * as the first 100 pixels are insert.
 */
public class Thread1 extends Thread {
    PriortyQueue p1 = new PriortyQueue();
    TwoDArray obj;
    private boolean exit;

    public Thread1(PriortyQueue q, TwoDArray a){
        p1=q;
        obj=a;
        exit=false;
        System.out.println("Thread1: ["+a.toString()+"]");
    }

    @Override
    public void run(){
        synchronized (p1){
            // while (!exit){
            p1.offer(obj);
            //}
        }
    }

    public synchronized void start(){
        run();
    }

    public void stopp(){
        System.out.println("qqq");
        exit=true;
    }
}
