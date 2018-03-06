package pers.hanchao.flavors;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 并发四种口味-02 Executor
 * Created by 韩超 on 2018/3/6.
 */
public class FlavorExecutorsDemo {
    private final static Logger LOGGER = Logger.getLogger(FlavorExecutorsDemo.class);

    /**
     * <p>Title: 通过多个搜索引擎查询多个条件，并返回第一条查询结果</p>
     *
     * @param question 问题
     * @param engines  搜索引擎列表
     * @author 韩超 2018/3/6 10:07
     */
    public static String getFirstResult(String question, List<String> engines){
        //将查询结果放在"Atomic"变量中，保证原子性
        AtomicReference<String> result = new AtomicReference<String>();

        //通过Executors.newFixedThreadPool(size)创建固定大小的线程池，只能运行size数量的线程，其余线程等待
        //创建ExecutorService线程池，此线程池能够主动控制线程池的运行、关闭和终止
        ExecutorService service = Executors.newFixedThreadPool(3);
        LOGGER.info("通过Executors创建固定大小的线程池，线程池大小：3，当前线程数：" + Thread.activeCount() + "线程池最大线程数：" + (Thread.activeCount() + 3));
        try{
            //针对每一个搜索引擎,都调用一次service的submit()方法
            for (String engine : engines) {
                //lambda，通过service.submit()设置业务代码
                service.submit(
                        () -> {
                            //调用某种搜索引擎进行搜索，并将搜索结果通过CAS方式放到result中
                            result.compareAndSet(null, EngineUtils.searchByEngine(question, engine));
                            return result;
                        }
                );
                LOGGER.info("为搜索引擎[" + engine + "]创建一个线程...当前活跃线程数：" + Thread.activeCount());
            }
            //当result取不到值时，证明还没有搜索引擎获取查出结果，通过while的无限循环进行等待
            while (null == result.get()) ;
        }finally {
            //记得要手动关闭ExecutorService线程池
            service.shutdown();
        }

        return result.get();
    }

    /**
     * <p>Title: 创建一组搜索引擎，对同一话题进行查询，并获取第一个查到的结果。</p>
     *
     * @author 韩超 2018/3/6 10:05
     */
    public static void main(String[] args) throws InterruptedException {
        //通过工具类获取搜索引擎列表
        List<String> engines = EngineUtils.getEngineList();
        //通过 executor 进行并发查询，获取最先查到的答案
        String result = getFirstResult("如何使用筷子？", engines);
        //打印结果
        LOGGER.info(result);
    }
}
