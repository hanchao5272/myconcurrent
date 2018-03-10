package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>Java并发原子性示例</p>
 * @author hanchao 2018/3/10 13:23
 **/
public class ConcurrentAtomicityDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentAtomicityDemo.class);

    /**
     * <p>原子性示例：操作不是原子性</p>
     * @author hanchao 2018/3/10 14:58
     **/
    static class Increment{
        private int count = 1;
        public void increment(){
            count ++;
        }

        public int getCount() {
            return count;
        }
    }
    /**
     * <p>原子性示例：通过synchronized保证代码块的原子性</p>
     * @author hanchao 2018/3/10 15:07
     **/
    static class SynchronizedIncrement extends Increment{
        /**
         * <p>添加关键字synchronized，使之成为同步方法</p>
         * @author hanchao 2018/3/10 15:12
         **/
        @Override
        public synchronized void increment(){
            super.count ++ ;
        }
    }
    /**
     * <p>原子性示例：通过Lock接口保证指定范围捏代码的原子性</p>
     * @author hanchao 2018/3/10 15:14
     **/
    static class LockIncrement extends Increment{
        //定义个读写锁：锁内运行多线程读，单线程写
        private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        /**
         * <p>运用读写所重写方法</p>
         * @author hanchao 2018/3/10 15:13
         **/
        @Override
        public void increment(){
            //写锁 加锁
            readWriteLock.writeLock().lock();
            try {
                //开始写
                super.count ++ ;
            }finally {
                //将解锁放在finally块中，保证必然执行，防止死锁
                readWriteLock.writeLock().unlock();
            }
        }
    }
    /**
     * <p>原子性示例：通过Atomic类型</p>
     * @author hanchao 2018/3/10 15:19
     **/
    static class AtomicIncrement{
        private AtomicInteger count = new AtomicInteger(1);
        /**
         * <p>无需其他处理，直接自增即可</p>
         * @author hanchao 2018/3/10 15:21
         **/
        public void increment(){
            count.getAndIncrement();
        }

        public AtomicInteger getCount() {
            return count;
        }
    }

    /**
     * <p>Java并发原子性示例</p>
     * @author hanchao 2018/3/10 13:24
     **/
    public static void main(String[] args) throws InterruptedException {
        //变量的原子操作
        LOGGER.info("a = true包含一个操作：1.在工作期间内，将true的值赋给a。所以将常量复制给变量，是一个原子操作。");
        LOGGER.info("a = 5包含一个操作：1.在工作期间内，将5的值赋给a。所以将常量复制给变量，是一个原子操作。");
        LOGGER.info("a = b包含两个操作：1.去主内存读取b的值写入工作内存；2.将b的值赋值给a。所以将变量赋值给变量，不是原子操作: ");
        LOGGER.info("a = b + 2包含三个操作：1.从主内存中读取b写入工作内存；2.在工作内存中b+2；3将b+2的值赋值给a。所以不是原子操作");
        LOGGER.info("a ++即a = a + 1包含三个操作。所以不是原子操作");
        LOGGER.info("综上，JMM只保证了基本的读取和赋值是原子操作。");

        LOGGER.info("Java提供的能够保证更大范围操作原子性的技术有：1.synchronized关键字、2.Lock接口、3.Atomic类型\n");
        //不进行原子性保护的大范围操作
//        Increment increment = new Increment();
//        int begin = increment.getCount();
//        LOGGER.info("count的初始值是：" + increment.getCount());
//        int num = 100000;
//        for (int i = 0; i < num; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    increment.increment();
//                }
//            }).start();
//        }
//        //等待足够长的时间，以便所有的线程都能够运行完
//        Thread.sleep(10000);
//        LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());

        //synchronized关键字能够保证原子性（代码块锁，多线程操作某一对象时，在某个代码块内只能单线程执行）
//        Increment increment = new SynchronizedIncrement();
//        LOGGER.info(increment.getClass());
//        int begin = increment.getCount();
//        LOGGER.info("count的初始值是：" + increment.getCount());
//        int num = 100000;
//        for (int i = 0; i < num; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    increment.increment();
//                }
//            }).start();
//        }
//        //等待足够长的时间，以便所有的线程都能够运行完
//        Thread.sleep(10000);
//        LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());

        //通过Lock接口保证原子性操作
//        Increment increment = new LockIncrement();
//        LOGGER.info(increment.getClass());
//        int begin = increment.getCount();
//        LOGGER.info("count的初始值是：" + increment.getCount());
//        int num = 100000;
//        for (int i = 0; i < num; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    increment.increment();
//                }
//            }).start();
//        }
//        //等待足够长的时间，以便所有的线程都能够运行完
//        Thread.sleep(10000);
//        LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());

        //通过Atomic变量保证变量操作的原子性
        AtomicIncrement increment = new AtomicIncrement();
        LOGGER.info(increment.getClass());
        int begin = increment.getCount().get();
        LOGGER.info("count的初始值是：" + increment.getCount());
        int num = 100000;
        for (int i = 0; i < num; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    increment.increment();
                }
            }).start();
        }
        //等待足够长的时间，以便所有的线程都能够运行完
        Thread.sleep(10000);
        LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());

    }
}
