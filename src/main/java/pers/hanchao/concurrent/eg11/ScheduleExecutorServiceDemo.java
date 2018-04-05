package pers.hanchao.concurrent.eg11;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * <p>ScheduleExecutorService-调度执行器服务-示例</p>
 *
 * @author hanchao 2018/4/5 12:50
 **/
public class ScheduleExecutorServiceDemo {
    private static final Logger LOGGER = Logger.getLogger(ScheduleExecutorServiceDemo.class);

    /**
     * <p>ScheduleExecutorService</p>
     *
     * @author hanchao 2018/4/5 12:50
     **/
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*
        Executor                    执行Runnable接口
        ExecutorService             执行Runnable接口、手动关闭、执行Future和Callable接口(单个、任一、批量)
        ScheduleExecutorService     执行Runnable接口、手动关闭、执行Future和Callable接口(单个、任一、批量)、
                                    延时执行Runnable接口和Callable接口、周期执行Runnable接口（2种方式）
         */

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        /*
        0   schedule                延时执行Runnable和延时执行Callable   执行一次
        1   scheduleWithFixedDelay  周期性的延时执行Runnable接口      上一次任务结束和下一次任务开始之间的时间间隔是固定的=delay
        2   scheduleAtFixedRate     周期性的等速率执行Runnable接口     上一次任务开始和下一次任务开始之间的时间间隔是固定的=period
        3   scheduleAtFixedRate     周期性的等速率执行Runnable接口     如果任务执行时间大于period，则上一次任务结束之后，立即开始下一次任务;即period=任务执行时间
         */
        int type = 3;
        switch (type) {
            case 0:
                //延时执行Runnable接口
                LOGGER.info("延时执行Runnable接口 : " + System.currentTimeMillis());
                scheduledExecutorService.schedule(() -> {
                    LOGGER.info("2秒之后 : " + System.currentTimeMillis());
                }, 2000, TimeUnit.MILLISECONDS);

                Thread.sleep(2500);
                //延时执行Callable接口
                System.out.println();
                LOGGER.info("延时执行Callable接口 : " + System.currentTimeMillis());
                ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(() -> {
                    return System.currentTimeMillis();
                }, 2, TimeUnit.SECONDS);
                LOGGER.info("2秒之后 ：" + scheduledFuture.get());

                //等待多长时间
                Thread.sleep(1000);
                break;
            case 1:
                //周期性的延时执行
                //初始延时
                long initDelay = 5000;
                //延时
                long delay = 3000;
                LOGGER.info("周期性的延时执行Runnable接口 ： " + System.currentTimeMillis());
                //周期性的延时执行
                scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    int number = RandomUtils.nextInt(1000, 3000);
                    LOGGER.info("周期性的延时执行Runnable接口 [" + number + "]开始运行 ： " + System.currentTimeMillis());
                    //模拟运行
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("周期性的延时执行Runnable接口 [" + number + "]结束运行 ： " + System.currentTimeMillis());
                }, initDelay, delay, TimeUnit.MILLISECONDS);

                //等待多长时间
                Thread.sleep(20000);
                break;
            case 2:
                //初始延时
                long initDelay1 = 5000;
                //执行周期
                long period = 3000;
                LOGGER.info("周期性的延时执行Runnable接口 ： " + System.currentTimeMillis());
                //周期性的执行
                scheduledExecutorService.scheduleAtFixedRate(() -> {
                    int number = RandomUtils.nextInt(1000, 3000);
                    LOGGER.info("周期性的延时执行Runnable接口 [" + number + "]开始运行 ： " + System.currentTimeMillis());
                    //模拟运行
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    LOGGER.info("周期性的延时执行Runnable接口 [" + number + "]结束运行 ： " + System.currentTimeMillis());
                }, initDelay1, period, TimeUnit.MILLISECONDS);

                //等待多长时间
                Thread.sleep(20000);
                break;
            case 3:
                //初始延时
                long initDelay2 = 5000;
                //执行周期
                long period1 = 2000;
                LOGGER.info("周期性的延时执行Runnable接口 ： " + System.currentTimeMillis());
                //周期性的执行
                scheduledExecutorService.scheduleAtFixedRate(() -> {
                    int number = RandomUtils.nextInt(1000, 3000);
                    LOGGER.info("周期性的延时执行Runnable接口 [" + number + "]开始运行 ： " + System.currentTimeMillis());
                    //模拟运行
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("周期性的延时执行Runnable接口 [" + number + "]结束运行 ： " + System.currentTimeMillis());
                }, initDelay2, period1, TimeUnit.MILLISECONDS);

                //等待多长时间
                Thread.sleep(20000);
                break;
            default:
                break;
        }
        //如果没关闭，则关闭
        if (!scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
        }
    }
}
