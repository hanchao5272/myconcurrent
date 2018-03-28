package pers.hanchao.concurrent.eg14;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>倒计时门闩-倒计时闭锁-学习实例-示例1-跑步比赛</p>
 *
 * @author hanchao 2018/3/28 20:32
 **/
public class CountDownLatchDemo1 {
    /**
     * 用法：
     * - 当count=1时，作为一个开关。所有调用它的wait()方法的线程都在等待， 直到开关打开。        限定开关
     * - 当count=n时，可以作为一个计数器。所有调用它的countDown()方法的线程都会导致count减一，直到为0.    分布计算
     */
    /**
     * 示例1：跑步比赛
     * - 运动员只能在听到哨声之后才一起开始奔跑。
     * - 裁判员吹动哨声
     */
    /**
     * <p>运动员</p>
     *
     * @author hanchao 2018/3/28 20:48
     **/
    static class Player implements Runnable {
        private static final Logger LOGGER = Logger.getLogger(Player.class);
        /**
         * 哨子
         */
        CountDownLatch latch;

        public Player(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            //等待吹哨
            LOGGER.info("Player[" + name + "] is waiting for the whistle.");
            try {
                //注意是await(),不是wait()。
                //前置是CountDownLatch的方法，后者是Object的方法。
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Player[" + name + "] is running...");
            //跑到终点的时间
            Integer time = RandomUtils.nextInt(5000, 9000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Player[" + name + "] is has arrived the finish line!");
        }
    }

    /**
     * <p>裁判员</p>
     *
     * @author hanchao 2018/3/28 20:48
     **/
    static class Referee implements Runnable {
        private static final Logger LOGGER = Logger.getLogger(Referee.class);
        /**
         * 哨子
         */
        CountDownLatch latch;

        public Referee(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            //准备吹哨
            LOGGER.info("Referee[" + name + "] is ready to whistle. 3... 2... 1...!");
            //裁判吹哨--countDown()使count减1，当count=0时，所有在latch上await()的线程不再等待
            latch.countDown();
        }
    }

    /**
     * <p>倒计时门闩-示例</p>
     *
     * @author hanchao 2018/3/28 20:52
     **/
    public static void main(String[] args) throws InterruptedException {
        //示例一：跑步比赛---哨子
        //创建 哨子
        CountDownLatch latch = new CountDownLatch(1);
        //创建 10人赛道
        int num = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        //运动员上赛道
        for (int i = 0; i < num; i++) {
            executorService.submit(new Player(latch));
        }
        //裁判准备
        Thread.sleep(1000);
        //裁判开始吹哨
        new Thread(new Referee(latch)).start();
        //等所有人都跑完，关闭跑道
        Thread.sleep(10000);
        executorService.shutdownNow();
    }
}
