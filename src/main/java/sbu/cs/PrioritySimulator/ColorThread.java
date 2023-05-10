package sbu.cs.PrioritySimulator;

import java.util.concurrent.CountDownLatch;

public abstract class ColorThread extends Thread {
    private CountDownLatch countDownLatch;
    public ColorThread(CountDownLatch countDownLatch){
        super();
        this.countDownLatch = countDownLatch;
    }
    void printMessage(Message message) {
        System.out.printf("[x] %s. thread_name: %s%n", message.toString(), currentThread().getName());
        Runner.addToList(message);
        countDownLatch.countDown();
    }

    abstract String getMessage();
}
