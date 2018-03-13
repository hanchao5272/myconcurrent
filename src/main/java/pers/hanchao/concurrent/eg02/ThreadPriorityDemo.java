package pers.hanchao.concurrent.eg02;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

/**
 * <p>线程优先级实例</p>
 * @author hanchao 2018/3/8 23:24
 **/
public class ThreadPriorityDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadPriorityDemo.class);

    static class SleepThread extends Thread {
        /**
         * <p>重写Thread类的构造器，用以给线程命名</br>
         * 此种方式无需定义name变量以指定线程名，因为父类Thread中已有。</p>
         * @author hanchao 2018/3/8 22:59
         **/
        public SleepThread(String name) {
            super(name);
        }

        /**
         * <p>业务代码写在run()方法中，此方法无返回值</p>
         * @author hanchao 2018/3/8 22:55
         **/
        @Override
        public void run(){
            Integer interval = 50000;
            System.out.println(System.nanoTime() + " : 线程[" + super.getName() + "]正在运行，预计运行" + interval + "...");
            int sum = 0;
            for(int i = 0; i < interval* interval*interval; i++){
                sum = sum + i;
            }
            System.out.println(System.nanoTime() + " : 线程[" + super.getName() + "]运行结束");
        }
    }

    /**
     * <p>线程优先级实例</p>
     * @author hanchao 2018/3/8 23:26
     **/
    public static void main(String[] args) {
        //线程优先级范围
        LOGGER.info("java多线程的优先级范围：[" + Thread.MIN_PRIORITY + "~" + Thread.MAX_PRIORITY + "]超出这个返回会报错IllegalArgumentException");
        //下面的线程优先级设置会报错
//        new Thread("超出优先级范围的线程").setPriority(0);

        //常用线程优先级
        LOGGER.info("常用线程优先级：Thread.MAX_PRIORITY = " + Thread.MAX_PRIORITY + ",用于标识高优先级的线程。");
        LOGGER.info("常用线程优先级：Thread.MIN_PRIORITY = " + Thread.MIN_PRIORITY + ",用于标识低优先级的线程。");
        LOGGER.info("常用线程优先级：Thread.NORM_PRIORITY = " + Thread.NORM_PRIORITY + ",用于标识正常优先级的线程。");
        LOGGER.info("默认的线程的优先级是：" + new Thread().getPriority());
        LOGGER.info("线程的优先级具有继承性：" + new SleepThread("自定义线程").getPriority());

        System.out.println();
        int num = 9;
        Thread[] threads = new Thread[num];
        //优先级与线程的执行顺序
        //注意多核CPU与单核CPU
        for (int i = 0; i < num; i++) {
            //部分线程设置为高级线程
            if ( i < (num / 3)){
                threads[i] = new SleepThread("高优先级线程-" + i);
                threads[i].setPriority(Thread.MAX_PRIORITY);
            }else if(i >= (num / 3) && i < (num / 3) * 2){//部分线程设置为中级线程
                threads[i] = new SleepThread("中优先级线程-" + i);
                threads[i].setPriority(Thread.NORM_PRIORITY);
            }else{//其余线程设置为低级线程
                threads[i] = new SleepThread("低优先级线程-" + i);
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
        }
        //统一运行线程
//        for(int i = 0; i < num; i++) {
//            threads[i].start();
//        }
        for(int i = num -1; i >= 0; i--) {
            threads[i].start();
        }
        System.out.println("=====================================");
    }
}
