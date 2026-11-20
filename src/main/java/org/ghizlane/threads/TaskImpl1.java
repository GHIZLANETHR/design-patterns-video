package org.ghizlane.threads;

public class TaskImpl1 extends Thread {

    @Override
    public void run() {
        System.out.println(
                "Thread hérité : " +
                        Thread.currentThread().getName()
        );
    }
}
