package pers.hanchao.concurrent.eg04;

import org.apache.log4j.Logger;

/**
 * <p>线程的基本方法:yield(退让)</p>
 *
 * @author hanchao 2018/3/11 16:18
 **/
public class ThreadYieldDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadYieldDemo.class);

    /**
     * <p>定义Runnable接口的实现（在驾校学车，要学会退让）</p>
     * @author hanchao 2018/3/11 16:51
     **/
    static class LearnToDriver implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                //当这个学员练习3次开车之后，尝试把机会让给同级别的学员
                if (i == 3) {
                    System.out.println(Thread.currentThread().getName() + " try to yield..");
                    Thread.currentThread().yield();
                }
                //这个学员正在练习开车
                System.out.println(Thread.currentThread().getName() + " is practicing driving....");
            }
        }
    }

    /**
     * <p>t.yield():让当前线程退让，把CPU让给其他的同样优先级级别线程</p>
     *
     * @author hanchao 2018/3/11 16:19
     **/
    public static void main(String[] args) throws InterruptedException {
        //定义四辆汽车
        Thread thread1 = new Thread(new LearnToDriver(), "银卡会员AAA");
        Thread thread2 = new Thread(new LearnToDriver(), "银卡会员BBB");
        Thread thread3 = new Thread(new LearnToDriver(), "铜卡会员CCC");
        Thread thread4 = new Thread(new LearnToDriver(), "金卡会员DDD");
        //设置优先级
        thread3.setPriority(Thread.MIN_PRIORITY);
        thread4.setPriority(Thread.MAX_PRIORITY);
        //启动所有汽车
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}