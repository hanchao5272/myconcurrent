package pers.hanchao.concurrent.eg15;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * <p>CyclicBarrier-循环屏障-基本方法学习示例</p>
 *
 * @author hanchao 2018/3/29 21:01
 **/
public class CyclicBarrierBasicDemo {
    private static final Logger LOGGER = Logger.getLogger(CyclicBarrierBasicDemo.class);

    /**
     * <p>CyclicBarrier-基本方法</p>
     *
     * @author hanchao 2018/3/29 21:02
     **/
    public static void main(String[] args) throws InterruptedException {
        /**
         * CyclicBarrier-循环屏障
         * 1.所谓屏障：指定数量的线程全部调用 cyclicBarrier的await()方法时，这些线程不再阻塞
         * 2.所谓循环：通过reset()方法可以进行重置
         *
         * CyclicBarrier 与 CountDownLatch
         * 1.CyclicBarrier：线程相互等待；CountDownLatch：当前线程等待一个或多个线程
         * 2.CyclicBarrier：可以重置count，所以叫循环；CountDownLatch：运行完就完了，count=0，不可重置
         */
        /**
         * 0 展示getParties()/await()/getNumberWaiting()/循环屏障的普通流程/屏障打开之后再次等待--循环的意义
         * 1 reset():将CyclicBarrier回归初始状态，如果有正在等待的线程，则会抛出BrokenBarrierException异常
         * 2 await() 等待，除非：1.屏障打开;2.本线程被interrupt;3.其他等待线程被interrupted;4.其他等待线程timeout;5.其他线程调用reset()
         *   await(long,TimeUnit) 等待，除非：1.屏障打开(返回true);2.本线程被interrupt;3.本线程timeout;4.其他等待线程被interrupted;5.其他等待线程timeout;6.其他线程调用reset()
         *   isBroken()：默认为true，除非：1.等待线程被interrupt;2.等待线程timeout。如果，其他线程调用reset()，将其重置为true
         * 3 CyclicBarrier(int parties, Runnable barrierAction)：第二个构造器，设置屏障打开前首先运行的线程
         */
        int type = 3;
        switch (type) {
            case 0:
                //构造函数1：初始化-开启屏障的方数
                CyclicBarrier barrier0 = new CyclicBarrier(2);
                //通过barrier.getParties()获取开启屏障的方数
                LOGGER.info("barrier.getParties()获取开启屏障的方数：" + barrier0.getParties());
                System.out.println();
                //通过barrier.getNumberWaiting()获取正在等待的线程数
                LOGGER.info("通过barrier.getNumberWaiting()获取正在等待的线程数：初始----" + barrier0.getNumberWaiting());
                System.out.println();
                new Thread(() -> {
                    //添加一个等待线程
                    LOGGER.info("添加第1个等待线程----" + Thread.currentThread().getName());
                    try {
                        barrier0.await();
                        LOGGER.info(Thread.currentThread().getName() + " is running...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info(Thread.currentThread().getName() + " is terminated.");
                }).start();
                Thread.sleep(10);
                //通过barrier.getNumberWaiting()获取正在等待的线程数
                LOGGER.info("通过barrier.getNumberWaiting()获取正在等待的线程数：添加第1个等待线程---" + barrier0.getNumberWaiting());
                Thread.sleep(10);
                System.out.println();
                new Thread(() -> {
                    //添加一个等待线程
                    LOGGER.info("添加第2个等待线程----" + Thread.currentThread().getName());
                    try {
                        barrier0.await();
                        LOGGER.info(Thread.currentThread().getName() + " is running...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info(Thread.currentThread().getName() + " is terminated.");
                }).start();
                Thread.sleep(100);
                System.out.println();
                //通过barrier.getNumberWaiting()获取正在等待的线程数
                LOGGER.info("通过barrier.getNumberWaiting()获取正在等待的线程数：打开屏障之后---" + barrier0.getNumberWaiting());

                //已经打开的屏障，再次有线程等待的话，还会重新生效--视为循环
                new Thread(() -> {
                    LOGGER.info("屏障破损之后，再有线程加入等待：" + Thread.currentThread().getName());
                    try {
                        //BrokenBarrierException
                        barrier0.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info(Thread.currentThread().getName() + " is terminated.");

                }).start();
                System.out.println();
                Thread.sleep(10);
                LOGGER.info("通过barrier.getNumberWaiting()获取正在等待的线程数：打开屏障之后---" + barrier0.getNumberWaiting());
                Thread.sleep(10);
                new Thread(() -> {
                    LOGGER.info("屏障破损之后，再有线程加入等待：" + Thread.currentThread().getName());
                    try {
                        //BrokenBarrierException
                        barrier0.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info(Thread.currentThread().getName() + " is terminated.");

                }).start();
                Thread.sleep(10);
                LOGGER.info("通过barrier.getNumberWaiting()获取正在等待的线程数：打开屏障之后---" + barrier0.getNumberWaiting());
                break;
            case 1:
                CyclicBarrier barrier2 = new CyclicBarrier(2);
                //如果是一个初始的CyclicBarrier，则reset()之后，什么也不会发生
                LOGGER.info("如果是一个初始的CyclicBarrier，则reset()之后，什么也不会发生");
                barrier2.reset();
                System.out.println();

                Thread.sleep(100);
                //如果是一个已经打开一次的CyclicBarrier，则reset()之后，什么也不会发生
                ExecutorService executorService2 = Executors.newCachedThreadPool();
                //等待两次
                for (int i = 0; i < 2; i++) {
                    executorService2.submit(() -> {
                        try {
                            barrier2.await();
                            LOGGER.info("222屏障已经打开.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    });
                }
                barrier2.reset();

                Thread.sleep(100);
                System.out.println();
                //如果是一个 有线程正在等待的线程，则reset()方法会使正在等待的线程抛出异常
                executorService2.submit(() -> {
                    executorService2.submit(() -> {
                        try {
                            barrier2.await();
                            LOGGER.info("333屏障已经打开.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {

                            LOGGER.info("在等待过程中，执行reset()方法，等待的线程跑出BrokenBarrierException异常，并不再等待");
                            e.printStackTrace();
                        }
                    });
                });
                Thread.sleep(100);
                barrier2.reset();
                break;
            case 2:
                CyclicBarrier barrier1 = new CyclicBarrier(3);
                ExecutorService executorService = Executors.newCachedThreadPool();
                //添加一个用await()等待的线程
                executorService.submit(() -> {
                    try {
                        //等待，除非：1.屏障打开;2.本线程被interrupt;3.其他等待线程被interrupted;4.其他等待线程timeout;5.其他线程调用reset()
                        barrier1.await();
                    } catch (InterruptedException e) {
                        LOGGER.info(Thread.currentThread().getName() + " is interrupted.");
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        LOGGER.info(Thread.currentThread().getName() + " is been broken.");
                        e.printStackTrace();
                    }
                });
                Thread.sleep(10);
                LOGGER.info("刚开始，屏障是否破损：" + barrier1.isBroken());
                //添加一个等待线程-并超时
                executorService.submit(() -> {
                    try {
                        //等待1s，除非：1.屏障打开(返回true);2.本线程被interrupt;3.本线程timeout;4.其他等待线程被interrupted;5.其他等待线程timeout;6.其他线程调用reset()
                        barrier1.await(1, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        LOGGER.info(Thread.currentThread().getName() + " is interrupted.");
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        LOGGER.info(Thread.currentThread().getName() + " is been reset().");
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        LOGGER.info(Thread.currentThread().getName() + " is timeout.");
                        e.printStackTrace();
                    }
                });
                Thread.sleep(100);
                LOGGER.info("当前等待线程数量：" + barrier1.getNumberWaiting());
                Thread.sleep(1000);
                LOGGER.info("当前等待线程数量：" + barrier1.getNumberWaiting());
                LOGGER.info("当等待的线程timeout时，当前屏障是否破损：" + barrier1.isBroken());
                LOGGER.info("等待的线程中，如果有一个出现问题，则此线程会抛出相应的异常；其他线程都会抛出BrokenBarrierException异常。");

                System.out.println();
                Thread.sleep(5000);
                //通过reset()重置屏障回顾初始状态，也包括是否破损
                barrier1.reset();
                LOGGER.info("reset()之后，当前屏障是否破损：" + barrier1.isBroken());
                break;
            case 3:
                //构造器：设置屏障放开前做的事情
                CyclicBarrier barrier3 = new CyclicBarrier(2, () -> {
                    LOGGER.info("屏障放开，我先来！");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("屏障放开，我先来！我的事情做完了");
                });
                for (int i = 0; i < 2; i++) {
                    new Thread(() -> {
                        LOGGER.info(Thread.currentThread().getName() + " 等待屏障放开");
                        try {
                            barrier3.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        LOGGER.info(Thread.currentThread().getName() + "开始干活...干活结束");
                    }).start();
                }
                break;
            default:
                break;
        }
    }
}
