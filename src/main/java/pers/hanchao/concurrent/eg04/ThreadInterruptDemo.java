package pers.hanchao.concurrent.eg04;

import org.apache.log4j.Logger;

/**
 * <p>线程：interrupt(中断线程的block状态)</p>
 *
 * @author hanchao 2018/3/11 17:07
 **/
public class ThreadInterruptDemo {

    private static final Logger LOGGER = Logger.getLogger(ThreadInterruptDemo.class);

    /**
     * <p>线程：interrupt(中断线程的block状态)</p>
     *
     * @author hanchao 2018/3/11 17:09
     **/
    public static void main(String[] args) throws InterruptedException {
        //interrupt()并不是中断线程
        //interrupt():中断线程的阻塞(sleep/join/wait)状态，并将 中断标志位 置为 true
        //所以如果是普通的运行中的线程，流程并不会受到影响
        LOGGER.info("===========interrupt()并不是中断线程,只是将线程的 中断标志位 置为true");
        new Thread(() -> {
            Thread thread = Thread.currentThread();
            for (int i = 0; i < 5; i++) {
                LOGGER.info("线程[" + thread.getName() + "]: i = " + i + ",isInterrupted = " + thread.isInterrupted());
                if (i == 3) {
                    thread.interrupt();
                }
            }
            LOGGER.info("线程[" + Thread.currentThread().getName() + "]: 停止运行" + ",isInterrupted = " + thread.isInterrupted());
        }).start();

        //interrupt()、isInterrupted()可以结合，控制线程中的for(无阻塞状态)循环
        Thread.sleep(200);
        System.out.println();
        LOGGER.info("===========interrupt()、isInterrupted()可以结合，控制线程中的for(无阻塞状态)循环");
        new Thread(() -> {
            Thread thread = Thread.currentThread();
            for (int i = 0; i < 5 && !thread.isInterrupted(); i++) {
                LOGGER.info("线程[" + thread.getName() + "] is running, i = " + i + ",isInterrupted = " + thread.isInterrupted());
                if (i == 3) {
                    thread.interrupt();
                    LOGGER.info("线程[" + thread.getName() + "] is isInterrupted, i = " + i + ", isInterrupted = " + thread.isInterrupted());
                }
            }
            LOGGER.info("线程[" + Thread.currentThread().getName() + "] 停止运行");
        }).start();

        //interrupt()、isInterrupted()可以结合，控制线程中的while(无阻塞状态)循环
        Thread.sleep(200);
        System.out.println();
        LOGGER.info("在无阻塞状态(sleep/wait/joni)的while循环中应用interrupt()和isInterrupted()");
        Thread thread1 = new Thread(() -> {
            //如果当前线程没被中断，则一直进行
            while (!Thread.currentThread().isInterrupted()) {
                LOGGER.info("线程[" + Thread.currentThread().getName() + "]正在运行...");
            }
            LOGGER.info("线程[" + Thread.currentThread().getName() + "]停止运行");
        });
        thread1.start();
        Thread.sleep(10);
        thread1.interrupt();

        //中断有阻塞状态(sleep/wait/joni)的线程，会产生一个InterruptedException异常，并将中断标志位还原为false，所以不会结束线程
        //所以在有阻塞状态(sleep/wait/joni)的while循环，只是应用interrupt()和isInterrupted()的结合，虽然会报错，但并不能停止线程
        Thread.sleep(200);
        System.out.println();
        LOGGER.info("在有阻塞状态(sleep/wait/joni)的while循环中应用interrupt()和isInterrupted()");
        Thread thread2 = new Thread(() -> {
            //如果当前线程没被中断，则一直进行
            while (!Thread.currentThread().isInterrupted()) {
                LOGGER.info("线程[" + Thread.currentThread().getName() + "]正在运行...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("线程[" + Thread.currentThread().getName() + "]停止运行");
        });
        thread2.start();
        Thread.sleep(20);
        thread2.interrupt();

        //中断有阻塞状态(sleep/wait/joni)的线程，会产生一个InterruptedException异常，并将中断标志位还原为false，所以不会结束线程
        //所以在有阻塞状态(sleep/wait/joni)的while循环，除了应用interrupt()和isInterrupted()的结合外，还需要在外层catch这个异常，才能够停止线程
        Thread.sleep(200);
        System.out.println();
        LOGGER.info("在有阻塞状态(sleep/wait/joni)的while循环中应用interrupt()和isInterrupted()+catch");
        Thread thread3 = new Thread(() -> {
            try {
                //如果当前线程没被中断，则一直进行
                while (!Thread.currentThread().isInterrupted()) {
                    LOGGER.info("线程[" + Thread.currentThread().getName() + "]正在运行...");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                LOGGER.info("线程[" + Thread.currentThread().getName() + "]停止运行");
                //没有必要打印错误，因为这是故意让程序产生的错误
                //e.printStackTrace();
            }
        });
        thread3.start();
        Thread.sleep(2000);
        thread3.interrupt();
    }
}
