package pers.hanchao.concurrent.eg04;

import org.apache.log4j.Logger;

/**
 * <p>线程的基本方法(不包括线程控制方法)</p>
 *
 * @author hanchao 2018/3/11 12:07
 **/
public class ThreadBasicDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadBasicDemo.class);

    /**
     * <p>线程的基本方法</p>
     *
     * @author hanchao 2018/3/11 12:08
     **/
    public static void main(String[] args) throws InterruptedException {
        //通过Thread.sleep(ms)，指定当前线程进行休眠(但是并没有释放锁)
        LOGGER.info("通过Thread.sleep(ms)，指定当前线程进行休眠(但是并没有释放锁)");
        LOGGER.info("现在时间:" + System.currentTimeMillis() + ",main线程即将休眠1000ms.");
        Thread.sleep(1000);
        LOGGER.info("休眠结束:" + System.currentTimeMillis() + "\n");

        //通过Thread.activeCount()，获取程序活着的线程数。
        LOGGER.info("通过Thread.activeCount()，获取程序活着的线程数");
        LOGGER.info("当前程序活着的线程数:" + Thread.activeCount() + ",一个是main线程，一个是GC线程。");
        //添加一个线程
        new Thread(() -> {
            try {
                Thread.sleep(10);//存活10毫秒，方便后续代码能够统计到此线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        LOGGER.info("添加一个匿名线程之后，活着的线程数:" + Thread.activeCount());
        Thread.sleep(15);//等着内部匿名线程死掉
        LOGGER.info("匿名线程死掉之后，活着的线程数:" + Thread.activeCount() + "\n");

        new Thread(() -> {
            //通过Thread.currentThread()获取当前线程
            LOGGER.info("通过Thread.currentThread()获取当前线程");
            Thread currentThread = Thread.currentThread();
            LOGGER.info("当前线程：" + currentThread);
            LOGGER.info("当前线程-名字(thread.getName())：" + currentThread.getName());
            LOGGER.info("当前线程-优先级(thread.getPriority())：" + currentThread.getPriority());
            LOGGER.info("当前线程-线程组名字：" + currentThread.getThreadGroup().getName() + "\n");

            //通过thread.setName(name)设置线程名
            LOGGER.info("通过thread.setName(name)设置线程名");
            currentThread.setName("张三");
            //通过thread.setPriority(priority)设置优先级
            LOGGER.info("通过thread.setPriority(priority)设置优先级");
            currentThread.setPriority(Thread.MAX_PRIORITY);
            LOGGER.info("修改名字和优先级之后：" + currentThread);

            //通过thread.isAlive()判断当前线程是否还活着
            LOGGER.info("通过thread.isAlive()判断当前线程是否还活着:" + currentThread.isAlive() + "\n");
        }).start();

        Thread.sleep(100);
        LOGGER.info("所谓[守护线程],可以理解为后台线程或非用户线程。");
        LOGGER.info("当前程序中，只剩下main线程和守护线程，且main线程执行完毕时，系统结束。");
        //守护线程
        Thread normalThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    LOGGER.info("normalThread线程正在工作...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread daemonThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    LOGGER.info("daemonThread线程正在工作...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        LOGGER.info("通过thread.isDaemon()判断当前线程[normalThread]是否是守护线程：" + normalThread.isDaemon());
        LOGGER.info("通过thread.isDaemon()判断当前线程[daemonThread]是否是守护线程：" + daemonThread.isDaemon());
        LOGGER.info("通过thread.setDaemon(true)将指定线程设置为守护线程，随main线程结束而结束。");
        daemonThread.setDaemon(true);
        LOGGER.info("通过thread.isDaemon()判断当前线程[daemonThread]是否是守护线程：" + daemonThread.isDaemon());
        if (false) {
            normalThread.start();
        } else {
            daemonThread.start();
        }

        Thread.sleep(2000);//等待线程死掉
        LOGGER.info("normalThread是否存活：" + normalThread.isAlive());
        LOGGER.info("daemonThread是否存活：" + daemonThread.isAlive());
    }
}
