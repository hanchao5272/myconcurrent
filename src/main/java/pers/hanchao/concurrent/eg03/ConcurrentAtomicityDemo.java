package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>Java并发原子性示例</p>
 *
 * @author hanchao 2018/3/10 13:23
 **/
public class ConcurrentAtomicityDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentAtomicityDemo.class);

    /**
     * <p>原子性示例：不是原子性</p>
     *
     * @author hanchao 2018/3/10 14:58
     **/
    static class Increment {
        private int count = 1;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * <p>原子性示例：通过synchronized保证代码块的原子性</p>
     *
     * @author hanchao 2018/3/10 15:07
     **/
    static class SynchronizedIncrement extends Increment {
        /**
         * <p>添加关键字synchronized，使之成为同步方法</p>
         *
         * @author hanchao 2018/3/10 15:12
         **/
        @Override
        public synchronized void increment() {
            super.count++;
        }
    }

    /**
     * <p>原子性示例：通过Lock接口保证指定范围代码的原子性</p>
     *
     * @author hanchao 2018/3/10 15:14
     **/
    static class LockIncrement extends Increment {
        //定义个读写锁：锁内运行多线程读，单线程写
        private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

        /**
         * <p>运用读写所重写方法</p>
         *
         * @author hanchao 2018/3/10 15:13
         **/
        @Override
        public void increment() {
            //写锁 加锁
            readWriteLock.writeLock().lock();
            try {
                //开始写
                super.count++;
            } finally {
                //将解锁放在finally块中，保证必然执行，防止死锁
                readWriteLock.writeLock().unlock();
            }
        }
    }

    /**
     * <p>原子性示例：通过Atomic类型保证类型的原子性</p>
     *
     * @author hanchao 2018/3/10 15:19
     **/
    static class AtomicIncrement {
        private AtomicInteger count = new AtomicInteger(1);

        /**
         * <p>无需其他处理，直接自增即可</p>
         *
         * @author hanchao 2018/3/10 15:21
         **/
        public void increment() {
            count.getAndIncrement();
        }

        public AtomicInteger getCount() {
            return count;
        }
    }

    /**
     * <p>Java并发原子性示例</p>
     *
     * @author hanchao 2018/3/10 13:24
     **/
    public static void main(String[] args) throws InterruptedException {
        //变量的原子操作
        LOGGER.info("a = true包含一个操作：1.在工作期间内，将true的值赋给a。所以将常量复制给变量，是一个原子操作。");
        LOGGER.info("a = 5包含一个操作：1.在工作期间内，将5的值赋给a。所以将常量复制给变量，是一个原子操作。");
        LOGGER.info("a = b包含两个操作：1.去主内存读取b的值写入工作内存；2.将b的值赋值给a。所以将变量赋值给变量，不是原子操作: ");
        LOGGER.info("a = b + 2包含三个操作：1.从主内存中读取b写入工作内存；2.在工作内存中b+2；3将b+2的值赋值给a。所以不是原子操作");
        LOGGER.info("a ++即a = a + 1包含三个操作。所以不是原子操作");
        LOGGER.info("综上，JMM只保证了基本的读取和赋值是原子操作。\n");
        LOGGER.info("Java提供的能够保证更大范围操作原子性的技术有：1.synchronized关键字、2.Lock接口、3.Atomic类型\n");


        /**
         * 0 = 普通共享变量   没有原子性
         * 1 = synchronized ok
         * 2 = Lock         ok
         * 3 = Atomic       ok
         */
        int type = 0;//类型
        int num = 50000;//自增次数
        int sleepTime = 5000;//等待计算时间
        int begin;//开始的值
        Increment increment;
        switch (type) {
            case 0:
                //不进行原子性保护的大范围操作
                increment = new Increment();
                begin = increment.getCount();
                LOGGER.info("Java中普通的自增操作不是原子性操作。");
                LOGGER.info("当前运行类：" +increment.getClass().getSimpleName() +  "，count的初始值是：" + increment.getCount());
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment.increment();
                    }).start();
                }
                //等待足够长的时间，以便所有的线程都能够运行完
                Thread.sleep(sleepTime);
                LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());
                break;
            case 1:
                //synchronized关键字能够保证原子性（代码块锁，多线程操作某一对象时，在某个代码块内只能单线程执行）
                increment = new SynchronizedIncrement();
                begin = increment.getCount();
                LOGGER.info("可以通过synchronized关键字保障代码的原子性");
                LOGGER.info("当前运行类：" +increment.getClass().getSimpleName() +  "，count的初始值是：" + increment.getCount());
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment.increment();
                    }).start();
                }
                //等待足够长的时间，以便所有的线程都能够运行完
                Thread.sleep(sleepTime);
                LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());

                break;
            case 2:
                //通过Lock接口保证原子性操作
                increment = new LockIncrement();
                begin = increment.getCount();
                LOGGER.info("可以通过Lock接口保证代码的原子性");
                LOGGER.info("当前运行类：" +increment.getClass().getSimpleName() +  "，count的初始值是：" + increment.getCount());
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment.increment();
                    }).start();
                }
                //等待足够长的时间，以便所有的线程都能够运行完
                Thread.sleep(sleepTime);
                LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment.getCount());
                break;
            case 3:
                //通过Atomic变量保证变量操作的原子性
                AtomicIncrement increment1 = new AtomicIncrement();
                begin = increment1.getCount().get();
                LOGGER.info("可以通过Atomic类型保证变量的原子性");
                LOGGER.info("当前运行类：" +increment1.getClass().getSimpleName() +  "，count的初始值是：" + increment1.getCount());
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment1.increment();
                    }).start();
                }
                //等待足够长的时间，以便所有的线程都能够运行完
                Thread.sleep(sleepTime);
                LOGGER.info("进过" + num + "次自增，count应该 = " + (begin + num) + ",实际count = " + increment1.getCount());
                break;
            default:
                break;
        }
    }
}
