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
        /**
         * 1.t.yield()方法针对的是running状态的线程
         * 2.t.yield()方法：t线程告诉CPU，它愿意将CPU资源让出
         * 3.调用了t.yield()并不一定100%让出资源，取决于CPU的调度情况
         * 4.t.yield()只会将资源让给同样优先级基本的线程
         */

        //定义四辆汽车
        Thread thread1 = new Thread(new LearnToDriver(), "银卡会员AAA");
        Thread thread2 = new Thread(new LearnToDriver(), "银卡会员BBB");
        Thread thread3 = new Thread(new LearnToDriver(), "铜卡会员CCC");
        Thread thread4 = new Thread(new LearnToDriver(), "金卡会员DDD");
        //设置优先级，线程1、2默认优先级为5=Thread.NORM_PRIORITY，无需修改
        thread3.setPriority(Thread.MIN_PRIORITY);
        thread4.setPriority(Thread.MAX_PRIORITY);
        //启动所有汽车
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
