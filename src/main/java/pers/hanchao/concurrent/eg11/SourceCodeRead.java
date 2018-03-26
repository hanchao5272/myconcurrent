package pers.hanchao.concurrent.eg11;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>源码阅读-Executor系列</p>
 * @author hanchao 2018/3/26 19:57
 **/
public class SourceCodeRead {
    /**
     * <p></p>
     * @author hanchao 2018/3/26 19:57
     **/
    public static void main(String[] args) {

        //Executor
        /**
         *  An object that executes submitted {@link Runnable} tasks.
         This interface provides a way of decoupling task submission from the mechanics of how each task will be run, including details of thread use, scheduling, etc.
         An {@code Executor} is normally used instead of explicitly creating threads.
         For example, rather than invoking {@code new Thread(new(RunnableTask())).start()} for each of a set of tasks, you might use:

         {Executor}，又称为执行器，表示一个对象，这个对象可以执行已经提交的{Runnable接口}类型的{任务}。
         这个接口提供了一种将{任务提交}从{每个任务如何运行}(包括在线程使用、调度等待)分离出来的方式。
         {Executor接口}通常用来替代{显式地}创建线程。
         例如，相较于为每一组任务调用{new Thread(new(RunnableTask())).start()}，你也可以使用下面的方式：

         <pre>
         Executor executor = <em>anExecutor</em>;
         executor.execute(new RunnableTask1());
         executor.execute(new RunnableTask2());
         ...
         </pre>

         However, the {@code Executor} interface does not strictly require that execution be asynchronous.
         In the simplest case, an executor can run the submitted task immediately in the caller's thread:

         然而，{Executor接口}并不是严格的要求运行是异步的。
         在最简单的情况下，{executor}可以在{调用者的线程中}立即运行提交的任务：

         <pre> {@code
        class DirectExecutor implements Executor {
        public void execute(Runnable r) {
        r.run();
        }
        }}</pre>

         More typically, tasks are executed in some thread other than the caller's thread.  The executor below spawns a new thread for each task.

         更典型的是，{任务}运行在其他线程中，而非调用者的线程中。
         下面的{执行器}为每一个任务开启一个新线程：

         <pre> {@code
        class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
        new Thread(r).start();
        }
        }}</pre>

         Many {@code Executor} implementations impose some sort of limitation on how and when tasks are scheduled.
         The executor below serializes the submission of tasks to a second executor, illustrating a composite executor.

         很多{Executor接口}的实现类对如何以及何时调度任务施加了一些限制。
         下面的{执行器}将任务提交给第二个{执行器}，构建了一个组合型的{执行器}。


         <pre> {@code
        class SerialExecutor implements Executor {
        final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
        final Executor executor;
        Runnable active;

        SerialExecutor(Executor executor) {
        this.executor = executor;
        }

        public synchronized void execute(final Runnable r) {
        tasks.offer(new Runnable() {
        public void run() {
        try {
        r.run();
        } finally {
        scheduleNext();
        }
        }
        });
        if (active == null) {
        scheduleNext();
        }
        }

        protected synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
        executor.execute(active);
        }
        }
        }}</pre>

         The {@code Executor} implementations provided in this package implement {@link ExecutorService}, which is a more extensive interface.
         The {@link ThreadPoolExecutor} class provides an extensible thread pool implementation.
         The {@link Executors} class provides convenient factory methods for these Executors.

         {ExecutorService接口}是{Executor接口}的一种实现，是一种更加广泛的接口。
         {ThreadPoolExecutor类}提供了一个可扩展的线程池的实现。
         {Executors工具类}为{执行器}提供了方便的工厂方法。

         <p>Memory consistency effects:
         Actions in a thread prior to submitting a {@code Runnable} object to an {@code Executor} <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a> its execution begins, perhaps in another thread.

         谨记一致性影响

         在一个线程中，提交一个{Runnable接口}类型的任务到一个{执行器}之前的操作 hb 它执行之后的操作(即便是在另一个线程中)。
         在一个线程中，提交一个{Runnable接口}类型的任务到一个{执行器}之前的操作 对 它执行之后的操作(即便是在另一个线程中)可见。


         @since 1.5
         @author Doug Lea

         public interface Executor {

             /**
             Executes the given command at some time in the future.
             The command may execute in a new thread, in a pooled thread, or in the calling thread, at the discretion of the {@code Executor} implementation.

             在将来的某个时刻，执行给定的命令。
             基于{执行器}的实现方式的不同，{给定的命令}可能执行在一个新线程中、一个线程池管理的线程中或者实一个调用线程中。

             @param command the runnable task
             @throws RejectedExecutionException if this task cannot be
             accepted for execution
             @throws NullPointerException if command is null
             /
             void execute(Runnable command);
         }
         */
    }
}
