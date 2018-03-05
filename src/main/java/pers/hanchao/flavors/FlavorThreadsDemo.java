package pers.hanchao.flavors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>并发四种口味-裸线程</p>
 * @author hanchao 2018/3/5 21:53
 **/
public class FlavorThreadsDemo {
    private static final Logger LOGGER = Logger.getLogger(FlavorThreadsDemo.class);

    /**
     * 通过多个搜索引擎查询多个条件，并返回第一条查询结果
     * @param question 查询问题
     * @param engines 查询条件数组
     * @return 最先查出的结果
     * @author hanchao 2018/3/5 22:05
     */
    public static String getFirstResult(String question, List<String> engines){
        //将存放查询的数据类型设置为"Atomic"类型，包装原子性
        AtomicReference<String> result = new AtomicReference<String >();
        //针对每一个搜索引擎，都开启一个线程进行查询
        for (String engine : engines){
            //通过java8提供的lambda表达式创建线程
            new Thread(
                () -> {
                        try {
                            //调用某种搜索引擎进行搜索
                            result.compareAndSet(null,searchByEngine(question,engine));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            ).start();//通过.start()启动线程
            LOGGER.info("为搜索引擎[" + engine + "]创建一个线程...");
        }
        //无限循环，直至result有值为止
        while (result.get() == null);
        //返回搜索结果
        return result.get();
    }

    /**
     * <p>通过搜索引擎进行查询</p>
     * @author hanchao 2018/3/5 22:05
     * @param engine 搜索引擎
     **/
    public static String searchByEngine(String question,String engine) throws InterruptedException {
        int interval = RandomUtils.nextInt(1,5000);
        LOGGER.info("搜索引擎[" + engine + "]正在查询，预计用时" + interval + "毫秒...");
        Thread.sleep(interval);
        return "通过搜索引擎[" + engine + "]，首先查到关于(" + question + ")问题的结果，用时 = " + interval + "毫秒!";
    }

    /**
     * <p>创建一组搜索引擎，对同一话题进行查询，并获取第一个查到的结果。</p>
     * @author hanchao 2018/3/5 22:47
     **/
    public static void main(String[] args) {
         List<String > engines = new ArrayList<String >();
         engines.add("百度");
         engines.add("Google");
         engines.add("必应");
         engines.add("搜狗");
         engines.add("Redis");
         engines.add("Solr");
         String result = getFirstResult("正则表达式",engines);
         LOGGER.info(result);
    }
}
