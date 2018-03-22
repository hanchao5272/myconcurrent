package pers.hanchao.concurrent.eg09;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 线程本地变量-随机数的三种使用
 *
 * @since jdk1.7
 * Created by 韩超 on 2018/3/22.
 */
public class ThreadLocalRandomDemo {
    private static Random random = null;
    private static ThreadLocalRandom threadLocalRandom = null;
    private static ThreadLocal<Random> tlRandom = new ThreadLocal<>();

    /**
     * <p>Title: </p>
     *
     * @author 韩超 2018/3/22 15:10
     */
    public static void main(String[] args) throws InterruptedException {
        //获取种子
        Long seed = System.currentTimeMillis();
        /////////////////////////////// 伪随机 示例 ///////////////////////////////
        System.out.println("================伪随机：种子相同，生成的随机数序列一致。");
        random = new Random(seed);
        System.out.println("第一次生成：");
        for (int i = 0; i < 10; i++) {
            System.out.print(random.nextInt() + " ");
        }
        random = new Random(seed);
        System.out.println("\n重置种子\n第一次生成：");
        for (int i = 0; i < 10; i++) {
            System.out.print(random.nextInt() + " ");
        }


        System.out.println();
        System.out.println();
        /////////////////////////////// Random ThreadLocalRandom ThreadLocal<Random> 使用示例 ///////////////////////////////
        Thread.sleep(1000);
        //java.util.Random 多线程-共享 伪随机 相同种子
        System.out.println("================java.util.Random的多线程共享伪随机数");
        //不同种子
        System.out.println("----------------不同种子");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                random = new Random(System.currentTimeMillis());
                System.out.print(Thread.currentThread().getName() + ":");
                for (int j = 0; j < 10; j++) {
                    System.out.print(random.nextInt() + " ");
                }
                System.out.println();
            }).start();
        }

        Thread.sleep(100);
        System.out.println("----------------相同种子-->证明Random的伪随机性");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                random = new Random(seed);
                System.out.print(Thread.currentThread().getName() + ":");
                for (int j = 0; j < 10; j++) {
                    System.out.print(random.nextInt() + " ");
                }
                System.out.println();
            }).start();
        }

        Thread.sleep(1000);
        //java.util.concurrent.ThreadLocalRandom 多线程-并发 伪随机
        System.out.println("\n================java.util.concurrent.ThreadLocalRandom的多线程并发伪随机数");
        //不同种子
        System.out.println("----------------不同种子");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                threadLocalRandom = ThreadLocalRandom.current();
                System.out.print(Thread.currentThread().getName() + ":");
                for (int j = 0; j < 10; j++) {
                    System.out.print(threadLocalRandom.nextInt() + " ");
                }
                System.out.println();
            }).start();
        }

        Thread.sleep(100);
        //相同种子-->证明Random的伪随机性
        threadLocalRandom = ThreadLocalRandom.current();
        System.out.println("----------------相同种子-->证明Random的伪随机性");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                System.out.print(Thread.currentThread().getName() + ":");
                for (int j = 0; j < 10; j++) {
                    System.out.print(threadLocalRandom.nextInt() + " ");
                }
                System.out.println();
            }).start();
        }

        Thread.sleep(1000);
        //java.util.concurrent.ThreadLocal<Random> 多线程-并发 伪随机
        System.out.println("\n================java.util.concurrent.ThreadLocal<Random>的多线程并发伪随机数");
        Thread.sleep(100);
        //不同种子
        System.out.println("----------------不同种子");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                tlRandom.set(new Random(System.currentTimeMillis()));
                System.out.print(Thread.currentThread().getName() + ":");
                for (int j = 0; j < 10; j++) {
                    System.out.print(tlRandom.get().nextInt() + " ");
                }
                System.out.println();
            }).start();
        }

        Thread.sleep(100);
        //相同种子-->证明Random的伪随机性
        System.out.println("----------------相同种子-->证明Random的伪随机性");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                tlRandom.set(new Random(seed));
                System.out.print(Thread.currentThread().getName() + ":");
                for (int j = 0; j < 10; j++) {
                    System.out.print(tlRandom.get().nextInt() + " ");
                }
                System.out.println();
            }).start();
        }

        /////////////////////////////// Random ThreadLocalRandom ThreadLocal<Random> 性能对比 ///////////////////////////////
//        /*
//        num  *     Random      ThreadLocalRandom   ThreadLocal<Random>
//        10
//        100
//        1000
//        100000     24724          23551              22999
//         */
//        int num = 1000000;
//        int max = 10000;
//        Long begin = System.currentTimeMillis();
//
//        random = new Random(System.currentTimeMillis());
////        threadLocalRandom = ThreadLocalRandom.current();
//
//        for (int i = 0; i < num; i++) {
//            new Thread(() -> {
//
////                tlRandom.set(new Random(System.currentTimeMillis()));
//                for (int j = 0; j < max; j++) {
//
//                    random.nextInt();
//
////                    threadLocalRandom.nextInt();
//
////                    tlRandom.get().nextInt();
//                }
//                System.out.println(System.currentTimeMillis() - begin);
//            }).start();
//        }
    }
}
