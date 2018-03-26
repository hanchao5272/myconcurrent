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

        //Executor--执行器
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

        //ExecutorService -- 执行器服务
        /**
         An {@link Executor} that provides methods to manage termination and methods that can produce a {@link Future} for tracking progress of one or more asynchronous tasks.

         {ExecutorService接口}是一个{执行器}，它可以执行任务终止；而且还提供了一个返回{Future接口}的方法，这个方法可以用于用于追踪一个或多个异步任务的执行情况。

         <p>An {@code ExecutorService} can be shut down, which will cause it to reject new tasks.  Two different methods are provided for shutting down an {@code ExecutorService}.
         The {@link #shutdown} method will allow previously submitted tasks to execute before terminating, while the {@link #shutdownNow} method prevents waiting tasks from starting and attempts to stop currently executing tasks.
         Upon termination, an executor has no tasks actively executing, no tasks awaiting execution, and no new tasks can be submitted.
         An unused {@code ExecutorService} should be shut down to allow reclamation of its resources.

         一个{ExecutorService接口}可以关闭，这种操作会导致它拒绝新的任务。在{ExecutorService接口}中，提供了两种方法用于关闭
         1.shutdown()：允许之前已经提交的方法执行完毕，然后再关闭{执行器}。
         2.shutdonwNow()；阻止正在等待的任务开启，并且会试图停止正在执行的任务，然后关闭{执行器}。
         在{执行器}终止时，它不会有主动执行的任务，不会有等待执行的任务，也不能在被提交新任务。
         一个不再使用的{ExecutorService}应该关闭，以允许JVM回收其所占的资源。

         <p>Method {@code submit} extends base method {@link Executor#execute(Runnable)} by creating and returning a {@link Future} that can be used to cancel execution and/or wait for completion.
         Methods {@code invokeAny} and {@code invokeAll} perform the most commonly useful forms of bulk execution, executing a collection of tasks and then waiting for at least one, or all, to complete.
         (Class {@link ExecutorCompletionService} can be used to write customized variants of these methods.)

         {submit()方法}继承自{Executor}的方法，这个方法创建和返回一个{Future}对象，这个{Future}对象可以被用来取消任务执行或等待任务执行完毕。
         {invokeAny()方法}和{invokeAll()方法}是进行批量执行任务、执行任务集合，然后等待一个获全部任务执行完毕最常用的方法。
         {ExecutorCompletionService}类可以用来对这些方法进行二次开发。

         <p>The {@link Executors} class provides factory methods for the executor services provided in this package.

         {Executors工具类}为此包中的{ExecutorService}提供了工厂方法。

         <h3>Usage Examples</h3>

         Here is a sketch of a network service in which threads in a thread pool service incoming requests.
         It uses the preconfigured {@link Executors#newFixedThreadPool} factory method:

         使用示例
         下面是一个web服务的示例程序，在线程池中的线程对近来的请求提供服务。
         它使用了预先配置的{Executors}的{newFixedThreadPool}来创建线程池。

         <pre> {@code
        class NetworkService implements Runnable {
        private final ServerSocket serverSocket;
        private final ExecutorService pool;

        public NetworkService(int port, int poolSize)
        throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
        }

        public void run() { // run the service
        try {
        for (;;) {
        pool.execute(new Handler(serverSocket.accept()));
        }
        } catch (IOException ex) {
        pool.shutdown();
        }
        }
        }

        class Handler implements Runnable {
        private final Socket socket;
        Handler(Socket socket) { this.socket = socket; }
        public void run() {
        // read and service request on socket
        }
        }}</pre>

         The following method shuts down an {@code ExecutorService} in two phases, first by calling {@code shutdown} to reject incoming tasks, and then calling {@code shutdownNow}, if necessary, to cancel any lingering tasks:

         下面演示了在{ExecutorService}中的两个关闭方法。
         通过第一个关闭方法{shutdonw()}去拒绝继续接收任务。
         然后通过第二个关闭方法(shutdonwNow())在需要的情况下，取消任何拖延的任务。


         <pre> {@code
        void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
        // Wait a while for existing tasks to terminate
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        pool.shutdownNow(); // Cancel currently executing tasks
        // Wait a while for tasks to respond to being cancelled
        if (!pool.awaitTermination(60, TimeUnit.SECONDS))
        System.err.println("Pool did not terminate");
        }
        } catch (InterruptedException ie) {
        // (Re-)Cancel if current thread also interrupted
        pool.shutdownNow();
        // Preserve interrupt status
        Thread.currentThread().interrupt();
        }
        }}</pre>

         <p>Memory consistency effects:
         Actions in a thread prior to the submission of a {@code Runnable} or {@code Callable} task to an {@code ExecutorService}
         <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
         any actions taken by that task, which in turn <i>happen-before</i> the result is retrieved via {@code Future.get()}.

         谨记一致性影响

         在一个线程中，在提交一个{Runnable}或{Callable}任务到一个{ExecutorService}执行器之前的操作  hb  这个任务的执行的任何后续操作。
         反过来 hb 通过{Future.get()}获取结果。

         1.在一个线程中，在提交一个{Runnable}或{Callable}任务到一个{ExecutorService}执行器之前的操作 对 这个任务的执行的任何后续操作可见。
         2.这个任务的执行的任何后续操作 对 通过{Future.get()}获取结果可见。


         @since 1.5
         @author Doug Lea
         */
    }
}
