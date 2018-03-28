package pers.hanchao.concurrent.eg14;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p>倒计时门闩-CountDownLatch-基本方法</p>
 *
 * @author hanchao 2018/3/28 21:47
 **/
public class CountDownLatchBasic {
    private static final Logger LOGGER = Logger.getLogger(CountDownLatchBasic.class);

    /**
     * <p>基本方法</p>
     *
     * @author hanchao 2018/3/28 21:47
     **/
    public static void main(String[] args) throws InterruptedException {
        //基本方法
        //数量
        int num = 2;
        //构造函数：定义一个count=num的CountDownLatch对象。
        CountDownLatch countDownLatch = new CountDownLatch(num);
        //toString()
        LOGGER.info("通过toString()获取字符串描述：" + countDownLatch.toString());
        //getCount()
        LOGGER.info("通过getCount()获取当前的count值：" + countDownLatch.getCount());
        //latch.countDown()
        countDownLatch.countDown();
        LOGGER.info("通过countDown()进行一次倒计时，即count减1：" + countDownLatch.getCount());
        countDownLatch.countDown();
        LOGGER.info("通过countDown()进行一次倒计时，即count减1：" + countDownLatch.getCount());
        countDownLatch.countDown();
        LOGGER.info("通过countDown()进行一次倒计时，即count减1：" + countDownLatch.getCount());
        LOGGER.info("count最小为0");

        //await()/await() 与countDown()
        /**
         *   线程          等待方法               终止原因
         * ------------------------------------------------
         * thread-0     await()                 count=1
         * thread-1     await()                 interrupted
         * ------------------------------------------------
         * thread-2     await(long,TimeUnit)    timeout
         * thread-3     await(long,TimeUnit)    interrupted
         * thread-4     await(long,TimeUnit)    count=1
         */
        System.out.println();
        CountDownLatch latch = new CountDownLatch(num);
        //await() 直至latch 的count = 0
        LOGGER.info("通过await()等待至latch的count=0，或者被中断。");
        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "(await0) is awaiting....");
            try {
                latch.await();
                LOGGER.info(Thread.currentThread().getName() + "(await0) is terminated because the latch's count is zero.");
            } catch (InterruptedException e) {
                LOGGER.info(Thread.currentThread().getName() + "(await0) is interrupted.");
                //e.printStackTrace();
            }
        }).start();
        //await() 被中断
        Thread thread = new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "(await1) is awaiting....");
            try {
                latch.await();
                LOGGER.info(Thread.currentThread().getName() + "(await1) is terminated because the latch's count is zero.");
            } catch (InterruptedException e) {
                LOGGER.info(Thread.currentThread().getName() + "(await1) is terminated because it is interrupted.");
                //e.printStackTrace();
            }
        });
        thread.start();

        //await(timeout,TimeUnit)
        //await(timeout,TimeUnit) timeout
        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "(await2) is awaiting....");
            try {
                //等待2秒
                boolean result = latch.await(1, TimeUnit.SECONDS);
                //如果等到了count=0，则返回true，并停止等待
                if (result) {//如果返回结果为true,表示在等待时间耗尽之前等到了count=0
                    LOGGER.info(Thread.currentThread().getName() + "(await2) is terminated because the latch's count is zero.");
                } else {//如果返回结果为false,表示等待时间耗尽,也没有等到count=0
                    LOGGER.info(Thread.currentThread().getName() + "(await2) is terminated because the the waiting time is timeout.");
                }
            } catch (InterruptedException e) {
                LOGGER.info(Thread.currentThread().getName() + "(await2) is terminated because it is interrupted.");
                //e.printStackTrace();
            }
        }).start();
        //await(timeout,TimeUnit)

        Thread thread1 = new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "(await3) is awaiting....");
            try {
                //等待3秒
                boolean result = latch.await(5, TimeUnit.SECONDS);
                //如果等到了count=0，则返回true，并停止等待
                if (result) {//如果返回结果为true,表示在等待时间耗尽之前等到了count=0
                    LOGGER.info(Thread.currentThread().getName() + "(await3) is terminated because the latch's count is zero.");
                } else {//如果返回结果为false,表示等待时间耗尽,也没有等到count=0
                    LOGGER.info(Thread.currentThread().getName() + "(await3) is terminated because the the waiting time is timeout.");
                }
            } catch (InterruptedException e) {
                LOGGER.info(Thread.currentThread().getName() + "(await3) is terminated because it is interrupted.");
                //e.printStackTrace();
            }
        });
        thread1.start();
        //await(timeout,TimeUnit) count=0
        new Thread(() -> {
            LOGGER.info(Thread.currentThread().getName() + "(await4) is awaiting....");
            try {
                //等待3秒
                boolean result = latch.await(5, TimeUnit.SECONDS);
                //如果等到了count=0，则返回true，并停止等待
                if (result) {//如果返回结果为true,表示在等待时间耗尽之前等到了count=0
                    LOGGER.info(Thread.currentThread().getName() + "(await4) is terminated because the latch's count is zero.");
                } else {//如果返回结果为false,表示等待时间耗尽,也没有等到count=0
                    LOGGER.info(Thread.currentThread().getName() + "(await4) is terminated because the the waiting time is timeout.");
                }
            } catch (InterruptedException e) {
                LOGGER.info(Thread.currentThread().getName() + "(await4) is interrupted.");
                //e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        System.out.println();
        //等待4秒
        Thread.sleep(2000);
        System.out.println();
        //中断
        thread.interrupt();
        thread1.interrupt();
        Thread.sleep(1000);
        System.out.println();
        //进行自减，直到count=0
        while (latch.getCount() > 0) {
            LOGGER.info(Thread.currentThread().getName() + " latch.countDown , count = " + latch.getCount());
            latch.countDown();
        }
    }
}
