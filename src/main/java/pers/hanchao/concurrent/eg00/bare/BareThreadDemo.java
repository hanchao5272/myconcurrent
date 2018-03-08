package pers.hanchao.concurrent.eg00.bare;

import org.apache.log4j.Logger;

import pers.hanchao.concurrent.eg00.common.SearchEngineUtils;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>裸线程实现的两种方式：implements Runnable和extends Thread</p>
 *
 * @author hanchao 2018/3/7 22:34
 **/
public class BareThreadDemo {
    private static final Logger LOGGER = Logger.getLogger(BareThreadDemo.class);

    /**
     * <p>通过实现Runnable接口实现自定义线程，需要重写run()方法</p>
     *
     * @author hanchao 2018/3/7 22:36
     **/
    static class MyRunnableImpl implements Runnable {
        private static final Logger LOGGER = Logger.getLogger(MyRunnableImpl.class);
        /**
         * 线程ming
         */
        private String name;
        /**
         * 问题
         */
        private String question;
        /**
         * 搜索引擎
         */
        private String engine;
        /**
         * 搜索结果
         */
        private AtomicReference<String> result;

        /**
         * <p>通过构造方法向线程传递参数</p>
         * @author hanchao 2018/3/7 23:34
         **/
        public MyRunnableImpl(String name, String question, String engine, AtomicReference<String> result) {
            this.name = name;
            this.question = question;
            this.engine = engine;
            this.result = result;
        }

        /**
         * <p>在run()方法中定义业务代码</p>
         *
         * @author hanchao 2018/3/7 22:39
         **/
        @Override
        public void run() {
            try {
                LOGGER.info("MyRunnableImpl线程[" + this.name + "]开始搜索...");
                result.compareAndSet(null, SearchEngineUtils.searchByEngine(question, engine));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOGGER.info("...MyRunnableImpl线程[" + this.name + "]搜索结束");
            }
        }
    }

    static class MyThread extends Thread{
        private static final Logger LOGGER = Logger.getLogger(MyThread.class);
        /**
         * 线程ming
         */
        private String name;
        /**
         * 问题
         */
        private String question;
        /**
         * 搜索引擎
         */
        private String engine;
        /**
         * 搜索结果
         */
        private AtomicReference<String> result;

        /**
         * <p>通过构造方法向线程传递参数</p>
         * @author hanchao 2018/3/7 23:34
         **/
        public MyThread(String name, String question, String engine, AtomicReference<String> result) {
            this.name = name;
            this.question = question;
            this.engine = engine;
            this.result = result;
        }

        /**
         * <p>在run()方法中定义业务代码</p>
         * @author hanchao 2018/3/7 23:19
         **/
        @Override
        public void run() {
            try {
                LOGGER.info("MyThread线程[" + this.name + "]开始搜索...");
                result.compareAndSet(null, SearchEngineUtils.searchByEngine(question, engine));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOGGER.info("...MyThread线程[" + this.name + "]搜索结束");
            }
        }

    }

    /**
     * <p>分别以两种方式的裸线程实现：多搜索引擎最快查询</p>
     * @author hanchao 2018/3/7 23:14
     **/
    public static void main(String[] args) {
        //第一种方式：实现Runnable接口
        //定义一个原子变量盛放返回结果，以保证原子性。
        AtomicReference<String> result = new AtomicReference<>();
        //通过工具类获取搜索引擎列表
        List<String> engines = SearchEngineUtils.getSearchEngineList();
        //遍历列表，每个元素生成一个线程去搜索
        for (String engine : engines) {
            //调用自定义的Runnable实现类
            Runnable runnable = new MyRunnableImpl(engine + "的线程", "今天你吃了吗？", engine, result);
            //通过new Thread(runnable).start()启动线程
            new Thread(runnable).start();
        }
        //通过while无限循环等待搜索结果产生
        while (null == result.get()) ;
        //打印搜索结果
        LOGGER.info(result.get() + "\n");

        LOGGER.info("=============================================================");
        //第二种方式：继承Thread类（Thread implements Runnable）
        //定义一个原子变量盛放返回结果，以保证原子性。
        AtomicReference<String> result1 = new AtomicReference<>();
        //通过工具类获取搜索引擎列表
        List<String> engines1 = SearchEngineUtils.getSearchEngineList();
        //遍历列表，每个元素生成一个线程去搜索
        for (String engine : engines1) {
            //调用自定义的Thread子类
            Thread thread = new MyThread(engine + "的线程", "今天你吃了吗？", engine, result1);
            //通过thread.start()启动线程
            thread.start();
        }
        //通过while无限循环等待搜索结果产生
        while (null == result1.get()) ;
        //打印搜索结果
        LOGGER.info(result1.get());
    }
}
