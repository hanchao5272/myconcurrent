package pers.hanchao.concurrent.eg01;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * <p>自定义线程03：实现Callable接口</p>
 *
 * @author hanchao 2018/3/12 8:56
 **/
//注意，Callable是一个泛型接口
public class MyCallableImpl implements Callable<Integer> {
    private static final Logger LOGGER = Logger.getLogger(MyCallableImpl.class);

    /**
     * <p>实现Callable需要重写call方法，此方法有返回值</p>
     *
     * @author hanchao 2018/3/12 8:59
     **/
    @Override
    public Integer call() throws Exception {
        Integer interval = RandomUtils.nextInt(1000, 5000);
        Thread.sleep(interval);
        return interval;
    }

    /**
     * <p>实现Callable示例</p>
     *
     * @author hanchao 2018/3/12 9:00
     **/
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        //Future、Callable一般与Executor结合使用
//        //Executor负责创建线程池服务
//        //实现Callable接口形成的线程类，负责处理业务逻辑，并将处理结果返回
//        //Future接口负责接收Callable接口返回的值
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        try {
//            //定义一组返回值
//            Future<Integer>[] futures = new Future[5];
//            //向线程池提交任务
//            for (int i = 0; i < 5; i++) {
//                //注意Future的参数化类型要与Callable的参数化类型一致
//                futures[i] = executor.submit(new MyCallableImpl());
//            }
//            //输出执行结果
//            for (int i = 0; i < 5; i++) {
//                LOGGER.info(futures[i].get());
//            }
//        }finally {//将关闭线程池放在finally中，最大限度保证线程安全
//            //记得关闭这个线程池
//            executor.shutdown();
//        }

//        System.out.println();
//        //Future接口方法简单展示: isDone/isCancelled/get()
//        //创建单线程池
//        ExecutorService executor1 = Executors.newSingleThreadExecutor();
//        //向线程池提交任务
//        Future<Integer> future = executor1.submit(new MyCallableImpl());
//        try {
//            //计算执行时间
//            Long begin = System.currentTimeMillis();
//            LOGGER.info("future开始执行任务...当前时间：" + begin);
//            LOGGER.info("通过future.isDone()判断任务是否计算完成：" + future.isDone());
//            LOGGER.info("通过future.isCancelled()判断任务是否取消：" + future.isCancelled());
//            LOGGER.info("通过future.get()获取任务的计算结果(从任务开始就一直等待，直至有返回值)：" + future.get());
//            LOGGER.info("future结束执行任务...共计用时：" + (System.currentTimeMillis() - begin) + "ms..\n");
//        }finally {//将关闭线程池放在finally中，最大限度保证线程安全
//            LOGGER.info("通过future.isDone()判断任务是否计算完成：" + future.isDone());
//            LOGGER.info("通过future.isCancelled()判断任务是否取消：" + future.isCancelled());
//            //记得关闭这个线程池
//            executor1.shutdown();
//        }

        //get(long,TimeUnit):最多等待多长时间就不再等待
        //创建单线程池
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        //向线程池提交任务
        Future<Integer> future2 = executor2.submit(new MyCallableImpl());
        Long begin2 = System.currentTimeMillis();
        try {
            LOGGER.info("future开始执行任务...当前时间：" + begin2);
            LOGGER.info("通过future.get(long,TimeUnit)获取任务的计算结果(5秒钟之后再获取结果)：" + future2.get(500,TimeUnit.MILLISECONDS));
            LOGGER.info("future结束执行任务...共计用时：" + (System.currentTimeMillis() - begin2) + "ms..\n");
        }catch (TimeoutException e){
            LOGGER.info("在限定时间内没有等到查询结果，不再等待..");
            //关闭任务
            LOGGER.info("当前任务状态：future2.isDone() = " + future2.isDone());
            LOGGER.info("当前任务状态：future2.isCancelled() = " + future2.isCancelled());
            LOGGER.info("通过future.cancel()取消这个任务：");
            future2.cancel(true);
            LOGGER.info("当前任务状态：future2.isDone() = " + future2.isDone());
            LOGGER.info("当前任务状态：future2.isCancelled() = " + future2.isCancelled());

            //关闭线程池
            executor2.shutdown();

            //意料之中的结果，无需打印日志
            //e.printStackTrace();
        }
    }
}
