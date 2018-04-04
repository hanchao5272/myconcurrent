package pers.hanchao.concurrent.eg11;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>Executor学习示例</p>
 *
 * @author hanchao 2018/4/3 22:34
 **/
public class ExecutorDemo {
    /**
     * <p>Executor学习示例-Runnable接口的两种运行方式：Thread、Executor</p>
     *
     * @author hanchao 2018/4/3 22:35
     **/
    public static void main(String[] args) throws InterruptedException {
        //定义第1个Runnable接口对象
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is first Runnable Object.");
            }
        };
        //定义第2个Runnable接口对象
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is second Runnable Object.");
            }
        };

        System.out.println("======通过Thread运行：原始方式");
        //方式1：通过Thread运行
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        Thread thread2 = new Thread(runnable2);
        thread2.start();

        Thread.sleep(100);
        System.out.println();
        System.out.println("======通过Executor运行：便于统一调度");
        //方式2：通过Executor运行
        //Executor通常通过Executors工具类的静态方法实例化对象
        Executor executor = Executors.newCachedThreadPool();
        //Executor只有一个方法execute()，用于执行Runnable接口
        executor.execute(runnable1);
        executor.execute(runnable2);
    }
}
