package pers.hanchao.concurrent.eg91;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>线程控制：synchronized + wait + notify</p>
 *
 * @author hanchao 2018/3/26 21:28
 **/
public class ThreadControlDemo {
    /**
     * 定义一个线程安全的队列去存放这些产品
     */
    static BlockingQueue<String> goodQueue = new LinkedBlockingQueue(400);
    /**
     * 定义产品数量
     */
    static Integer maxSize = 100;

    /**
     * <p>循环顺序打印ABCD</p>
     *
     * @author hanchao 2018/3/26 22:22
     **/
    static class GoodProducer implements Runnable {

        private String value;//产品名称
        private byte[] preLock;//前置锁
        private byte[] selfLock;//自身锁

        public GoodProducer(String value, byte[] preLock, byte[] selfLock) {
            this.value = value;
            this.preLock = preLock;
            this.selfLock = selfLock;
        }

        @Override
        public void run() {
            /** 用原子变量计数，保证原子性*/
            AtomicInteger count = new AtomicInteger(maxSize);
            //循环
            while (count.get() > 0) {
                //获取前面那个产品的锁
                synchronized (preLock) {
                    //获取自身的锁
                    synchronized (selfLock) {
                        //存放产品
                        try {
                            goodQueue.put(value);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //自减
                        count.decrementAndGet();
                        //唤醒锁住 自身锁的 其他线程
                        selfLock.notifyAll();
                    }
                    //是否前面那个产品的锁
                    try {
                        preLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " is terminated.");
        }
    }

    /**
     * <p></p>
     *
     * @author hanchao 2018/3/26 21:37
     **/
    public static void main(String[] args) throws InterruptedException {
        //以byte[]定义四个对象作为锁
        byte[] lockA = new byte[0];
        byte[] lockB = new byte[0];
        byte[] lockC = new byte[0];
        byte[] lockD = new byte[0];

        //获取开始时间
        Long begin = System.currentTimeMillis();

        System.out.println("================ begin time " + begin);
        //定义四个线程
        Thread t0 = new Thread(new GoodProducer("A", lockD, lockA));
        Thread t1 = new Thread(new GoodProducer("B", lockA, lockB));
        Thread t2 = new Thread(new GoodProducer("C", lockB, lockC));
        Thread t3 = new Thread(new GoodProducer("D", lockC, lockD));

        //启动四个线程
        new Thread(t0).start();
        Thread.sleep(5);
        new Thread(t1).start();
        Thread.sleep(5);
        new Thread(t2).start();
        Thread.sleep(5);
        new Thread(t3).start();
        Thread.sleep(5);

        //定义结束时间
        final Long[] end = new Long[1];
        //定义一个线程作为经销商E去仓库取货
        new Thread(() -> {
            /** 用原子变量计数，保证原子性*/
            AtomicInteger count = new AtomicInteger(maxSize * 4);
            while (count.get() > 0) {
                //取出一个产品
                System.out.print(goodQueue.poll());
                //次数自减
                count.decrementAndGet();
            }
            end[0] = System.currentTimeMillis();
        }).start();

        //等待500ms,肯定运行完了
        Thread.sleep(500);
        System.out.println("\n================ end time " + end[0]);
        System.out.println("================ use time " + (end[0] - begin) + "ms");
    }
}
