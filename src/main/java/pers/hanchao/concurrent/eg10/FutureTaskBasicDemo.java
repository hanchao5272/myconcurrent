package pers.hanchao.concurrent.eg10;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>FutureTask学习</p>
 *
 * @author hanchao 2018/4/2 22:59
 **/
public class FutureTaskBasicDemo {
    /**
     * <p>FutureTask学习</p>
     * <p>
     * <p>FutureTask的主要用途：包装Runnable接口和Callable接口</p>
     *
     * @author hanchao 2018/4/2 23:00
     **/
    public static void main(String[] args) {
        /*
        - 五种方式对比
        - FutureTask与Future的方法类似，不再赘述
         */
        //定义一个线程池
        ExecutorService service = Executors.newCachedThreadPool();
        //定义一个Callable对象
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2000);
                return RandomUtils.nextInt(100, 200);
            }
        };

        //方式1：通过Future + Callable + ExecutorService获取值
        Future future = service.submit(callable);
        //获取结果
        try {
            System.out.println("方式1：Future + Callable + ExecutorService 计算结果：" + future.get());
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("Future 被中断");
        } catch (ExecutionException e) {
            //e.printStackTrace();
            System.out.println("Future 执行出错");
        }

        System.out.println();
        //方式2：通过FutureTask + Callable + ExecutorService取值 -- FutureTask包装Callable接口
        //定义一个FutureTask对象
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        //提交一个FutureTask
        service.submit(futureTask);
        //获取结果
        try {
            System.out.println("方式2：FutureTask包装Callable接口 + ExecutorService 计算结果：" + futureTask.get());
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("FutureTask(ExecutorService) 被中断");
        } catch (ExecutionException e) {
            //e.printStackTrace();
            System.out.println("FutureTask(ExecutorService) 执行出错");
        }

        System.out.println();
        //方式3：通过FutureTask + Callable + Thread取值 -- FutureTask包装Callable接口
        //定义一个FutureTask对象
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(callable);
        //裸线程方式
        new Thread(futureTask1).start();
        //获取结果
        try {
            System.out.println("方式3：FutureTask包装Callable接口 + Thread 的计算结果：" + futureTask1.get());
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("FutureTask(Thread) 被中断");
        } catch (ExecutionException e) {
            //e.printStackTrace();
            System.out.println("FutureTask(Thread) 执行出错");
        }

        //方法4和5
        //定义一个结果对象
        AtomicInteger result = new AtomicInteger(0);
        //定义一个Runnable对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result.compareAndSet(0, RandomUtils.nextInt(100, 200));
            }
        };


        System.out.println();
        //方式4：通过FutureTask + Runnable + ExecutorService 取值 -- FutureTask包装Runnable接口
        //定义一个FutureTask对象
        FutureTask<AtomicInteger> futureTask2 = new FutureTask<AtomicInteger>(runnable, result);
        //第二种 ExecutorService
        service.submit(futureTask2);
        //循环等待结果
        while (result.get() == 0) ;
        //输出结果
        System.out.println("方式4：FutureTask包装Runnable接口 + ExecutorService 的计算结果：" + result.get());

        System.out.println();
        //方式5：通过FutureTask + Runnable + Thread 取值 -- FutureTask包装Runnable接口
        //清除result的值
        result.set(0);
        //定义一个FutureTask对象
        FutureTask<AtomicInteger> futureTask3 = new FutureTask<AtomicInteger>(runnable, result);
        //第一种 裸线程
        new Thread(futureTask3).start();
        //循环等待结果
        while (result.get() == 0) ;
        //输出结果
        System.out.println("方式5：FutureTask包装Runnable接口 + Thread  的计算结果：" + result.get());

        //关闭线程池
        service.shutdown();
    }
}
