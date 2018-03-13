package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Java并发有序性示例</p>
 *
 * @author 韩超 2018/3/13 17:10
 */
public class ConcurrentOrderlyDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentOrderlyDemo.class);
    ///////////////////////////////////////////////  并发有序性 无保障 ///////////////////////////////////////////////
    static int x = 0;
    static int y = 0;
    static int z = 0;
    static int w = 0;

    /**
     * <p>Title: 不采取措施-导致：单线程来看有序，多线程来看无序性</p>
     *
     * @author 韩超 2018/3/13 16:50
     */
    static class DisorderedThread extends Thread {
        public DisorderedThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(super.getName() + ".x = " + x++);
            System.out.println(super.getName() + ".y = " + y++);
            System.out.println(super.getName() + ".z = " + z++);
            System.out.println(super.getName() + ".w = " + w++);
        }
    }

    ///////////////////////////////////////////////  通过synchronized保证并发有序性 ///////////////////////////////////////////////
    //定义一个对象用于同步块
    static byte[] lock = new byte[0];

    /**
     * <p>Title: 使用synchronized保证有序性</p>
     *
     * @author 韩超 2018/3/13 16:53
     */
    static class SynchronizedThread extends Thread {
        public SynchronizedThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(super.getName() + ".x = " + x++);
                System.out.println(super.getName() + ".y = " + y++);
                System.out.println(super.getName() + ".z = " + z++);
                System.out.println(super.getName() + ".w = " + w++);
            }
        }
    }

    ///////////////////////////////////////////////  通过Lock保证并发有序性 ///////////////////////////////////////////////
    //定义一个Lock锁
    static ReentrantLock reentrantLock = new ReentrantLock(true);

    /**
     * <p>Title: 使用Lock保证有序性</p>
     *
     * @author 韩超 2018/3/13 17:02
     */
    static class LockThread extends Thread {
        public LockThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            reentrantLock.lock();
            System.out.println(super.getName() + ".x = " + x++);
            System.out.println(super.getName() + ".y = " + y++);
            System.out.println(super.getName() + ".z = " + z++);
            System.out.println(super.getName() + ".w = " + w++);
            reentrantLock.unlock();
        }
    }

    ///////////////////////////////////////////////  Atomic无法保证 并发有序性 ///////////////////////////////////////////////
    static AtomicInteger atomicX = new AtomicInteger(0);
    static AtomicInteger atomicY = new AtomicInteger(0);
    static AtomicInteger atomicZ = new AtomicInteger(0);
    static AtomicInteger atomicW = new AtomicInteger(0);

    /**
     * <p>Title: 通过Atomic保证有序性：失败</p>
     *
     * @author 韩超 2018/3/13 16:50
     */
    static class AtomicThread extends Thread {
        public AtomicThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(super.getName() + ".x = " + atomicX.getAndIncrement());
            System.out.println(super.getName() + ".y = " + atomicY.getAndIncrement());
            System.out.println(super.getName() + ".z = " + atomicZ.getAndIncrement());
            System.out.println(super.getName() + ".w = " + atomicW.getAndIncrement());
        }
    }

    /////////////////////////////////////////////// volatile无法保证 并发有序性 ///////////////////////////////////////////////
    static int vx = 0;
    static int vy = 0;
    static int vz = 0;
    static int vw = 0;

    /**
     * <p>Title: volatile无法保证 并发有序性 </p>
     *
     * @author 韩超 2018/3/13 16:50
     */
    static class VolatileThread extends Thread {
        public VolatileThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(super.getName() + ".x = " + vx++);
            System.out.println(super.getName() + ".y = " + vy++);
            System.out.println(super.getName() + ".z = " + vz++);
            System.out.println(super.getName() + ".w = " + vw++);
        }
    }

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
         * 9 = 无序
         * 1 = synchronized
         * 2 = lock
         * 3 = Atomic
         * 4 = volatile
         */
        int type = 4;
        switch (type) {
            case 0:
                //不采取有序性措施,也没有发生有序性问题.....
                LOGGER.info("不采取措施：单线程串行，视为有序；多线程交叉串行，视为无序。");
                new AtomicThread("A").start();
                new AtomicThread("B").start();
                new AtomicThread("C").start();
                new AtomicThread("D").start();
                break;
            case 1:
                LOGGER.info("通过synchronized保证有序性：成功");
                //通过synchronized保证有序性
                new SynchronizedThread("A").start();
                new SynchronizedThread("B").start();
                new SynchronizedThread("C").start();
                new SynchronizedThread("D").start();
                break;
            case 2:
                LOGGER.info("通过Lock保证有序性：成功");
                //通过Lock保证有序性
                new LockThread("A").start();
                new LockThread("B").start();
                new LockThread("C").start();
                new LockThread("D").start();
                break;
            case 3:
                LOGGER.info("通过Atomic保证有序性：失败。");
                //通过Atomic保证有序性
                new AtomicThread("A").start();
                new AtomicThread("B").start();
                new AtomicThread("C").start();
                new AtomicThread("D").start();
                break;
            case 4:
                LOGGER.info("通过volatile保证有序性：失败。");
                //通过volatile保证有序性
                new VolatileThread("A").start();
                new VolatileThread("B").start();
                new VolatileThread("C").start();
                new VolatileThread("D").start();
                break;
            default:
                break;
        }
    }
}
