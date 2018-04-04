package pers.hanchao.concurrent.eg11;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>ExecutorService-Executor的升级版</p>
 *
 * @author hanchao 2018/4/3 22:54
 **/
public class ExecutorServiceDemo {
    /**
     * <p>ExecutorService--相较于Executor，增加了2个新特性：1.可以关闭线程池；2.可以使用Callable和Future接口</p>
     *
     * @author hanchao 2018/4/3 22:54
     **/
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /**
         * 0 Executor   无法主动关闭 根据创建线程池的方式采取不同的关闭策略
         * 1 ExecutorService shutdown 当正在运行的线程运行结束后，关闭线程池
         * 2 ExecutorService shutdownNow 理解关闭线程池，正在运行的线程将被停止
         */
        int type = 2;
        switch (type) {
            case 0:
                //Executor   无法主动关闭 根据创建线程池的方式采取不同的关闭策略
                Executor executor = Executors.newCachedThreadPool();
                //Executor只有一个方法execute()：提交Runnable任务
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "--- Executor begin ...");
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "--- Executor end .");
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "--- Executor is interrupted .");
                    }
                });
                break;
            case 1:
                //ExecutorService shutdown 当正在运行的线程运行结束后，关闭线程池
                ExecutorService executorService = Executors.newCachedThreadPool();
                //ExecutorService通过submit提交任务
                executorService.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "--- ExecutorService begin ...");
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "--- ExecutorService end .");
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "--- ExecutorService is interrupted .");
                    }
                });
                executorService.shutdown();
                break;
            case 2:
                //ExecutorService shutdownNow 理解关闭线程池，正在运行的线程将被停止
                ExecutorService executorService1 = Executors.newCachedThreadPool();
                //ExecutorService通过submit提交任务
                executorService1.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "--- ExecutorService begin ...");
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "--- ExecutorService end .");
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "--- ExecutorService is interrupted .");
                    }
                });
                executorService1.shutdownNow();
                break;
            default:
                break;
        }

        Thread.sleep(1000);
        System.out.println();
        //关于线程池关闭的其他方法
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(()->{System.out.println();});
        //executorService.isShutdown():线程池服务是否已经关闭
        executorService.isShutdown();
        //executorService.isTerminated():在线程池服务执行shutdown()或者shutdownNow()方法之后，所有的任务是否已经完成
        //如果没有执行shutdown()或者shutdownNow()方法，则永远返回false
        executorService.isTerminated();
        //阻塞等待终止所有的任务终止
        //如果等待时间超时，则返回false
        //如果当前线程被interrupt，则抛出InterruptedException异常
        //如果线程池了执行shutdown()或者shutdownNow()方法，并且所有的任务都已经完成，则返回true
        //如果线程池未执行shutdown()或者shutdownNow()方法，则永远返回false
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);


        //关于Callable和Future的简单用法，前面已经学习
        //下面进行Callable和Future的进阶用法--批量任务：最先完成任务和全部完成任务
        List<Callable<Integer>> callableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            callableList.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Integer result = RandomUtils.nextInt(100,2000);
                    Thread.sleep(result);
                    System.out.println("init " + result);
                    return result;
                }
            });
        }
        //全部任务完成
        List<Future<Integer>> futureList = executorService.invokeAll(callableList);
        System.out.println("等待所有的任务完成之后，才会得到结果集。");
        futureList.forEach(future -> {
            try {
                System.out.println("result " + future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(1000);
        System.out.println();
        //最先完成任务
        Integer firstResult = executorService.invokeAny(callableList);
        System.out.println("得到一个最先得到的结果，立即返回；后面的任务不再运行。");
        System.out.println("result " + firstResult);
    }
}
