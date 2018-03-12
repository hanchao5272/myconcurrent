package pers.hanchao.concurrent.eg04;

import org.apache.log4j.Logger;

/**
 * <p>java线程基本方法：join</p>
 *
 * @author hanchao 2018/3/11 16:03
 **/
public class ThreadJoinDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadJoinDemo.class);

    //线程间的共享资源
    private static String config = "配置未被初始化.";
    /**
     * <p>join：让当前线程等待指定的线程结束</p>
     *
     * @author hanchao 2018/3/11 16:04
     **/
    public static void main(String[] args) throws InterruptedException {
        //不加join
        LOGGER.info("==========不使用t.join()方法==========");
        new Thread(() -> {
            LOGGER.info("[线程1]开始运行...");
            Thread otherThread = new Thread(() -> {
                LOGGER.info("[线程2]开始运行...");
                try {
                    Thread.sleep(200);//线程2运行200毫秒
                    config = "配置已被[线程2]初始化.";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("[线程2]运行结束");
            });
            //启动线程2
            otherThread.start();
            try {
                Thread.sleep(100);//线程1运行100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("[线程1]获取的配置为：" + config);
            LOGGER.info("[线程1]运行结束");
        }).start();//启动线程1

        //使用t.join()方法
        Thread.sleep(1000);//等待线程1和线程2都有运行完
        System.out.println();
        LOGGER.info("==========使用t.join()方法==========");
        new Thread(() -> {
            LOGGER.info("[线程11]开始运行...");
            Thread otherThread = new Thread(() -> {
                LOGGER.info("[线程22]开始运行...");
                try {
                    Thread.sleep(200);//线程22运行200毫秒
                    config = "配置已被[线程22]初始化.";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("[线程22]运行结束");
            });
            otherThread.start();//启动线程22
            try {
                //在线程11中，加入线程22-->需要等到线程22结束之后才能结束线程11
                otherThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("[线程11]等待[线程22]结束");
            try {
                Thread.sleep(100);//线程11运行100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("[线程1]获取的配置为：" + config);
            LOGGER.info("[线程11]运行结束");
        }).start();//启动线程11
    }
}
