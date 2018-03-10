package pers.hanchao.concurrent.eg02;

import org.apache.log4j.Logger;
import pers.hanchao.concurrent.eg01.MyThread;

/**
 * <p>线程优先级实例</p>
 * @author hanchao 2018/3/8 23:24
 **/
public class ThreadPriorityDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadPriorityDemo.class);

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
        LOGGER.info("线程的优先级具有继承性：" + new MyThread("自定义线程").getPriority());

        //优先级与线程的执行顺序
        //注意多核CPU与单核CPU
        for (int i = 0; i < 10; i++) {
            Thread thread;
            //部分线程设置为高级线程
            if ( i % 2 == 0){
                thread = new MyThread("高优先级线程-" + i);
                thread.setPriority(Thread.MAX_PRIORITY);
            }else if( i % 3 == 0){//部分线程设置为中级线程
                thread = new MyThread("中优先级线程-" + i);
                thread.setPriority(Thread.NORM_PRIORITY);
            }else{//其余线程设置为低级线程
                thread = new MyThread("低优先级线程-" + i);
                thread.setPriority(Thread.MIN_PRIORITY);
            }
            thread.start();
        }
    }
}
