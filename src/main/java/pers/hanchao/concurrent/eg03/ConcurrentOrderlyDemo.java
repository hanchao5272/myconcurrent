package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Java并发有序性示例</p>
 * @author hanchao 2018/3/10 21:46
 **/
public class ConcurrentOrderlyDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentOrderlyDemo.class);

    private static int[][] config = new int[100000][6];
    //获取配置
    static void useConfig(int index){
        if(5 != config[index][5])
            LOGGER.info("在第" + index + "个线程中，此时config[index][0] = " + config[index][0] + ",config[index][5] = " + config[index][5]);
        if(4 != config[index][4]) {
            LOGGER.info("在第" + index + "个线程中，此时config[index][0] = " + config[index][0] + ",config[index][5] = " + config[index][5]);
        }
        if(3 != config[index][3]) {
            LOGGER.info("在第" + index + "个线程中，此时config[index][0] = " + config[index][0] + ",config[index][5] = " + config[index][5]);
        }
        if(2 != config[index][2]) {
            LOGGER.info("在第" + index + "个线程中，此时config[index][0] = " + config[index][0] + ",config[index][5] = " + config[index][5]);
        }
        if(1 != config[index][1]) {
            LOGGER.info("在第" + index + "个线程中，此时config[index][0] = " + config[index][0] + ",config[index][5] = " + config[index][5]);
        }
    }
    //设置配置
    static void loadConfig(int index){
        config[index][1] = 1;
        config[index][2] = 2;
        config[index][3] = 3;
        config[index][4] = 4;
        config[index][5] = 5;
        config[index][0] = 1;//配置就绪
    }

    /**
     * <p>加载配置的线程</p>
     * @author hanchao 2018/3/10 21:56
     **/
    static class LoadConfigThread extends Thread{
        private int index;

        public LoadConfigThread(int index) {
            this.index = index;
        }

        /**
         * <p>加载配置</p>
         * @author hanchao 2018/3/10 21:56
         **/
        @Override
        public void run(){
            //1.获取配置
            loadConfig(this.index);

        }
    }
    static class UseConfigThread extends Thread{
        private int index;
        private AtomicInteger count = new AtomicInteger(10);//剩余等待次数

        public UseConfigThread(int index) {
            this.index = index;
        }

        @Override
        public void run(){
            //如果获取不到配置,则一直等待
            while (0 == config[this.index][0] && count.get() >= 0){
                count.getAndDecrement();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (1 == config[this.index][0]){
                //2.获取配置
                useConfig(this.index);
            }
        }
    }
    /**
     * <p>Java并发有序性示例</p>
     * @author hanchao 2018/3/10 21:47
     **/
    public static void main(String[] args) {
        //不采取有序性措施,也没有发生有序性问题.....
        for (int i = 0; i < 100000; i++) {
            new LoadConfigThread(i).start();
            new UseConfigThread(i).start();
        }
    }
}
