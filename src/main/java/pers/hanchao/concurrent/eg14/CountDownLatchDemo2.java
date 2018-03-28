package pers.hanchao.concurrent.eg14;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>倒计时门闩-倒计时闭锁-学习实例-示例2-战神金刚</p>
 *
 * @author hanchao 2018/3/28 21:18
 **/
public class CountDownLatchDemo2 {
    private static final Logger LOGGER = Logger.getLogger(CountDownLatchDemo2.class);
    /**
     * 用法：
     * - 当count=1时，作为一个开关。所有调用它的wait()方法的线程都在等待， 直到开关打开。        限定开关
     * - 当count=n时，可以作为一个计数器。所有调用它的countDown()方法的线程都会导致count减一，直到为0.    分布计算
     */
    /**
     * 示例2：儿时动画片-战神金刚
     * - 战神金刚有五个狮子组成，这五个狮子可以变形成战神金刚身体的一部分。
     * - 组成的先后顺序是：1.腿、脚、躯干、手臂；2.头
     * - 组装口号：组成腿和脚，组成躯干和手臂， 我来组成头部！
     * - 出发口号：前进，战神金刚！
     */
    static class MachineLion implements Runnable {
        private static final Logger LOGGER = Logger.getLogger(MachineLion.class);
        //身体部分
        private String name;
        //变形计数器
        CountDownLatch latch;

        public MachineLion(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            //花费一些时间进行组装
            Integer time = RandomUtils.nextInt(1000, 2000);
            LOGGER.info(Thread.currentThread().getName() + " [" + name + "] 正在进行组装...");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            LOGGER.info(Thread.currentThread().getName() + " 我来组成[" + name + "]!");
        }
    }

    /**
     * <p>CountDownLatch用法2-线程计数器</p>
     *
     * @author hanchao 2018/3/28 21:34
     **/
    public static void main(String[] args) {
        //main就是战神金刚
        int num = 5;
        //定义 变形计数器
        CountDownLatch latch = new CountDownLatch(num);
        //定义 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(num + 1);
        //五个机器狮子纷纷开始组装
        executorService.submit(new MachineLion("脚部", latch));
        executorService.submit(new MachineLion("腿部", latch));
        executorService.submit(new MachineLion("躯干", latch));
        executorService.submit(new MachineLion("手臂", latch));
        executorService.submit(new MachineLion("头部", latch));
        //等待五个机器狮子进行组装
        LOGGER.info(Thread.currentThread().getName() + " [战神金刚] 正在等待组装...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //战神金刚开始发威
        LOGGER.info(Thread.currentThread().getName() + ": 前进，战神金刚！");
        executorService.shutdownNow();
    }
}
