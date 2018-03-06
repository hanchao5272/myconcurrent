package pers.hanchao.flavors;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发四种口味-03 Fork/Join框架
 * Created by 韩超 on 2018/3/6.
 */
public class FlavorParallelDemo implements IFlavorDemo {
    private final static Logger LOGGER = Logger.getLogger(FlavorParallelDemo.class);

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
        LOGGER.info("使用默认并行流进行并发编程，默认划分的子任务数 = CPU内核数（4）");
        //使用原子变量去测试任务划分是否有序
        AtomicInteger count = new AtomicInteger();

        //用list.stream.parallel()开启并行流进行并发编程
        Optional<String> result = engines.stream().parallel().map(
                (engine) -> {
                    try {
                        LOGGER.info("CPU划分了第" + count + "个子任务....");
                        count.getAndIncrement();
                        return EngineUtils.searchByEngine(question, engine);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        ).findAny();//任何一个子任务完成都可以结束
        return result.get();
    }

    /**
     * <p>Title: 创建一组搜索引擎，对同一话题进行查询，并获取第一个查到的结果。</p>
     *
     * @author 韩超 2018/3/6 11:53
     */
    public static void main(String[] args) {
        //通过工具类获取搜索引擎列表
        List<String> engines = EngineUtils.getEngineList();
        //通过 并行操作 进行并发查询，获取最先查到的答案
        String result = new FlavorParallelDemo().getFirstResult("正则表达式", engines);
        //打印结果
        LOGGER.info(result);
    }
}
