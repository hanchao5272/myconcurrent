package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>并发可见性实例</p>
 *
 * @author hanchao 2018/3/13 21:57
 **/
public class ConcurrentVisibilityDemo {
    //普通情况下，多线程不能保证可见性
    private static boolean stop;

    //使用volatile能够保证可见性
    private volatile static boolean vStop;

    //通过synchronized同步代码块保证可见性
    private static byte[] obj = new byte[0];

    //通过Lock接口保证可见性
    private static ReentrantLock lock = new ReentrantLock(true);
    private static Condition condition = lock.newCondition();

    //通过Atomic保证可见性
    private static AtomicBoolean aStop = new AtomicBoolean(false);

    /**
     * <p>并发可见性实例</p>
     *
     * @author hanchao 2018/3/13 21:56
     **/
    public static void main(String[] args) throws InterruptedException {
        /**
         * 0 = 不可见性      no
         * 1 = volatile     ok
         * 2 = synchronized ok
         * 3 = Lock         ok
         * 4 = Atomic       ok
         */
        int type = 3;
        switch (type) {
            case 0:
                //普通情况下，多线程不能保证可见性
                new Thread(() -> {
                    System.out.println("Ordinary A is running...");
                    while (!stop) ;
                    System.out.println("Ordinary A is terminated.");
                }).start();
                Thread.sleep(10);
                new Thread(() -> {
                    System.out.println("Ordinary B is running...");
                    stop = true;
                    System.out.println("Ordinary B is terminated.");
                }).start();
                break;
            case 1:
                //通过volatile关键字保证可见性
                new Thread(() -> {
                    System.out.println("Volatile A is running...");
                    while (!vStop) ;
                    System.out.println("Volatile A is terminated.");
                }).start();
                new Thread(() -> {
                    System.out.println("Volatile B is running...");
                    vStop = true;
                    System.out.println("Volatile B is terminated.");
                }).start();
                break;
            case 2:
                //通过synchronized同步代码块保证可见性
                new Thread(() -> {
                    System.out.println("Synchronized A is running...");
                    synchronized (obj) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        while (!stop) ;
                    }
                    System.out.println("Synchronized A is terminated.");
                }).start();
                Thread.sleep(10);
                new Thread(() -> {
                    System.out.println("Synchronized B is running...");
                    synchronized (obj) {
                        stop = true;
                        obj.notify();
                    }
                    System.out.println("Synchronized B is terminated.");
                }).start();
                break;
            case 3:
                //通过Lock同步代码块保证可见性
                new Thread(() -> {
                    System.out.println("Lock A is running...");
                    lock.lock();
                    try {
                        condition.await();
                        while (!stop) ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    System.out.println("Lock A is terminated.");
                }).start();
                new Thread(() -> {
                    System.out.println("Lock B is running...");
                    lock.lock();
                    try {
                        stop = true;
                        condition.signal();
                    } finally {
                        lock.unlock();
                    }
                    System.out.println("Lock B is terminated.");
                }).start();
                break;
            case 4:
                //通过Atomic保证可见性
                new Thread(() -> {
                    System.out.println("Atomic A is running...");
                    while (!aStop.get()) ;
                    System.out.println("Atomic A is terminated.");
                }).start();
                new Thread(() -> {
                    System.out.println("Atomic B is running...");
                    aStop.set(true);
                    System.out.println("Atomic B is terminated.");
                }).start();
                break;
            default:
                break;
        }
    }
}
