package pers.hanchao.concurrent.eg11;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>ThreadPoolExecutor-线程池执行器-示例</p>
 *
 * @author hanchao 2018/4/5 14:21
 **/
public class ThreadPoolExecutorDemo {
    private static final Logger LOGGER = Logger.getLogger(ThreadPoolExecutorDemo.class);

    /**
     * <p>线程池执行器示例</p>
     *
     * @author hanchao 2018/4/5 14:22
     **/
    public static void main(String[] args) {
        /**
         * 参数1：corePoolSize     核心线程池大小 threadPoolExecutor.setCorePoolSize(11);
         *      核心：
         *      默认情况下核心线程会一直存活，即使任务执行完毕；除非allowCoreThreadTimeout被设置为true，才会超时退出
         * 参数2：maximumPoolSize  最大线程池大小 threadPoolExecutor.setMaximumPoolSize(22);
         *      表示线程池的最大处理能力，通俗的将：逼不得已才会用
         *
         *      新任务到达：
         *      如果当前线程数量<corePoolSize,则创建新的线程处理新任务
         *      如果corePoolSize<=当前线程池数量<maximumPoolSize,且workQueue未满，则将新任务添加到workQueue中
         *      如果corePoolSize<=当前线程池数量<maximumPoolSize,且workQueue已满，则创建新线程
         *      如果当前线程数量>=maximumPoolSize,且workQueue未满，则将新任务添加到workQueue中
         *      如果当前线程数量>=maximumPoolSize,且workQueue已满，则抛出异常，拒绝任务
         *
         * 参数3和4：keepAliveTime和TimeUnit    存活时间 threadPoolExecutor.setKeepAliveTime(1000,TimeUnit.MILLISECONDS);
         *      当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。
         *      如果allowCoreThreadTimeout设置为true，则所有线程均会退出直到线程数量为0。
         *
         * 参数5：workQueue        工作队列
         *      影响到在corePoolSize<=当前线程池数量的情况下，何时创建新线程，即：实际存活线程池数
         *      三种队列：
         *      1.直传      SynchronousQueue    它不会持有任务，而是直接将这些任务交给线程。
         *      2.无界队列  LinkedBlockingQueue  所有的{核心线程}都在忙着的时候，新来的{任务}会加入到队列中尽心等待
         *      3.有界队列  ArrayBlockingQueue  使用有限的{maximumPoolSize}能够防止资源枯竭，但是可能造成资源的难易调配和控制。
         *
         * 参数6：threadFactory    threadPoolExecutor.setThreadFactory(null);
         *      默认情况下，{ThreadPoolExecutor}使用{Executors#defaultThreadFactory}去创建同一线程组{ThreadGroup}
         *      的并且拥有同样优先级(NORM_PRIORITY)的非守护线程。
         *
         *
         * 字段7：rejectedExecutionHandler 拒绝任务处理器 threadPoolExecutor.setRejectedExecutionHandler(null);
         *      在以下两种情况下，在{submit(Runnable)方法}中新提交的任务可能被拒绝：
         *       - {执行器}已经关闭。
         *       - {执行器}使用有限的线程池大小和工作队列大小，并且都已经饱和。
         *      四种策略：
         *      1.默认策略。这种策略情况下，会在拒绝的时候抛出一个运行期的{RejectedExecutionException}异常。
         *      2.调用{execute()方法}的线程会自己运行这个任务。这种策略提供了一种简单的反馈控制机制，可以降低新任务提交的速率。
         *      3.如果一个任务不能被执行，那么久放弃它。
         *      4.当{执行器}并未关闭时，位于队列头部的任务将被放弃，然后{执行器}互再次尝试运行{execute()方法(这种再次运行可能还会失败，导致上面的步骤重复进行)}。
         *
         * 8.挂钩方法 beforeExecute afterExecute
         *          - beforeExecute(Thread, Runnable)：可以在每个任务运行之前进行调用。
         *          - afterExecute(Runnable, Throwable)：可以在每个任务运行之后进行调用。
         *
         * 9.队列维护
         *       为了监控和调试的目的，{getQueue()方法}允许访问工作队列。强烈禁止使用此方法用于其他用途。
         *        当队列的大量任务被取消时，{remove(Runnable)方法}和{purge方法}可以被用于协助资源回收。
         *
         * 10.关闭 shutdown shutdownNow
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                0,
                2,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                null);
    }
}
