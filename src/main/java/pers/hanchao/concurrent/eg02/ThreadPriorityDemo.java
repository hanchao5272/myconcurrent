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
