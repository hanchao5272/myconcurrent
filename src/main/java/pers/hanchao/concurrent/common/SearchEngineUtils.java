package pers.hanchao.concurrent.common;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>搜索引擎工具类</p>
 * @author hanchao 2018/3/7 22:49
 **/
public class SearchEngineUtils {
    private final static Logger LOGGER = Logger.getLogger(SearchEngineUtils.class);

    //搜索引擎列表
    private static List<String> SearchEngineList;

    static {
        SearchEngineList = new ArrayList<>();
        SearchEngineList.add("百度");
        SearchEngineList.add("Google");
        SearchEngineList.add("搜狗");
        SearchEngineList.add("必应");
        SearchEngineList.add("雅虎");
        SearchEngineList.add("360");
    }
    /**
     * <p>模拟一个搜索引擎进行一次问题查询</p>
     * @author hanchao 2018/3/7 22:50
     **/
    public static String searchByEngine(String question,String engine) throws InterruptedException {
        //获取随机的时间间隔
        int interval = RandomUtils.nextInt(1,5000);
        LOGGER.info("搜索引擎[" + engine + "]正在查询，预计用时" + interval + "毫秒...");
        //当前线程休眠指定时间，模拟搜索引擎用时
        Thread.sleep(interval);
        return "通过搜索引擎[" + engine + "]，首先查到关于(" + question + ")问题的结果，用时 = " + interval + "毫秒!";
    }

    public static List<String> getSearchEngineList() {
        return SearchEngineList;
    }

    public static void setSearchEngineList(List<String> searchEngineList) {
        SearchEngineUtils.SearchEngineList = searchEngineList;
    }
}
