package pers.hanchao.concurrent.eg11;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * <p>Executors-执行器工具类-示例</p>
 *
 * @author hanchao 2018/4/5 13:49
 **/
public class ExecutorsDemo {
    private static final Logger LOGGER = Logger.getLogger(ExecutorsDemo.class);

    /**
     * <p>执行器工具类</p>
     *
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
        Thread thread = threadFactory.newThread(() -> {
            System.out.println(Thread.currentThread() + " : " + 22222);
        });
        //执行此案吃
        thread.start();

        Thread.sleep(2000);
        System.out.println();
        ////////////////////////////////////第3类方法：ExecutorService////////////////////////////////////
        /* {newSingleThreadExecutor - 单任务线程池}
        - 使用一个{单独的工作线程}和{无界工作队列}的线程池。
        - 需要注意的是：如果这个工作线程在终止之前因为执行失败而终止，则如果需要去执行后续任务，可以{新建一个线程}代替它。
        - 任务是按{顺序执行}的，任意时刻，都{不会有超过一个以上}的活动线程。
        - 不同于{等效的newFixedThreadPool(1)}，{newSingleThreadExecutor}不能通过配置而达到使用额外线程的目的。
         */
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        /* {newCachedThreadPool - 缓存线程池}
        - 创建一个线程池，这个线程池能够按需创建新线程，并且能够重用可用的之前创建的线程。
        - 这个线程池会典型的提高处理多个短期异步任务的程序的新能。
        - 如果有可用的线程，执行任务会尽量重用以前构建的线程。
        - 如果没有可用的线程，将会创建一个新的线程，并将此线程添加到线程池中。
        - 空闲超过60秒的线程将会被终止，并且从缓存中移除。
        - 因此，即使这个线程池空闲再长时间，也不会消耗任何资源。
        - 注意，可以通过使用{ThreadPoolExecutor}的构造函数，构造具有相似属性不同细节(例如：超时参数)的缓存线程池实现。
         */
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        /* {newFixedThreadPool - 固定大小线程池}
        - 创建一个线程池，这个线程池重复使用固定数量的线程池，以及一个共享的无界队列。
        - 在任何时候，最多有{nThreads}个活动的线程。
        - 如果所有的{nThreads}个线程都处于活动状态，则新提交的任务将会在队列中等待。
        - 如果一个工作线程在终止之前因为执行失败而终止，则如果需要去执行后续任务，可以{新建一个线程}代替它。
        - 线程池中的线程会{一直存在}，直到显式的调用关闭{shutdown或shutdownNow}方法。
         */
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
        /*{newWorkStealingPool - 并行任务线程池}
        - 创建一个工作窃取线程池，以JVM运行时可以CPU核数作为线程池的并行度。
         */
        ExecutorService newWorkStealingPool = Executors.newWorkStealingPool();

        ////////////////////////////////////第4类方法：ScheduleExecutorService////////////////////////////////////
        /* {newScheduledThreadPool - 调度线程池}
        - 创建一个线程池，这个线程池可以延时执行任务，或者周期性的执行任务。
         */
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
        /* {newSingleThreadScheduledExecutor - }
        - 创建一个单线程线程池，这个线程池可以延时执行任务，或者周期性的执行任务。
        - 但是请注意，如果这个工作线程在终止之前因为执行失败而终止，则如果需要去执行后续任务，可以{新建一个线程}代替它。
        - 任务是按{顺序执行}的，任意时刻，都{不会有超过一个以上}的活动线程。
        - 不同于{等效的newScheduledThreadPool(1)}，{newSingleThreadScheduledExecutor}不能通过配置而达到使用额外线程的目的。
         */
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }
}
