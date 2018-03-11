package pers.hanchao.concurrent.eg03;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>并发可见性实例</p>
 *
 * @author hanchao 2018/3/10 15:39
 **/
public class ConcurrentVisibilityDemo {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentVisibilityDemo.class);

    //冒险家计数器，统计还存活多少个冒险家
    public static AtomicInteger num = new AtomicInteger(0);

    //使用普通变量，不添加可见性保证
    private static boolean shotByArrow = false;

    //使用volatile保证可见性
    private volatile static boolean shotByArrowWithVolatile = false;

    //使用Atomic变量
    private static AtomicBoolean shotByArrowWithAtomic = new AtomicBoolean(false);

    /**
     * <p>（普通共享变量，无法保证可见性）冒险家类：冒险家一致在冒险，直到他膝盖中了一箭</p>
     *
     * @author hanchao 2018/3/10 16:40
     **/
    static class Adventurer extends Thread {
        /**
         * <p>冒险家一直在冒险直到膝盖中了一箭</p>
         *
         * @author hanchao 2018/3/10 16:57
         **/
        @Override
        public void run() {
            LOGGER.info("我是一个像你一样的冒险家,我的名字是" + super.getName() + "一直都在冒险...");
            //没中箭之前，我一直在冒险
            while (!shotByArrow) ;
            num.getAndDecrement();
            LOGGER.info("直到我膝盖中了一箭...Game Over.我的名字是[Adventurer" + super.getName() + "]");
        }
    }

    /**
     * <p>(使用volatile关键字保证可见性)冒险家类：冒险家一致在冒险，直到他膝盖中了一箭</p>
     *
     * @author hanchao 2018/3/10 16:40
     **/
    static class AdventurerWithVolatile extends Thread {
        /**
         * <p>冒险家一直在冒险直到膝盖中了一箭</p>
         *
         * @author hanchao 2018/3/10 16:57
         **/
        @Override
        public void run() {
            LOGGER.info("我是一个像你一样的冒险家,我的名字是" + super.getName() + "一直都在冒险...");
            //没中箭之前，我一直在冒险
            while (!shotByArrowWithVolatile) ;
            num.getAndDecrement();
            LOGGER.info("直到我膝盖中了一箭...Game Over.我的名字是[AdventurerWithVolatile" + super.getName() + "]");
        }
    }

    /**
     * <p>（使用Atomic保证可见性）冒险家类：冒险家一致在冒险，直到他膝盖中了一箭</p>
     *
     * @author hanchao 2018/3/10 16:40
     **/
    static class AdventurerWithAtomic extends Thread {
        /**
         * <p>冒险家一直在冒险直到膝盖中了一箭</p>
         *
         * @author hanchao 2018/3/10 16:57
         **/
        @Override
        public void run() {
            LOGGER.info("我是一个像你一样的冒险家,我的名字是" + super.getName() + "一直都在冒险...");
            //没中箭之前，我一直在冒险
            while (!shotByArrowWithAtomic.get()) ;
            num.getAndDecrement();
            LOGGER.info("直到我膝盖中了一箭...Game Over.我的名字是[AdventurerWithAtomic" + super.getName() + "]");
        }
    }

    /**
     * <p>并发可见性实例</p>
     *
     * @author hanchao 2018/3/10 15:40
     **/
    public static void main(String[] args) throws InterruptedException {
        //简言
        LOGGER.info("可见性:在工作内存中，对一个变量修改时，能够保证修改的值即被更新到主内存中，从而被其他线程看见。");
        LOGGER.info("普通的共享变量不能保证可见性，应为在工作区间修改完成后，什么时候更新到内存中，是不确定的。");
        LOGGER.info("Java中能够保证变量可见性的有：volatile关键字、synchronized代码块、Lock接口以及Atomic类型。\n");

        num.compareAndSet(0,2);
        Long interval = 5000L;
        int type = 2;
        /**
         * 0:普通共享变量 无法保证可见性
         * 1:使用 volatile关键字 保证可见性
         * 2:使用 Atomic类型 保证可见性
         */
        switch (type) {
            case 0:
                //使用普通的贡献变量不能保证可见性
                for (int i = 0; i < num.get(); i++) {
                    Thread adventurer = new Adventurer();
                    adventurer.setName("[" + i + "]");
                    adventurer.start();
                }
                //向冒险家的膝盖射箭
                shotByArrow = true;
                //等待足够长的时间，让能够结束的线程都结束
                Thread.sleep(interval);
                LOGGER.info("还剩下" + num.get() + "个冒险家[Adventurer]没有中箭...");
                break;
            case 1:
                //使用volatile保证可见性
                for (int i = 0; i < num.get(); i++) {
                    Thread adventurer = new AdventurerWithVolatile();
                    adventurer.setName("[" + i + "]");
                    adventurer.start();
                }
                //向冒险家的膝盖射箭
                shotByArrowWithVolatile = true;
                //等待足够长的时间，让能够结束的线程都结束
                Thread.sleep(interval);
                LOGGER.info("还剩下" + num.get() + "个冒险家[AdventurerWithVolatile]没有中箭...");
                break;
            case 2:
                //使用Atomic变量保证可见性
                for (int i = 0; i < num.get(); i++) {
                    Thread adventurer = new AdventurerWithAtomic();
                    adventurer.setName("[" + i + "]");
                    adventurer.start();
                }
                //向冒险家的膝盖射箭
                shotByArrowWithAtomic.compareAndSet(false, true);
                //等待足够长的时间，让能够结束的线程都结束
                Thread.sleep(interval);
                LOGGER.info("还剩下" + num.get() + "个冒险家[AdventurerWithAtomic]没有中箭...");
                break;
            default:
                break;
        }
    }
}
