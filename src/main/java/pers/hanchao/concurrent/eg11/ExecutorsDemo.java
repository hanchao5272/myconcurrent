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

        ////////////////////////////////////第一类静态方法：将Runnable转换成Callable////////////////////////////////////
//
//        //定义运行结果
//        final Integer[] result = {null};
//        //定义一个Runnable接口
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                result[0] = 111111;
//            }
//        };
//        //定义一个线程池
//        ExecutorService executorService = Executors.newCachedThreadPool();
//
//        //Executors.callable(runnable)=转换成Callable<Object>，Future返回null
//        Callable<Object> callable = Executors.callable(runnable);
//        //提交转换成之后的Callable接口
//        Future future = executorService.submit(callable);
//        //输出运行结果，肯定是null
//        System.out.println("Executors.callable(runnable) 的future = " + future.get());
//
//        //Executors.callable(runnable, result)=转换成Callable<V>，future有值
//        Callable<Integer> callable1 = Executors.callable(runnable, result[0]);
//        //提交转换成之后的Callable接口
//        Future future1 = executorService.submit(callable1);
//        //输出运行结果
//        System.out.println("Executors.callable(runnable, result) 的future = " + future1.get());
//
//        Thread.sleep(2000);
//        System.out.println();
//        ////////////////////////////////////第2类静态方法：线程工厂类////////////////////////////////////
//        //创建线程工厂
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
//        //创建多个线程
//        for (int i = 0; i < 5; i++) {
//            Thread thread = threadFactory.newThread(() -> {
//                System.out.println(Thread.currentThread() + " : " + 22222);
//            });
//            //执行此线程
//            thread.start();
//        }
//
//        Thread.sleep(2000);
//        System.out.println();

        ////////////////////////////////////第3类静态方法：不可配置的线程池////////////////////////////////////
        /* {unconfigurableExecutorService- 不可配置的线程池}
           {unconfigurableScheduledExecutorService- 不可配置的调度线程池}
        - 通过代理线程池对象DelegatedExecutorService防止方法的强制转换
        - 这提供了一种安全地“冻结”配置并且不允许调整给定具体实现的方法。
         */

