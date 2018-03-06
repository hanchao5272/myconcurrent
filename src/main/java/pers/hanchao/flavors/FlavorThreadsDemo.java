package pers.hanchao.flavors;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>并发四种口味-01 裸线程</p>
 *
 * @author hanchao 2018/3/5 21:53
 **/
public class FlavorThreadsDemo implements IFlavorDemo {
    private static final Logger LOGGER = Logger.getLogger(FlavorThreadsDemo.class);

    /**
     * 通过多个搜索引擎查询多个条件，并返回第一条查询结果
     *
     * @param question 查询问题
     * @param engines  查询条件数组
     * @return 最先查出的结果
     * @author hanchao 2018/3/5 22:05
     */
    @Override
    public String getFirstResult(String question, List<String> engines) {
        //将存放查询的数据类型设置为"Atomic"类型，保证原子性
        AtomicReference<String> result = new AtomicReference<String>();
        LOGGER.info("通过裸线程进行并发编程，自己控制现场数量：" + engines.size());
        //针对每一个搜索引擎，都开启一个线程进行查询
        for (String engine : engines) {
            //通过java8提供的lambda表达式创建线程
            new Thread(
                    () -> {
                        try {
                            //调用某种搜索引擎进行搜索
                            result.compareAndSet(null, EngineUtils.searchByEngine(question, engine));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();//通过.start()启动线程
            LOGGER.info("为搜索引擎[" + engine + "]创建一个线程...");
        }
        //无限循环，直至result有值为止
        while (result.get() == null) ;
        //返回搜索结果
        return result.get();
    }

    /**
     * <p>创建一组搜索引擎，对同一话题进行查询，并获取第一个查到的结果。</p>
     *
     * @author hanchao 2018/3/5 22:47
     **/
    public static void main(String[] args) {
        //通过工具类获取搜索引擎列表
        List<String> engines = EngineUtils.getEngineList();
        //通过 裸线程 进行并发查询，获取最先查到的答案
        String result = new FlavorThreadsDemo().getFirstResult("正则表达式", engines);
        //打印结果
        LOGGER.info(result);
    }
}
