package pers.hanchao.concurrent.eg17;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * <p>ForkJoin框架-RecursiveTask-有返回值-超大集合分割计算</p>
 *
 * @author hanchao 2018/4/15 21:31
 **/
public class RecursiveTaskDemo {
    private static final Logger LOGGER = Logger.getLogger(RecursiveTaskDemo.class);

    /**
     * <p>超大集合计算任务-泛型类</p>
     *
     * @author hanchao 2018/4/15 21:34
     **/
    static class LargeSetComputeTask extends RecursiveTask<Long> {

        //阈值=JVM运行时的可用CPU核数
        private static final int THRESHOLD = 100000;
        private int start;//开始下标
        private int end;//结束下标

        public LargeSetComputeTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            //如果当前任务的计算量在阈值范围内，则直接进行计算
            if (end - start < THRESHOLD) {
                return computeByUnit();
            } else {//如果当前任务的计算量超出阈值范围，则进行计算任务拆分
                //计算中间索引
                int middle = (start + end) / 2;
                //定义子任务-迭代思想
                LargeSetComputeTask left = new LargeSetComputeTask(start, middle);
                LargeSetComputeTask right = new LargeSetComputeTask(middle, end);
                //划分子任务-fork
                left.fork();
                right.fork();
                //合并计算结果-join
                return left.join() + right.join();
            }
        }

        /**
         * <p>最小计算单元进行计算</p>
         *
         * @author hanchao 2018/4/15 21:39
         **/
        private long computeByUnit() {
            long sum = 0L;
            for (int i = start; i < end; i++) {
                sum += i + i;
            }
            return sum;
        }
    }

    /**
     * <p>ForkJoin框架-RecursiveTask</p>
     * <p>1.有返回值：可用Future接口进行结果获取</p>
     * <p>2.RecursiveTask需要fork和join并用</p>
     *
     * @author hanchao 2018/4/15 21:44
     **/
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //计算(0+1+2+3+1000000000)*2的结果
        int count = 1000000001;

        //第一种方式：单线程计算
        long start1 = System.currentTimeMillis();
        LOGGER.info("1.第一种计算方式--单线程计算");
        long result = 0L;
        for (long i = 0; i < count; i++) {
            result += i + i;
        }
        LOGGER.info("1.计算结果：" + result + ",用时：" + (System.currentTimeMillis() - start1) + "ms.\n");

        //通过ForkJoin框架进行子任务计算
        long start2 = System.currentTimeMillis();
        LOGGER.info("2.第二种计算方式--ForkJoin框架计算");
        //定义ForkJoinPool线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //定义计算任务
        LargeSetComputeTask computeTask = new LargeSetComputeTask(0, count);
        //提交计算任务
        Future<Long> future = forkJoinPool.submit(computeTask);
        //执行完任务关闭线程池
        forkJoinPool.shutdown();
        //输出计算结果：
        LOGGER.info("2.计算结果：" + future.get() + ",用时：" + (System.currentTimeMillis() - start2) + "ms.");
    }
}