//        //定义一个线程池服务ExecutorService
//        ExecutorService executorService1 = null;
//        //将上述的线程池服务ExecutorService 转换成 不可配置的线程池服务
//        ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(executorService1);
//        //...业务操作
//        //关闭服务
//        unconfigurableExecutorService.shutdownNow();
//
//        //定义一个可调度的线程池服务ScheduledExecutorService
//        ScheduledExecutorService scheduledExecutorService = null;
//        //将上述的线程池服务ScheduledExecutorService 转换成 不可配置的可调度的线程池服务
//        ScheduledExecutorService unconfigurableScheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledExecutorService);
//        //...业务操作
//        //关闭服务
//        unconfigurableScheduledExecutorService.shutdown();

        ////////////////////////////////////第4类静态方法：ExecutorService////////////////////////////////////
        /* {newSingleThreadExecutor - 单任务线程池}
        - 使用一个{单独的工作线程}和{无界工作队列}的线程池。
        - 需要注意的是：如果这个工作线程在关闭之前因为执行失败而终止，则如果需要去执行后续任务，可以{新建一个线程}代替它。
        - 任务是按{顺序执行}的，任意时刻，都{不会有超过一个以上}的活动线程。
        - 不同于{等效的newFixedThreadPool(1)}，{newSingleThreadExecutor}不能通过配置而达到使用额外线程的目的。

        - corePoolSize = 1      --> 1.至多只有一个活动线程会长期存在
        - maximumPoolSize = 1   --> 2.因为无界队列，此参数无实际意义
        - keepAliveTime = 0L    --> 3.因为无界队列，此参数无实际意义
        - TimeUnit = TimeUnit.MILLISECONDS      --> 3.因为无界队列，此参数无实际意义
        - workQueue = new LinkedBlockingQueue<Runnable>())  --> 2.无界队列，如果无可用核心线程，则新任务在此等待，不会再创建线程
         */
        System.out.println("===================== newSingleThreadExecutor - 单任务线程池");
        //定义一个单任务线程池
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        //循环执行5个任务
        for (int i = 0; i < 5; i++) {
            //永远都是thread-1
            singleThreadExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        Thread.sleep(1000);
        singleThreadExecutor.shutdown();
        System.out.println("===================== newSingleThreadExecutor - 单任务线程池");
        System.out.println();

        /* {newCachedThreadPool - 缓存线程池}
        - 创建一个线程池，这个线程池能够按需创建新线程，并且能够重用可用的之前创建的线程。
        - 这个线程池会典型的提高处理多个短期异步任务的程序的新能。
        - 如果有可用的线程，执行任务会尽量重用以前构建的线程。
        - 如果没有可用的线程，将会创建一个新的线程，并将此线程添加到线程池中。
        - 空闲超过60秒的线程将会被终止，并且从缓存中移除。
        - 因此，即使这个线程池空闲再长时间，也不会消耗任何资源。
        - 注意，可以通过使用{ThreadPoolExecutor}的构造函数，构造具有相似属性不同细节(例如：超时参数)的缓存线程池实现。

        - corePoolSize = 0      --> 1.不会存在长期存在的线程
        - maximumPoolSize = Integer.MAX_VALUE   --> 3.最多创建2,147,483,647个线程
        - keepAliveTime = 60L   --> 4.线程执行完任务后，最多空闲60秒，就会关闭
        - TimeUnit = TimeUnit.SECONDS   --> 4.线程执行完任务后，最多空闲60秒，就会关闭
        - workQueue = new SynchronousQueue<Runnable>()  --> 2.直传队列，新线程到达，不会等待，会直接创建新线程
        */
        System.out.println("===================== newCachedThreadPool - 缓存线程池");
        //定义一个缓冲线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //创建5个线程
        for (int i = 0; i < 5; i++) {
            //如果无可用线程，则创建新线程
            cachedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        //等待1秒
        Thread.sleep(1000);
        System.out.println();
        //再次重新创建5个线程
        for (int i = 0; i < 5; i++) {
            //如果有可用线程，则重用之前创建的线程
            cachedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        //等待1秒
        Thread.sleep(7000);
        System.out.println();
        //再次重新创建5个线程
        for (int i = 0; i < 5; i++) {
            //如果有可用线程，则重用之前创建的线程
            cachedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        Thread.sleep(1000);
        cachedThreadPool.shutdown();
        System.out.println("===================== newCachedThreadPool - 缓存线程池");
        System.out.println();

        /* {newFixedThreadPool - 固定大小线程池}
        - 创建一个线程池，这个线程池重复使用固定数量的线程池，以及一个共享的无界队列。
        - 在任何时候，最多有{nThreads}个活动的线程。
        - 如果所有的{nThreads}个线程都处于活动状态，则新提交的任务将会在队列中等待。
        - 如果一个工作线程在关闭之前因为执行失败而终止，则如果需要去执行后续任务，可以{新建一个线程}代替它。
        - 线程池中的线程会{一直存在}，直到显式的调用关闭{shutdown或shutdownNow}方法。

        - corePoolSize = nThreads       --> 1.至多有nThreads个活动线程会长期存在
        - maximumPoolSize = nThreads    --> 2.因为无界队列，此参数无实际意义
        - keepAliveTime = 0L    --> 3.因为无界队列，此参数无实际意义
        - TimeUnit = TimeUnit.MILLISECOND   --> 3.因为无界队列，此参数无实际意义
        - workQueue = new LinkedBlockedQueue<Runnable>()>   --> 2.无界队列，如果无可用核心线程，则新任务在此等待，不会再创建线程
         */
        System.out.println("===================== newFixedThreadPool - 固定大小线程池");
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            //在thread-1 和 Thread-2 之间切换
            fixedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        Thread.sleep(1000);
        fixedThreadPool.shutdown();
        System.out.println("===================== newFixedThreadPool - 固定大小线程池");
        System.out.println();

        /*{newWorkStealingPool - 并行工作者线程池}
        - 创建一个工作窃取线程池，以JVM运行时可以CPU核数作为线程池的并行度。
        - 此线程池通过ForkJoinPool实现

        - parallelism = Runtime.getRuntime().availableProcessors()  --> 1.并行级别为运行期可用的CPU处理器数量
        - factory = ForkJoinPool.defaultForkJoinWorkerThreadFactory --> 2.使用ForkJoinPool默认的工作线程队列
        - handler = null    --> 3.不设置异常捕捉处理器
        - asyncMode = true  --> 4.采取异步模式
         */
        System.out.println("===================== newWorkStealingPool - 并行任务线程池");
        //定义一个并行工作者线程池
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
        //循环创建线程池
        for (int i = 0; i < 20; i++) {
            //因为本机CPU为4核，所以并行级别为4，即最多平行四个工作者。
            workStealingPool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        //
        Thread.sleep(1000);
        workStealingPool.shutdown();
        System.out.println("===================== newWorkStealingPool - 并行任务线程池");
        System.out.println();

        ////////////////////////////////////第5类静态方法：ScheduleExecutorService////////////////////////////////////
        /* {newScheduledThreadPool - 固定大小调度线程池}
        - 创建一个线程池，这个线程池可以延时执行任务，或者周期性的执行任务。

        - corePoolSize = 指定大小   --> 1.至多有指定数量的活动线程会长期存在
        - maximumPoolSize = Integer.MAX_VALUE   --> 3.因为无界队里，此参数无实际意义
        - keepAliveTime = 0L       --> 4.因为无界队列，此参数无实际意义
        - TimeUnit = TimeUnit.NANOSECONDS       --> 4.因为无界队列，此参数无实际意义
        - workQueue = new DelayedWorkQueue()    --> 2.一种无界队列，如果无可用核心线程，则新任务在此等待，不会再创建线程
         */
        System.out.println("===================== newScheduledThreadPool - 调度线程池");
        //定义一个固定大小的调度线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        //循环提交任务
        for (int i = 0; i < 5; i++) {
            //在thread-1 和 Thread-2 之间切换
            //如果不能计算好线程池的核心线程数量和任务延时之间的关系，很可能造成指定的延时任务并未按照计划执行
            scheduledThreadPool.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + " begin... ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end. ");
            }, 2, TimeUnit.SECONDS);
        }
        Thread.sleep(7000);
        System.out.println("===================== newScheduledThreadPool - 调度线程池");
        System.out.println();
        scheduledThreadPool.shutdown();
        /* {newSingleThreadScheduledExecutor - 单线程的调度线程池}
        - 创建一个单线程线程池，这个线程池可以延时执行任务，或者周期性的执行任务。
        - 但是请注意，如果这个工作线程在关闭之前因为执行失败而终止，则如果需要去执行后续任务，可以{新建一个线程}代替它。
        - 任务是按{顺序执行}的，任意时刻，都{不会有超过一个以上}的活动线程。
        - 不同于{等效的newScheduledThreadPool(1)}，{newSingleThreadScheduledExecutor}不能通过配置而达到使用额外线程的目的。

        - corePoolSize = 1   --> 1.至多只有1个的活动线程会长期存在
        - maximumPoolSize = Integer.MAX_VALUE   --> 3.因为无界队里，此参数无实际意义
        - keepAliveTime = 0L       --> 4.因为无界队列，此参数无实际意义
        - TimeUnit = TimeUnit.NANOSECONDS       --> 4.因为无界队列，此参数无实际意义
        - workQueue = new DelayedWorkQueue()    --> 2.一种无界队列，如果无可用核心线程，则新任务在此等待，不会再创建线程
         */
        System.out.println("===================== newSingleThreadScheduledExecutor - 单线程的调度线程池");
        //定义一个单线程的调度线程池
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < 5; i++) {
            singleThreadScheduledExecutor.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + " begin... ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end. ");
            }, 2, TimeUnit.SECONDS);
        }
        Thread.sleep(10000);
        System.out.println("===================== newSingleThreadScheduledExecutor - 单线程的调度线程池");
        singleThreadScheduledExecutor.shutdown();
    }
}
