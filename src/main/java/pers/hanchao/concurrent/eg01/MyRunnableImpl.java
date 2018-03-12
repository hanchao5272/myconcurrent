package pers.hanchao.concurrent.eg01;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>自定义线程02：实现Runnable接口</p>
 * @author hanchao 2018/3/8 23:04
 **/
public class MyRunnableImpl implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(MyRunnableImpl.class);

    /** 线程名(需要手动指定) */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyRunnableImpl(String name) {
        this.name = name;
    }

    /**
     * <p>义务代码在run()方法中，此方法无返回值</p>
     * @author hanchao 2018/3/8 23:07
     **/
    @Override
    public void run() {
        Integer interval = RandomUtils.nextInt(1000,5000);
        LOGGER.info("线程[" + this.getName() + "]正在运行，预计运行" + interval + "...");
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            LOGGER.info("...线程[" + this.getName() + "]运行结束");
        }
    }

    /**
     * <p>自定义线程实现类测试</p>
     * @author hanchao 2018/3/8 23:07
     **/
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            //通过new创建一个线程
            Runnable runnable = new MyRunnableImpl("MyRunnableImpl-" + i);
            //通过new Thread.start()启动线程
            new Thread(runnable).start();
        }
    }
}
