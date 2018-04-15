package pers.hanchao.concurrent.eg17;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>ForkJoin-ForkJoinPool的方法学习</p>
 *
 * @author hanchao 2018/4/15 22:12
 **/
public class ForkJoinPoolBasicDemo {
    /**
     * <p>ForkJoin-ForkJoinPool的方法学习</p>
     *
     * @author hanchao 2018/4/15 22:14
     **/
    public static void main(String[] args) {
        //构造函数
        //无参：并行级别=Runtime.getRuntime.availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //指定并行级别
        ForkJoinPool forkJoinPool1 = new ForkJoinPool(4);

        //提交任务(返回计算情况)
        //ForkJoinTask<V> implements Future<V>, Serializable
        //提交Runnable任务
        Runnable runnable = null;
        forkJoinPool.submit(runnable);
        //提交Runnable + result任务
        Integer result = null;
        Future<Integer> future2 = forkJoinPool.submit(runnable, result);
        //提交Callable<V>任务
        Callable<Integer> callable = null;
        Future<Integer> future3 = forkJoinPool.submit(callable);
        //提交ForkJoinTask<V>任务
        ForkJoinTask<Integer> forkJoinTask = null;
        Future<Integer> future4 = forkJoinPool.submit(forkJoinTask);
        //提交RecursiveAction任务(RecursiveAction extends ForkJoinTask<Void>)
        RecursiveAction recursiveAction = null;
        forkJoinPool.submit(recursiveAction);
        //提交RecursiveTask<V>任务(RecursiveTask<V> extends ForkJoinTask<V>)
        RecursiveTask<Integer> recursiveTask = null;
        Future<Integer> future6 = forkJoinPool.submit(recursiveTask);

        //提交任务(不返回计算情况)
        //提交Runnable任务
        Runnable runnable1 = null;
        forkJoinPool.execute(runnable1);
        //提交ForkJoinTask<V>任务
        ForkJoinTask<Integer> forkJoinTask1 = null;
        forkJoinPool.execute(forkJoinTask);
        //提交RecursiveAction任务(RecursiveAction extends ForkJoinTask<Void>)
        RecursiveAction recursiveAction1 = null;
        forkJoinPool.execute(recursiveAction);
        //提交RecursiveTask<V>任务(RecursiveTask<V> extends ForkJoinTask<V>)
        RecursiveTask<Integer> recursiveTask1 = null;
        forkJoinPool.execute(recursiveTask);

        //提交任务(返回计算结果)
        //提交ForkJoinTask<V>任务
        ForkJoinTask<Integer> forkJoinTask2 = null;
        Integer result1 = forkJoinPool.invoke(forkJoinTask);
        //提交RecursiveAction任务(RecursiveAction extends ForkJoinTask<Void>)
        RecursiveAction recursiveAction2 = null;
        forkJoinPool.invoke(recursiveAction);
        //提交RecursiveTask<V>任务(RecursiveTask<V> extends ForkJoinTask<V>)
        RecursiveTask<Integer> recursiveTask2 = null;
        Integer result3 = forkJoinPool.invoke(recursiveTask);

        //提交任务集
        //获取最先计算完成的-阻塞
        List<Callable<Integer>> callableList = new ArrayList<Callable<Integer>>();
        try {
            Integer result4 = forkJoinPool.invokeAny(callableList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //获取最先计算完成的-阻塞-可超时
        try {
            Integer result5 = forkJoinPool.invokeAny(callableList, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        //所有任务计算完成之后，返回结果-阻塞
        List<Future<Integer>> futureList = forkJoinPool.invokeAll(callableList);
        //所有任务计算完成之后，返回结果-阻塞-可超时
        try {
            List<Future<Integer>> futureList1 = forkJoinPool.invokeAll(callableList, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //是否正在终止
        forkJoinPool.isTerminating();
        //是否终止
        forkJoinPool.isTerminated();
        try {
            //等待终止
            forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //是否休眠
        forkJoinPool.isQuiescent();
        //等待休眠
        forkJoinPool.awaitQuiescence(1, TimeUnit.SECONDS);

        //存在等待执行的子任务
        forkJoinPool.hasQueuedSubmissions();

        //是否是FIFO模式
        boolean asyncMode = forkJoinPool.getAsyncMode();
        //获取当前活跃线程数
        int activeThreadCount = forkJoinPool.getActiveThreadCount();
        //获取线程池并行级别
        int parallelism = forkJoinPool.getParallelism();
        //获取工作线程数量
        int poolSize = forkJoinPool.getPoolSize();
        //获取等待执行的子任务数量
        int queuedSubmissionCount = forkJoinPool.getQueuedSubmissionCount();
        //获取等待执行的任务数量
        long queuedTaskCount = forkJoinPool.getQueuedTaskCount();
        //获取非阻塞的活动线程数量
        int runningThreadCount = forkJoinPool.getRunningThreadCount();
        //获取窃取线程数量
        long stealCount = forkJoinPool.getStealCount();
        //获取工作线程工厂
        ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory = forkJoinPool.getFactory();
        //获取未捕获异常处理器
        Thread.UncaughtExceptionHandler handler = forkJoinPool.getUncaughtExceptionHandler();

        //关闭线程池
        forkJoinPool.isShutdown();
        forkJoinPool.shutdown();
        forkJoinPool.shutdownNow();
    }
}
