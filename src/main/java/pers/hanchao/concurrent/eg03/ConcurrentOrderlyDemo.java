package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Java并发有序性示例</p>
 *
 * @author 韩超 2018/3/13 17:10
 */
public class ConcurrentOrderlyDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentOrderlyDemo.class);
    ///////////////////////////////////////////////  并发有序性 无保障 ///////////////////////////////////////////////
    static String a1 = new String("A : x = x + 1");
    static String a2 = new String("A : x = x - 1");
    static String b1 = new String("B : x = x * 2");
    static String b2 = new String("B : x = x / 2");

    ///////////////////////////////////////////////  通过synchronized保证并发有序性 ///////////////////////////////////////////////
    //定义一个对象用于同步块
    static byte[] obj = new byte[0];

    ///////////////////////////////////////////////  通过Lock保证并发有序性 ///////////////////////////////////////////////
    //定义一个Lock锁
    static ReentrantLock reentrantLock = new ReentrantLock(true);

    ///////////////////////////////////////////////  Atomic无法保证 并发有序性 ///////////////////////////////////////////////
    static AtomicReference<String> atomicA1 = new AtomicReference<>("A : x = x + 1");
    static AtomicReference<String> atomicA2 = new AtomicReference<>("A : x = x - 1");
    static AtomicReference<String> atomicB1 = new AtomicReference<>("B : x = x * 2");
    static AtomicReference<String> atomicB2 = new AtomicReference<>("B : x = x / 2");

    /////////////////////////////////////////////// volatile无法保证 并发有序性 ///////////////////////////////////////////////
    static volatile String volatileA1 = new String("A : x = x + 1");
    static volatile String volatileA2 = new String("A : x = x - 1");
    static volatile String volatileB1 = new String("B : x = x * 2");
    static volatile String volatileB2 = new String("B : x = x / 2");

    /**
     * <p>Title: Java并发有序性示例</p>
     *
     * @author 韩超 2018/3/13 17:11
     */
    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Java自带的有序性：在单个线程中，代码是串行执行的。");
        LOGGER.info("Java自带的无序性：在多个线程中，多个线程的代码是交替执行的。");
        LOGGER.info("交替执行：准确的说应该是[交替的串行执行]。");
        LOGGER.info("并发安全的有序性：在单个线程中，代码是串行执行的，在多个线程中，每个单线程的部分是串行的");
        System.out.println();
        /**
         * 0 = 无序          no
         * 1 = synchronized ok
         * 2 = obj          ok
         * 3 = Atomic       no
         * 4 = volatile     no
         */
        int type = 0;
        switch (type) {
            case 0:
                //不采取有序性措施,也没有发生有序性问题.....
                LOGGER.info("不采取措施：单线程串行，视为有序；多线程交叉串行，视为无序。");
                new Thread(() -> {
                    System.out.println(a1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(a2);
                }).start();
                new Thread(() -> {
                    System.out.println(b1);
                    System.out.println(b2);
                }).start();
                break;
            case 1:
                LOGGER.info("通过synchronized保证有序性：成功");
                //通过synchronized保证有序性
                new Thread(() -> {
                    synchronized (obj) {
                        System.out.println(a1);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(a2);
                    }
                }).start();
                new Thread(() -> {
                    synchronized (obj) {
                        System.out.println(b1);
                        System.out.println(b2);
                    }
                }).start();
                break;
            case 2:
                LOGGER.info("通过Lock保证有序性：成功");
                //通过Lock保证有序性
                new Thread(() -> {
                    reentrantLock.lock();
                    System.out.println(a1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(a2);
                    reentrantLock.unlock();
                }).start();
                new Thread(() -> {
                    reentrantLock.lock();
                    System.out.println(b1);
                    System.out.println(b2);
                    reentrantLock.unlock();
                }).start();
                break;
            case 3:
                LOGGER.info("通过Atomic保证有序性：失败。");
                //通过Atomic保证有序性
                new Thread(() -> {
                    reentrantLock.lock();
                    System.out.println(atomicA1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(atomicA2);
                    reentrantLock.unlock();
                }).start();
                new Thread(() -> {
                    reentrantLock.lock();
                    System.out.println(atomicB1);
                    System.out.println(atomicB2);
                    reentrantLock.unlock();
                }).start();
                break;
            case 4:
                LOGGER.info("通过volatile保证有序性：失败。");
                //通过volatile保证有序性
                new Thread(() -> {
                    reentrantLock.lock();
                    System.out.println(volatileA1);
                    System.out.println(volatileA2);
                    reentrantLock.unlock();
                }).start();
                new Thread(() -> {
                    reentrantLock.lock();
                    System.out.println(volatileB1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(volatileB2);
                    reentrantLock.unlock();
                }).start();
                break;
            default:
                break;
        }
    }
}
