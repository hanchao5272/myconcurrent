package pers.hanchao.concurrent.eg11;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * <p>Executors-执行器工具类-示例</p>
 * @author hanchao 2018/4/5 13:49
 **/
public class ExecutorsDemo {
    private static final Logger LOGGER = Logger.getLogger(ExecutorsDemo.class);
    /**
     * <p>执行器工具类</p>
     * @author hanchao 2018/4/5 13:49
     **/
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ////////////////////////////////////第一类方法：将Runnable转换成Callable////////////////////////////////////

        //定义运行结果
        final Integer[] result = {null};
        //定义一个Runnable接口
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                result[0] = 111111;
            }
        };
        //定义一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //Executors.callable(runnable)=转换成Callable<Object>，Future返回null
        Callable<Object> callable = Executors.callable(runnable);
        //提交转换成之后的Callable接口
        Future future = executorService.submit(callable);
        //输出运行结果，肯定是null
        System.out.println("Executors.callable(runnable) 的future = " + future.get());

        //Executors.callable(runnable, result)=转换成Callable<V>，future有值
        Callable<Integer> callable1 = Executors.callable(runnable, result[0]);
        //提交转换成之后的Callable接口
        Future future1 = executorService.submit(callable1);
        //输出运行结果
        System.out.println("Executors.callable(runnable, result) 的future = " + future1.get());

        Thread.sleep(2000);
        System.out.println();
        ////////////////////////////////////第2类方法：线程工厂类////////////////////////////////////
        //创建线程工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //创建线程
        Thread thread = threadFactory.newThread(()->{
            System.out.println(Thread.currentThread() + " : " + 22222);
        });
        //执行此案吃
        thread.start();

        Thread.sleep(2000);
        System.out.println();
        ////////////////////////////////////第3类方法：ExecutorService////////////////////////////////////
    }
}
