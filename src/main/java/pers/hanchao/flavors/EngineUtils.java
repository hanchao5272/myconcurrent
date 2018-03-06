package pers.hanchao.flavors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索引擎工具类
 * Created by 韩超 on 2018/3/6.
 */
public class EngineUtils {
    private final static Logger LOGGER = Logger.getLogger(EngineUtils.class);

    //搜索引擎列表
    private static List<String> engineList;

    static {
        engineList = new ArrayList<>();
        engineList.add("百度");
        engineList.add("Google");
        engineList.add("必应");
        engineList.add("搜狗");
        engineList.add("Redis");
        engineList.add("Solr");
    }

    /**
     * <p>Title: 模拟一个搜索引擎进行一次问题查询</p>
     * @author 韩超 2018/3/6 11:20
     */
    public static String searchByEngine(String question,String engine) throws InterruptedException {
        //获取随机的时间间隔
        int interval = RandomUtils.nextInt(1,5000);
        LOGGER.info("搜索引擎[" + engine + "]正在查询，预计用时" + interval + "毫秒...");
        //当前线程休眠指定时间，模拟搜索引擎用时
        Thread.sleep(interval);
        return "通过搜索引擎[" + engine + "]，首先查到关于(" + question + ")问题的结果，用时 = " + interval + "毫秒!";
    }

    public static List<String> getEngineList() {
        return engineList;
    }

    public static void setEngineList(List<String> engineList) {
        EngineUtils.engineList = engineList;
    }
}
