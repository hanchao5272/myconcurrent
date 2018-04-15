package pers.hanchao.concurrent.eg17;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * <p>ForkJoin框架实例1-RecursiveAction-无返回值-数据交换</p>
 * <p>数据交换：专网A内的数据库DB1上有100万数据，需要通过数据交换服务发送到专网B的数据库DB2上。
 * 1.原来的做法：将这100万数据按照5000条一组进行分组，然后每组都通过一个线程进行发送。不知道什么原因，总之经常会出现重复发送的数据。
 * 2.新的做法：根据ForkJoin框架编程思想，将这100万数据按照阈值THRESHOLD进行子任务划分，然后依次发送。</p>
 *
 * @author hanchao 2018/4/15 19:26
 **/
public class RecursiveActionDemo {
    private static final Logger LOGGER = Logger.getLogger(RecursiveActionDemo.class);
    //模拟数据库DB2
    static ConcurrentLinkedQueue DB2 = new ConcurrentLinkedQueue();

    /**
     * <p>定义一个数据交换任务，继承自RecursiveAction，用于发送数据交换的JSON数据</p>
     *
     * @author hanchao 2018/4/15 19:28
     **/
    static class DataExchangeTask extends RecursiveAction {

        //阈值=运行期可用CPU核数
        private static final int THRESHOLD = Runtime.getRuntime().availableProcessors();
        //开始索引
        private int start;
        //结束索引
        private int end;
        //交换的数据
        List<String> list;

        public DataExchangeTask(int start, int end, List<String> list) {
            this.start = start;
            this.end = end;
            this.list = list;
        }

        @Override
        protected void compute() {
            //如果当前任务数量在阈值范围内，则发送数据
            if (end - start < THRESHOLD) {
                //发送Json数据
                try {
                    sendJsonDate(this.list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //如果当前任务数量超出阈值，则进行任务拆分
                int middle = (start + end) / 2;
                //左边的子任务
                DataExchangeTask left = new DataExchangeTask(start, middle, list);
                //右边的子任务
                DataExchangeTask right = new DataExchangeTask(middle, end, list);
                //并行执行两个“小任务”
                left.fork();
                right.fork();
            }
        }

        /**
         * <p>发送数据</p>
         *
         * @author hanchao 2018/4/15 20:04
         **/
        private void sendJsonDate(List<String> list) throws InterruptedException {
            //遍历
            for (int i = start; i < end; i++) {
                //每个元素都插入到DB2中 ==> 模拟数据发送到DB2
                DB2.add(list.get(i));
            }
            //假定每次发送耗时1ms
            Thread.sleep(1);
        }
    }

    /**
     * <p>模拟从数据库中查询数据并形成JSON个是的数据</p>
     *
     * @author hanchao 2018/4/15 20:21
     **/
    static void queryDataToJson(List list) {
        //随机获取100万~110万个数据
        int count = RandomUtils.nextInt(1000000, 1100000);
        for (int i = 0; i < count; i++) {
            list.add("{\"id\":\"" + UUID.randomUUID() + "\"}");
        }
    }

    /**
     * <p>RecursiveAction-无返回值:可以看成只有fork没有join</p>
     *
     * @author hanchao 2018/4/15 19:26
     **/
    public static void main(String[] args) throws InterruptedException {
        //从数据库中获取所有需要交换的数据
        List dataList = new ArrayList<String>();
        queryDataToJson(dataList);
        int count = dataList.size();
        LOGGER.info("1.从DB1中读取数据并存放到List中,共计读取了" + count + "条数据.");

        //DB2的数据量
        LOGGER.info("2.开始时，DB2中的数据量：" + DB2.size());

        LOGGER.info("3.通过ForkJoin框架进行子任务划分，并发送数据");
        //定义一个ForkJoin线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //定义一个可分解的任务
        DataExchangeTask dataExchangeTask = new DataExchangeTask(0, count, dataList);
        //向ForkJoin线程池提交任务
        forkJoinPool.submit(dataExchangeTask);
        //线程阻塞，等待所有任务完成
        forkJoinPool.awaitTermination(180, TimeUnit.SECONDS);
        //任务完成之后关闭线程池
        forkJoinPool.shutdown();

        //查询最终传输的数据量
        LOGGER.info("4.结束时，DB2中的数据量：" + DB2.size());
    }
}
