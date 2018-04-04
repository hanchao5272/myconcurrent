package pers.hanchao.concurrent.eg10;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Callable接口的基本用法</p>
 * @author hanchao 2018/4/2 22:09
 **/
public class CallableBasicDemo {
    /**
     * Callable与Runnable类似，都是为了实现多线程而设计的。
     * Callable与Runnable类似，都可以在Executor框架中使用。
     * Callable与Runnable不同，主要有以下几个方面：
     * - Callable可以返回结果；Runnable不能返回结果
     * - Callable可以抛出异常；Runnable不能抛出异常
     * - Callable是泛型接口；Runnable是普通接口
     * - Callable常常与Future结合使用
     */
    /**
     * 多线程示例-获取随机值-实现Runnable接口
     */
    //定义一个共享变量
    static AtomicInteger value = new AtomicInteger(0);

    //实现Runnable
    static class RandomByRunnable implements Runnable{
        @Override
        public void run() {
            //模拟计算
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //CAS赋值
            value.compareAndSet(0,RandomUtils.nextInt(100,200));
        }
    }

    /**
     * 多线程示例-获取随机值-实现Callable接口
     */
    static class RandomByCallable implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            return RandomUtils.nextInt(100,200);
        }
    }
    /**
     * <p>Callable与Runnable接口的对比</p>
     * @author hanchao 2018/4/2 22:15
     **/
    public static void main(String[] args) throws Exception {
        //实现Runnable接口的方式
        new Thread(new RandomByRunnable()).start();
        //通过while去循环查询是否获取了值
        while (value.get() == 0);
        System.out.println("实现Runnable接口的线程（裸线程），获取了结果：" + value.get());

        //实现Runnable接口 + Executor框架的方式
        ExecutorService executorService = Executors.newCachedThreadPool();
        //重新初始化
        value = new AtomicInteger(0);
        executorService.submit(new RandomByRunnable());
        //通过while去循环查询是否获取了值
        while (value.get() == 0);
        System.out.println("实现Runnable接口的线程 + Executor框架，获取了结果：" + value.get());
        
        //实现Callable接口 + Executor框架
        Future<Integer> result = executorService.submit(new RandomByCallable());
        System.out.println("实现Callable接口 + Executor框架，获取了结果：" + result.get());
        executorService.shutdown();
    }

}
