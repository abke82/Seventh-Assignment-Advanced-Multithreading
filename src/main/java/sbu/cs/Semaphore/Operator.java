package sbu.cs.Semaphore;
import java.time.LocalTime;
import java.util.concurrent.Semaphore;

public class Operator extends Thread {

    public Operator(String name) {
        super(name);
    }

    @Override
    public void run() {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 10; i++)
        {
            LocalTime currentTime = LocalTime.now();
            System.out.println(getName()+" "+currentTime);

            try {
                semaphore.acquire();
                Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                semaphore.release();
            }

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
