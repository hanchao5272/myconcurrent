package pers.hanchao.concurrent.eg04;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>线程基本方法（sleep、wait、notify、notifyAll、synchronized）</p>
 *
 * @author hanchao 2018/3/11 14:14
 **/
public class ThreadWaitDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadWaitDemo.class);

    //现有菜品
    private static Queue<String> foodQueue = (Queue<String>) new LinkedList<String>();
    //厨房的菜架能够存放菜品的最大值
    private static int maxSize = 6;
    //当厨房还剩几个菜时，继续炒菜
    private static int minSize = 2;

    //

    /**
     * <p>菜品工具类</p>
     *
     * @author hanchao 2018/3/11 15:03
     **/
    public static class Foods {
        private static String[] foods = new String[]{"[鱼香肉丝]", "[水煮肉片]", "[地三鲜]", "[红烧肉]", "[干煸豆角]"};

        /**
         * <p>随机获取一个菜名</p>
         *
         * @author hanchao 2018/3/11 15:46
         **/
        public static String randomFood() {
            return foods[RandomUtils.nextInt(0, foods.length)];
        }
    }

    /**
     * <p>厨房生产各种菜肴（wait、notify、synchronized）</p>
     *
     * @author hanchao 2018/3/11 14:21
     **/
    static class Kitchen extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (foodQueue) {//加锁
                    //菜架满了,厨房不必再茶菜,等着前厅通着再炒菜
                    if (maxSize == foodQueue.size()) {
                        try {
                            LOGGER.info("厨房菜架满了,厨房不必再茶菜,等着前厅通着再炒菜,当前菜架:" + foodQueue.toString());
                            foodQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //炒一个菜
                        String food = Foods.randomFood();
                        foodQueue.add(food);
                        try {
                            LOGGER.info("厨房炒了一个:" + food + ",厨师歇息2分钟...当前菜架:" + foodQueue.toString());
                            //抄完一个菜,歇息1分钟
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>餐厅消费各种菜肴（wait、notify、synchronized）</p>
     *
     * @author hanchao 2018/3/11 14:54
     **/
    static class Restaurant extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (foodQueue) {//加锁
                    //如果生意太好，菜品供不应求，只能等待厨房做菜...
                    if (0 == foodQueue.size()) {
                        try {
                            LOGGER.info("餐厅：生意太好，菜品供不应求，只能等待厨房做菜...当前菜架:" + foodQueue.toString());
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (foodQueue.size() > 0) {//如果有菜，则消费菜品
                        //当厨房的储备菜品所剩不多时，告诉厨师开始炒菜
                        if (foodQueue.size() <= minSize) {
                            foodQueue.notify();
                            LOGGER.info("餐厅：厨房的储备菜品所剩不多时，厨师们该继续炒菜了...");
                        }
                        //消费菜品
                        String food = foodQueue.poll();
                        try {
                            //随机一定时间吃掉一道菜
                            Thread.sleep(RandomUtils.nextInt(1500, 2500));
                            LOGGER.info("餐厅：刚刚消费了一道" + food + "...当前菜架:" + foodQueue.toString());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>线程基本方法（sleep、wait、notify、notifyAll、synchronized）</p>
     *
     * @author hanchao 2018/3/11 14:15
     **/
    public static void main(String[] args) throws InterruptedException {
        //通过Thread.sleep(ms),指定当前线程进行休眠(但是并没有释放锁)
        LOGGER.info("通过Thread.sleep(ms),指定当前线程进行休眠(但是并没有释放锁)");
        LOGGER.info("现在时间:" + System.currentTimeMillis() + ",main线程即将休眠1000ms.");
        Thread.sleep(1000);
        LOGGER.info("休眠结束:" + System.currentTimeMillis() + "\n");

        //通过关键字synchronized和Object的方法wait()/notify()/notifyAll()实现线程等待与唤醒
        //通过object.wait()，使得对象线程进行入等待唤醒状态，并是否对象上的锁
        //通过object.notify()/object.notifyALL()，唤醒此对象上等待的线程，并获得对象上的锁
        //wait()/notify()/notifyAll()必须在synchronized中使用
        new Kitchen().start();
        //先让厨房多炒几个菜
        Thread.sleep(10000);
        //餐厅开始消费
        new Restaurant().start();
    }
}
