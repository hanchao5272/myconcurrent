package pers.hanchao.concurrent.eg04;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * <p>线程的状态转换</p>
 *
 * @author hanchao 2018/3/12 18:22
 **/
public class ThreadStateDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadStateDemo.class);

    /**
     * <p>线程间的状态转换</p>
     *
     * @author hanchao 2018/3/12 18:45
     **/
    public static void main(String[] args) throws InterruptedException {
        //线程的六种状态
        LOGGER.info("======线程的六种状态======");
        LOGGER.info("线程-初始状态：" + Thread.State.NEW);
        LOGGER.info("线程-就绪状态：" + Thread.State.RUNNABLE);
        LOGGER.info("线程-阻塞状态：" + Thread.State.BLOCKED);
        LOGGER.info("线程-等待状态：" + Thread.State.WAITING);
        LOGGER.info("线程-限时等待状态：" + Thread.State.TIMED_WAITING);
        LOGGER.info("线程-终止状态：" + Thread.State.TERMINATED + "\n");
        /////////////////////////////////////////////// TIME_WAITING ///////////////////////////////////////////////
        //线程状态间的状态转换：NEW->RUNNABLE->TIME_WAITING->RUNNABLE->TERMINATED
        LOGGER.info("======线程状态间的状态转换NEW->RUNNABLE->TIME_WAITING->RUNNABLE->TERMINATED======");
        //定义一个内部线程
        Thread thread = new Thread(() -> {
            LOGGER.info("2.执行thread.start()之后，线程的状态：" + Thread.currentThread().getState());
            try {
                //休眠100毫秒
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("4.执行Thread.sleep(long)完成之后，线程的状态：" + Thread.currentThread().getState());
        });
        //获取start()之前的状态
        LOGGER.info("1.通过new初始化一个线程，但是还没有start()之前，线程的状态：" + thread.getState());
        //启动线程
        thread.start();
        //休眠50毫秒
        Thread.sleep(50);
        //因为thread1需要休眠100毫秒，所以在第50毫秒，thread1处于sleep状态
        LOGGER.info("3.执行Thread.sleep(long)时，线程的状态：" + thread.getState());
        //thread1和main线程主动休眠150毫秒，所以在第150毫秒,thread1早已执行完毕
        Thread.sleep(100);
        LOGGER.info("5.线程执行完毕之后，线程的状态：" + thread.getState() + "\n");

        /////////////////////////////////////////////// WAITING ///////////////////////////////////////////////
        Thread.sleep(100);
        //线程状态间的状态转换：NEW->RUNNABLE->WAITING->RUNNABLE->TERMINATED
        LOGGER.info("======线程状态间的状态转换NEW->RUNNABLE->WAITING->RUNNABLE->TERMINATED======");
        //定义一个对象，用来加锁和解锁
        AtomicBoolean obj = new AtomicBoolean(false);
        //定义一个内部线程
        Thread thread1 = new Thread(() -> {
            LOGGER.info("2.执行thread.start()之后，线程的状态：" + Thread.currentThread().getState());
            synchronized (obj) {
                try {
                    //thread1需要休眠100毫秒
                    Thread.sleep(100);
                    //thread1100毫秒之后，通过wait()方法释放obj对象是锁
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("4.被object.notify()方法唤醒之后，线程的状态：" + Thread.currentThread().getState());
        });
        //获取start()之前的状态
        LOGGER.info("1.通过new初始化一个线程，但是还没有start()之前，线程的状态：" + thread1.getState());
        //启动线程
        thread1.start();
        //main线程休眠150毫秒
        Thread.sleep(150);
        //因为thread1在第100毫秒进入wait等待状态，所以第150秒肯定可以获取其状态
        LOGGER.info("3.执行object.wait()时，线程的状态：" + thread1.getState());
        //声明另一个线程进行解锁
        new Thread(() -> {
            synchronized (obj) {
                //唤醒等待的线程
                obj.notify();
            }
        }).start();
        //main线程休眠10毫秒等待thread1线程能够苏醒
        Thread.sleep(10);
        //获取thread1运行结束之后的状态
        LOGGER.info("5.线程执行完毕之后，线程的状态：" + thread1.getState() + "\n");

        /////////////////////////////////////////////// BLOCKED ///////////////////////////////////////////////
        Thread.sleep(100);
        //线程状态间的状态转换：NEW->RUNNABLE->BLOCKED->RUNNABLE->TERMINATED
        LOGGER.info("======线程状态间的状态转换NEW->RUNNABLE->BLOCKED->RUNNABLE->TERMINATED======");
        //定义一个对象，用来加锁和解锁
        AtomicBoolean obj2 = new AtomicBoolean(false);
        //定义一个线程，先抢占了obj2对象的锁
        new Thread(() -> {
            synchronized (obj2) {
                try {
                    //第一个线程要持有锁100毫秒
                    Thread.sleep(100);
                    //然后通过wait()方法进行等待状态，并释放obj2的对象锁
                    obj2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //定义目标线程，获取等待获取obj2的锁
        Thread thread3 = new Thread(() -> {
            LOGGER.info("2.执行thread.start()之后，线程的状态：" + Thread.currentThread().getState());
            synchronized (obj2) {
                try {
                    //thread3要持有对象锁100毫秒
                    Thread.sleep(100);
                    //然后通过notify()方法唤醒所有在ojb2上等待的线程继续执行后续操作
                    obj2.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("4.阻塞结束后，线程的状态：" + Thread.currentThread().getState());
        });
        //获取start()之前的状态
        LOGGER.info("1.通过new初始化一个线程，但是还没有thread.start()之前，线程的状态：" + thread3.getState());
        //启动线程
        thread3.start();
        //先等100毫秒
        Thread.sleep(50);
        //第一个线程释放锁至少需要100毫秒，所以在第50毫秒时，thread3正在因等待obj的对象锁而阻塞
        LOGGER.info("3.因为等待锁而阻塞时，线程的状态：" + thread3.getState());
        //再等300毫秒
        Thread.sleep(300);
        //两个线程的执行时间加上之前等待的50毫秒以供250毫秒，所以第300毫秒，所有的线程都已经执行完毕
        LOGGER.info("5.线程执行完毕之后，线程的状态：" + thread3.getState());
    }
}
