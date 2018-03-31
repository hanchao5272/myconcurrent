package pers.hanchao.concurrent.eg16;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p>Semaphore-信号量-实例</p>
 *
 * @author hanchao 2018/3/29 23:49
 **/
public class SemaphoreBasicDemo {
    /**
     * <p>Semaphore</p>
     *
     * @author hanchao 2018/3/29 23:49
     **/
    public static void main(String[] args) throws InterruptedException {
        //new Semaphore(permits)：初始化许可数量的构造函数
        Semaphore semaphore = new Semaphore(5);

        //new Semaphore(permits,fair):初始化许可数量和是否公平模式的构造函数
        semaphore = new Semaphore(5, true);

        //isFair()：是否公平模式FIFO
        System.out.println("是否公平FIFO：" + semaphore.isFair());

        //availablePermits():获取当前可用的许可数量
        System.out.println("获取当前可用的许可数量：开始---" + semaphore.availablePermits());

        //acquire():获取1个许可
        //---此线程会一直阻塞，直到获取这个许可，或者被中断(抛出InterruptedException异常)。
        semaphore.acquire();
        System.out.println("获取当前可用的许可数量：acquire 1 个---" + semaphore.availablePermits());

        //release()：释放1个许可
        semaphore.release();
        System.out.println("获取当前可用的许可数量：release 1 个---" + semaphore.availablePermits());

        //acquire(permits):获取n个许可
        //---此线程会一直阻塞，直到获取全部n个许可,或者被中断(抛出InterruptedException异常)。
        semaphore.acquire(2);
        System.out.println("获取当前可用的许可数量：acquire 2 个---" + semaphore.availablePermits());

        //release(permits):释放n个许可
        semaphore.release(2);
        System.out.println("获取当前可用的许可数量：release 1 个---" + semaphore.availablePermits());

        //其他的获取许可的方式
        //otherAcquireMethod(semaphore);

        //hasQueuedThreads():是否有正在等待许可的线程
        System.out.println("是否有正在等待许可的线程：" + semaphore.hasQueuedThreads());

        //getQueueLength():正在等待许可的队列长度(线程数量)
        System.out.println("正在等待许可的队列长度(线程数量)：" + semaphore.getQueueLength());

        Thread.sleep(10);
        System.out.println();
        //定义final的信号量
        Semaphore finalSemaphore = semaphore;
        new Thread(() -> {
            //drainPermits():获取剩余的所有的许可
            int permits = finalSemaphore.drainPermits();
            System.out.println(Thread.currentThread().getName() + "获取了剩余的全部" + permits + "个许可.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //释放所有的许可
            finalSemaphore.release(permits);
            System.out.println(Thread.currentThread().getName() + "释放了" + permits + "个许可.");
        }).start();

        Thread.sleep(10);
        new Thread(() -> {
            try {
                //有一个线程正在等待获取1个许可
                finalSemaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "获取了1个许可.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //释放1个许可
            finalSemaphore.release();
            System.out.println(Thread.currentThread().getName() + "释放了1个许可.");

        }).start();
        Thread.sleep(10);
        System.out.println();
        System.out.println("获取当前可用的许可数量：drain 剩余的---" + finalSemaphore.availablePermits());
        System.out.println("是否有正在等待许可的线程：" + finalSemaphore.hasQueuedThreads());
        System.out.println("正在等待许可的队列长度(线程数量)：" + finalSemaphore.getQueueLength());
        System.out.println();

        Thread.sleep(10);
        new Thread(() -> {
            try {
                //有一个线程正在等待获取2个许可
                finalSemaphore.acquire(2);
                System.out.println(Thread.currentThread().getName() + "获取了2个许可.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //释放两个许可
            finalSemaphore.release(2);
            System.out.println(Thread.currentThread().getName() + "释放了2个许可.");
        }).start();
        Thread.sleep(10);
        System.out.println();
        System.out.println("获取当前可用的许可数量：drain 剩余的---" + finalSemaphore.availablePermits());
        System.out.println("是否有正在等待许可的线程：" + finalSemaphore.hasQueuedThreads());
        System.out.println("正在等待许可的队列长度(线程数量)：" + finalSemaphore.getQueueLength());
        System.out.println();

        Thread.sleep(5000);
        System.out.println();
        System.out.println("获取当前可用的许可数量：---" + finalSemaphore.availablePermits());
        System.out.println("是否有正在等待许可的线程：" + finalSemaphore.hasQueuedThreads());
        System.out.println("正在等待许可的队列长度(线程数量)：" + finalSemaphore.getQueueLength());
    }

    /**
     * <p>其他的 acquire方法--注意：是否阻塞、是否可中断、是否可限时、1个还是n个</p>
     *
     * @author hanchao 2018/3/31 19:17
     **/
    private static void otherAcquireMethod(Semaphore semaphore) throws InterruptedException {
        //acquierUninterruptibly():不可中断地获取1个许可
        //---此线程会一直阻塞，直到获取这个许可；中断无效。
        semaphore.acquireUninterruptibly();

        //acquireUninterruptibly():不可中断地获取n个许可
        //此线程会一直阻塞，直到获取全部n个许可；中断无效。
        semaphore.acquireUninterruptibly(2);

        //tryAcquire():尝试获取1个许可
        //---此线程在这个方法被调用时，进行一次获取1个许可的尝试。
        //---如果获取到这个许可，则返回true；反之返回false，并不再等待。
        semaphore.tryAcquire();

        //tryAcquire(permits):尝试获取n个许可
        //---此线程在这个方法被调用时，进行一次获n个许可的尝试。
        //---如果获取到全部n个许可，则返回true；反之返回false，并不再等待。
        semaphore.tryAcquire();

        //tryAcquire(timeout,TimeUnit):限时尝试获取1个许可
        //---此线程在这个方法被调用时，会在限定时间内去尝试获取1个许可。
        //---如果获取到这个许可，则返回true；如果限时超时，则返回false；如果被中断，则抛出InterruptedException异常。
        semaphore.tryAcquire(1, TimeUnit.SECONDS);

        //tryAcquire(permits,timeout,TimeUnit):限时尝试获取n个许可
        //---此线程在这个方法被调用时，会在限定时间内去尝试获取n个许可。
        //---如果获取到这n个许可，则返回true；如果限时超时，则返回false；如果被中断，则抛出InterruptedException异常。
        semaphore.tryAcquire(2, 1, TimeUnit.SECONDS);
    }
}
