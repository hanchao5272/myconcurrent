package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>并发可见性实例</p>
 *
 * @author hanchao 2018/3/13 21:57
 **/
public class ConcurrentVisibilityDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentVisibilityDemo.class);

    //普通情况下，多线程不能保证可见性
    private static boolean aStop;
    private static boolean bStop;

    //使用volatile能够保证可见性
    private volatile static boolean aVStop;
    private volatile static boolean bVStop;

    //通过synchronized同步代码块保证可见性
    private static byte[] obj = new byte[0];

    static class SVThread extends Thread{
        private String name;
        private boolean aaa;
        private boolean bbb;

        public SVThread(String name, boolean aaa, boolean bbb) {
            this.name = name;
            this.aaa = aaa;
            this.bbb = bbb;
        }

        @Override
        public void run() {
            System.out.println("Synchronized " + this.name + " is running...");
            synchronized (obj){
                bStop = true;
            }
            while (!this.aaa) ;
            System.out.println("Synchronized " + this.name + " is terminated.");
        }
    }


    //通过Lock接口保证可见性
    private static ReentrantLock lock = new ReentrantLock(true);

    //通过Atomic保证可见性
    private static AtomicBoolean aAStop = new AtomicBoolean(false);
    private static AtomicBoolean bAStop = new AtomicBoolean(false);
    /**
     * <p>并发可见性实例</p>
     *
     * @author hanchao 2018/3/13 21:56
     **/
    public static void main(String[] args) throws InterruptedException {
        /**
         * 0 = 不可见性
         * 1 = volatile
         * 2 = synchronized
         * 3 = Lock
         * 4 = Atomic
         */
        int type = 0;
        switch (type) {
            case 0:
                //普通情况下，多线程不能保证可见性
                new Thread(() -> {
                    System.out.println("Ordinary A is running...");
                    bStop = true;
                    while (!aStop) ;
                    System.out.println("Ordinary A is terminated.");
                }).start();
//                Thread.sleep(10);
                new Thread(() -> {
                    System.out.println("Ordinary B is running...");
                    aStop = true;
//                    while (!bStop) ;
                    System.out.println("Ordinary B is terminated.");
                }).start();
                break;
            case 1:
                //通过volatile关键字保证可见性
                new Thread(() -> {
                    System.out.println("Volatile A is running...");
                    bVStop = true;
                    while (!aVStop) ;
                    System.out.println("Volatile A is terminated.");
                }).start();
                new Thread(() -> {
                    System.out.println("Volatile B is running...");
                    aVStop = true;
//                    while (!bVStop) ;
                    System.out.println("Volatile B is terminated.");
                }).start();
                break;
            case 2:
                //通过synchronized同步代码块保证可见性
//                new SVThread("A",aStop,bStop).start();
//                new SVThread("B",bStop,aStop).start();
                new Thread(() -> {
                    bStop = true;
                    System.out.println("Synchronized A is running...");
                    synchronized (obj){
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        while (!aStop) ;
                    }
                    System.out.println("Synchronized A is terminated.");
                }).start();
                Thread.sleep(10);
                new Thread(() -> {
                    System.out.println("Synchronized B is running...");
                    synchronized (obj){
                        aStop = true;
                        obj.notify();
                    }
//                    while (!bStop) ;
                    System.out.println("Synchronized B is terminated.");
                }).start();
                break;
            case 3:
                //通过Lock同步代码块保证可见性
                new Thread(() -> {
                    System.out.println("Lock A is running...");
                    lock.lock();
                    bStop = true;
                    lock.unlock();
                    while (!aStop) ;
                    System.out.println("Lock A is terminated.");
                }).start();
                new Thread(() -> {
                    System.out.println("Lock B is running...");
                    lock.lock();
                    aStop = true;
                    lock.unlock();
                    while (!bStop) ;
                    System.out.println("Lock B is terminated.");
                }).start();
                break;
            case 4:
                //通过Atomic保证可见性
                new Thread(() -> {
                    System.out.println("Atomic A is running...");
                    bAStop.set(true);
                    while (!aAStop.get()) ;
                    System.out.println("Atomic A is terminated.");
                }).start();
                new Thread(() -> {
                    System.out.println("Atomic B is running...");
                    aAStop.set(true);
                    while (!bAStop.get()) ;
                    System.out.println("Atomic B is terminated.");
                }).start();
                break;
            default:
                break;
        }



    }
}
