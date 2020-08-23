/**
 * will remove from PQBMX. the color pixels and print them on screen one after the other, up
 * until a total of WxH pixels are printed.
 */
public class Thread4 implements Runnable {
    private boolean exit;

    public Thread4(){
        exit=false;
    }

    @Override
    public void run(){
        System.out.println("Thread4");

        PriortyQueue p4 =new PriortyQueue();
        p4.poll();
    }

    public synchronized void start(){
        run();
    }

    public void stopp(){
        exit=true;
    }

}
