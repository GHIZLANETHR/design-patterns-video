package org.ghizlane.threads;

public class TaskImpl2 implements Runnable {

    @Override
    public void run() {
        System.out.println(
                "Runnable : " +
                        Thread.currentThread().getName()
        );
    }
}
