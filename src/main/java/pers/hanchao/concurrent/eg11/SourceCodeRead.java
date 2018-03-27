package pers.hanchao.concurrent.eg11;

import java.util.concurrent.*;

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

        //ScheduledExecutorService
        /**
         An {@link ExecutorService} that can schedule commands to run after a given delay, or to execute periodically.

         {ScheduledExecutorService}是一个{ExecutorService}，它可以在给定延时之后调度命令运行，也可以定期的调度命令运行。

         <p>The {@code schedule} methods create tasks with various delays and return a task object that can be used to cancel or check execution.
         The {@code scheduleAtFixedRate} and {@code scheduleWithFixedDelay} methods create and execute tasks that run periodically until cancelled.

         {schedule()方法}可以创建含有{delays}变量的任务，然后返回一个可以用于取消或检查运行状态的{Task}。
         {scheduleAtFixedRate()方法}和{scheduleWithFixedDelay()方法}可以创建并运行的任务，这些任务可以定期运行直至取消。

         <p>Commands submitted using the {@link Executor#execute(Runnable)} and {@link ExecutorService} {@code submit} methods are scheduled with a requested delay of zero.
         Zero and negative delays (but not periods) are also allowed in {@code schedule} methods, and are treated as requests for immediate execution.

         通过{Executor#execute(Runnable)方法}和{ExecutorService.submit()方法}提交的命令会以一个{delay=0}的请求被调度。
         在{schedule()方法}中，运行传递{0}或者{负值}的{延时参数}，这些参数将被当做{立刻执行}的请求。

         <p>All {@code schedule} methods accept <em>relative</em> delays and periods as arguments, not absolute times or dates.
         It is a simple matter to transform an absolute time represented as a {@link java.util.Date} to the required form.
         For example, to schedule at a certain future {@code date}, you can use: {@code schedule(task, date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)}.
         Beware however that expiration of a relative delay need not coincide with the current {@code Date} at which the task is enabled due to network time synchronization protocols, clock drift, or other factors.

         所有的{schedule()方法}都接手{相对延时}和{周期}作为参数，但是并不接受{绝对时间}或者{绝对日期}。
         将一{java.util.Date}表示的绝对日期转换成一个需要的形式是一件很简单的事情。
         例如，计划在将来的某个时刻执行任务，你可以这么做：{schedule(task, date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);}
         但是要小心，当任务启用取决于时间同步协议、时钟漂移或其他因素时，相对延时的到期时间不必与当前的{Date}时间一致。


         <p>The {@link Executors} class provides convenient factory methods for the ScheduledExecutorService implementations provided in this package.

         {Executors工具类}提供了方便的工厂方法用于{ScheduledExecutorService}实现。

         <h3>Usage Example</h3>
         Here is a class with a method that sets up a ScheduledExecutorService to beep every ten seconds for an hour:

         示例
         下面这个类拥有一个方法，这个方法启动了一个{ScheduledExecutorService}服务，这个服务在每个小时内有10秒发出哔哔哔的声音。

         <pre> {@code
        import static java.util.concurrent.TimeUnit.*;
        class BeeperControl {
        private final ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(1);

        public void beepForAnHour() {
        final Runnable beeper = new Runnable() {
        public void run() { System.out.println("beep"); }
        };
        final ScheduledFuture<?> beeperHandle =
        scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
        scheduler.schedule(new Runnable() {
        public void run() { beeperHandle.cancel(true); }
        }, 60 * 60, SECONDS);
        }
        }}</pre>

         @since 1.5
         @author Doug Lea
         */


        //ThreadPoolExecutor
        /**
         An {@link ExecutorService} that executes each submitted task using  one of possibly several pooled threads, normally configured  using {@link Executors} factory methods.

         {ThreadPoolExecutor}是一个{ExecutorService}服务，通常情况下它调用{Executors}的工厂方法进行配置，它能够使用一个或多个线程池管理的线程去执行提交的任务。

         <p>Thread pools address two different problems: they usually  provide improved performance when executing large numbers of  asynchronous tasks, due to reduced per-task invocation overhead,  and they provide a means of bounding and managing the resources,  including threads, consumed when executing a collection of tasks.
         Each {@code ThreadPoolExecutor} also maintains some basic  statistics, such as the number of completed tasks.

         {线程池}解决了一些两个不同的任务：
         1.通过减少每个任务的调度开销，提高了执行大批量异步任务的新能。
         2.提供了一种界限和管理资源的方法，包括在执行任务集合时消耗的线程。
         每个{ThreadPoolExecutor}都包含一些基本参数，例如已经完成的任务数量。

         <p>To be useful across a wide range of contexts, this class  provides many adjustable parameters and extensibility  hooks.
         However, programmers are urged to use the more convenient  {@link Executors} factory methods {@link  Executors#newCachedThreadPool} (unbounded thread pool, with  automatic thread reclamation), {@link Executors#newFixedThreadPool}  (fixed size thread pool) and {@link  Executors#newSingleThreadExecutor} (single background thread), that  preconfigure settings for the most common usage  scenarios.
         Otherwise, use the following guide when manually  configuring and tuning this class:

         为了能够在更广泛的上下文中发挥作用，这个类提供了非常多的可调节参数以及可扩展的设定。
         然而，程序猿被要求使用以下更方便的工厂方法：

         - {Executors#newCachedThreadPool}：能够自动回收线程的无边界的线程池。
         - {Executors#newFixedThreadPool}：固定大小的线程池。
         - {Executors#newSingleThreadExecutor}：只能存放单个后台线程的线程池。
         - 以上这些线程池都是为了最常用的场景而预先设定的。

         如果不适用上面预配置的线程池，可以参考下面的指南进行手动的配置和优化：

         <dl>
         <dt>Core and maximum pool sizes</dt>
         <dd>A {@code ThreadPoolExecutor} will automatically adjust the  pool size (see {@link #getPoolSize})  according to the bounds set by  corePoolSize (see {@link #getCorePoolSize}) and  maximumPoolSize (see {@link #getMaximumPoolSize}).

         1.核心线程池大小、最大线程池大小

         一个{ThreadPoolExecutor}会根据设定的{corePoolSize}和{maximumPoolSize}自动的调节线程池大小。

         When a new task is submitted in method {@link #execute(Runnable)},  and fewer than corePoolSize threads are running, a new thread is  created to handle the request, even if other worker threads are  idle.
         If there are more than corePoolSize but less than  maximumPoolSize threads running, a new thread will be created only  if the queue is full.
         By setting corePoolSize and maximumPoolSize  the same, you create a fixed-size thread pool.
         By setting  maximumPoolSize to an essentially unbounded value such as {@code  Integer.
        MAX_VALUE}, you allow the pool to accommodate an arbitrary  number of concurrent tasks.
         Most typically, core and maximum pool  sizes are set only upon construction, but they may also be changed  dynamically using {@link #setCorePoolSize} and {@link  #setMaximumPoolSize}.
         </dd>

         当通过{execute(Runnable)方法}提交了一个新的任务，此时如果小于{corePoolSize}数量的线程正在运行，即使此时其他的工作线程正在空闲，此时线程池也会新建一个线程用来处理这个任务。
         如果当前运行的线程数量介于{corePoolSize}和{maximumPoolSize}之间，只有队列满时，线程池才会创建新的线程。
         通过设置{corePoolSize}和{maximumPoolSize}，你可以创建一个固定大小的线程池。
         通过将{maximumPoolSize}设置为无限大小(例如Integer.MAX_VALUE),你运行线程池容纳任意数量的并发任务。

         典型用法是，在构建线程池时，设定{corePoolSize}和{maximumPoolSize}的值；然而，我们也可以通过{setCorePoolSize()方法}和{setMaximumPoolSize()方法}动态的设置{corePoolSize}和{maximumPoolSize}.


         <dt>On-demand construction</dt>

         <dd>By default, even core threads are initially created and  started only when new tasks arrive, but this can be overridden  dynamically using method {@link #prestartCoreThread} or {@link  #prestartAllCoreThreads}.
         You probably want to prestart threads if  you construct the pool with a non-empty queue.
         </dd>

         2.按需构造

         默认情况下，当新任务到达时，核心线程能够被创建和启动。但是这种情况可以通过使用{prestartCoreThread方法}和{prestartAllCoreThreads方法}动态的重写。
         如果你构建了一个非空队列的线程池，你可能希望去预启动线程。

         <dt>Creating new threads</dt>

         <dd>New threads are created using a {@link ThreadFactory}.
         If not  otherwise specified, a {@link Executors#defaultThreadFactory} is  used, that creates threads to all be in the same {@link  ThreadGroup} and with the same {@code NORM_PRIORITY} priority and  non-daemon status.
         By supplying a different ThreadFactory, you can  alter the thread's name, thread group, priority, daemon status,  etc.
         If a {@code ThreadFactory} fails to create a thread when asked  by returning null from {@code newThread}, the executor will  continue, but might not be able to execute any tasks.
         Threads  should possess the "modifyThread" {@code RuntimePermission}.
         If  worker threads or other threads using the pool do not possess this  permission, service may be degraded: configuration changes may not  take effect in a timely manner, and a shutdown pool may remain in a  state in which termination is possible but not completed.
         </dd>

         3.创建新线程

         新线程时通过{ThreadFactory}创建的。
         如果未另行声明，{ThreadPoolExecutor}使用{Executors#defaultThreadFactory}去创建同一线程组{ThreadGroup}的并且拥有同样优先级(NORM_PRIORITY)的非守护线程。
         通过应用一个不同的{ThreadFactory},你可以改版线程的名字、线程组、优先级和是否为守护线程。
         如果一个{ThreadFactory}创建线程失败并从{newThread()方法}返回了一个null值，则{执行器}会继续运行，但是却不能去执行任何任务。
         线程用该具备{RuntimePermission("modifyThread")}。
         如果一个工作线程或者其他的线程在没有{RuntimePermission("modifyThread")}的情况下使用线程池，服务可能被降级:
         配置变更可能不会及时生效、关闭的线程池可能会任然保留在{正在终止但没有完成}的状态。

         <dt>Keep-alive times</dt>

         <dd>If the pool currently has more than corePoolSize threads,  excess threads will be terminated if they have been idle for more  than the keepAliveTime (see {@link #getKeepAliveTime(TimeUnit)}).
         This provides a means of reducing resource consumption when the  pool is not being actively used.
         If the pool becomes more active  later, new threads will be constructed.
         This parameter can also be  changed dynamically using method {@link #setKeepAliveTime(long,  TimeUnit)}.
         Using a value of {@code Long.
        MAX_VALUE} {@link  TimeUnit#NANOSECONDS} effectively disables idle threads from ever  terminating prior to shut down.
         By default, the keep-alive policy  applies only when there are more than corePoolSize threads.
         But  method {@link #allowCoreThreadTimeOut(boolean)} can be used to  apply this time-out policy to core threads as well, so long as the  keepAliveTime value is non-zero.
         </dd>

         4.存活时间

         如果当前的线程池拥有超过{corePoolSize}数量的线程，且某些线程空闲时间超过了{keepAliveTime}，则这些线程将被终止。
         当{线程池}不是被频繁的使用时，这种机制是一种减少资源消耗的方法。
         如果之后{线程池}变得活跃了，则将创建新的线程。
         这个参数可以通过使用{setKeepAliveTime(long,  TimeUnit)方法}进行动态的修改。
         使用{Long.MAX_VALUE,TimeUnit.NANOSECONDS}可以有效地禁用在关闭之前从未禁止的空闲线程。
         默认情况下，只有当线程池中的线程数量超出{corePoolSize}时，{线程存活策略}才会生效。
         但是，只要{keepAliveTime}不为空，使用{allowCoreThreadTimeOut()方法}可以将{线程存活策略}应用到核心线程上。


         <dt>Queuing</dt>

         <dd>Any {@link BlockingQueue} may be used to transfer and hold  submitted tasks.
         The use of this queue interacts with pool sizing:

         5.队列
         {BlockingQueue}可能被用于传输和持有提交的任务。这个队列的使用会与线程池的大小产生交互。

         <ul>
         <li> If fewer than corePoolSize threads are running, the Executor  always prefers adding a new thread  rather than queuing.
         </li>
         <li> If corePoolSize or more threads are running, the Executor  always prefers queuing a request rather than adding a new  thread.
         </li>
         <li> If a request cannot be queued, a new thread is created unless  this would exceed maximumPoolSize, in which case, the task will be  rejected.
         </li>
         </ul>

         - 如果运行线程数量小于{corePoolSize}，则{执行器}通常会选择添加一个新的线程，而不是将线程排队到{BlockingQueue}中。
         - 如果运行线程数量大于等于{corePoolSize}，则{执行器}通常会选择将线程排队到{BlockingQueue}中，而不是新建一个线程。
         - 如果一个请求不能被添加打{BlockingQueue}中，除非线程数量={maximumPoolSize}(当前线程池拒绝接受任务),否则会新建一个线程。

         There are three general strategies for queuing:

         下面是三种排队策略。

         <ol>

         <li> <em> Direct handoffs.
         </em> A good default choice for a work  queue is a {@link SynchronousQueue} that hands off tasks to threads  without otherwise holding them.
         Here, an attempt to queue a task  will fail if no threads are immediately available to run it, so a  new thread will be constructed.
         This policy avoids lockups when  handling sets of requests that might have internal dependencies.
         Direct handoffs generally require unbounded maximumPoolSizes to  avoid rejection of new submitted tasks.
         This in turn admits the  possibility of unbounded thread growth when commands continue to  arrive on average faster than they can be processed.
         </li>

         5.1.直传
         作为工作队列，一种较好的默认选择是{SynchronousQueue}，它不会持有任务，而是直接将这些任务交给线程。
         在这种情况下，如果没有可用线程能够立即运行任务，则尝试去在队列中添加一个任务会失败，因此将创建一个新的线程。
         这种策略避免了当处理有内部依赖关系的请求集合造成的死机。
         {直传策略}通常需要{无界maximumPoolSizes}以避免拒绝新提交的任务。
         同样的，这种策略，也会导致一种可能：如果新任务持续到达且速率快约线程处理任务的速度，则会导致线程的无限增长。

         <li><em> Unbounded queues.

         </em> Using an unbounded queue (for  example a {@link LinkedBlockingQueue} without a predefined  capacity) will cause new tasks to wait in the queue when all  corePoolSize threads are busy.
         Thus, no more than corePoolSize  threads will ever be created.
         (And the value of the maximumPoolSize  therefore doesn't have any effect.
         )  This may be appropriate when  each task is completely independent of others, so tasks cannot  affect each others execution; for example, in a web page server.
         While this style of queuing can be useful in smoothing out  transient bursts of requests, it admits the possibility of  unbounded work queue growth when commands continue to arrive on  average faster than they can be processed.
         </li>

         5.2.无界队列
         {无界队列(例如使用一个没有定义大小的LinkedBlockingQueue)}会导致当所有的{核心线程}都在忙着的时候，新来的{任务}会加入到队列中尽心等待。
         也因此，不会有多余{corePoolSize}数量的线程会产生({maximumPoolSize参数}实际并没有什么意义)。
         当任务相互独立的，并不会影响各自的运行，例如一个Web页面服务，这种情形适合使用{无界队列}。
         这种策略在解决平滑速率的请求的瞬时突发是十分有用的。
         这种策略也存在这种可能：如果新任务持续到达且速率快约线程处理任务的速度，则会导致队列的无限增长。


         <li><em>Bounded queues.
         </em> A bounded queue (for example, an  {@link ArrayBlockingQueue}) helps prevent resource exhaustion when  used with finite maximumPoolSizes, but can be more difficult to  tune and control.
         Queue sizes and maximum pool sizes may be traded  off for each other: Using large queues and small pools minimizes  CPU usage, OS resources, and context-switching overhead, but can  lead to artificially low throughput.
         If tasks frequently block (for  example if they are I/O bound), a system may be able to schedule  time for more threads than you otherwise allow.
         Use of small queues  generally requires larger pool sizes, which keeps CPUs busier but  may encounter unacceptable scheduling overhead, which also  decreases throughput.
         </li>

         5.3.有界队列
         {有界队列(例如使用ArrayBlockingQueue)}使用有限的{maximumPoolSize}能够防止资源枯竭，但是可能造成资源的难易调配和控制。
         队列大小和最大线程池大小需要户型权衡“使用大嘟列小线程池能够降低CPU使用率、系统资源和上下文切换，但是也可能导致低吞吐量。如果任务频繁的被阻塞(例如它们是I/O绑定的)，那么系统可能会分配时间给超出你允许的线程。
         使用小队列一般需要大线程池，这样能够保持CPU持续繁忙，但是也可能会遇到不可接受的调度开销从而导致吞吐量下降。

         </ol>

         </dd>

         <dt>Rejected tasks</dt>

         <dd>New tasks submitted in method {@link #execute(Runnable)} will be  <em>rejected</em> when the Executor has been shut down, and also when  the Executor uses finite bounds for both maximum threads and work queue  capacity, and is saturated.
         In either case, the {@code execute} method  invokes the {@link  RejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)}  method of its {@link RejectedExecutionHandler}.
         Four predefined handler  policies are provided:

         6。拒绝任务

         在以下两种情况下，在{submit(Runnable)方法}中新提交的任务可能被拒绝：
         - {执行器}已经关闭。
         - {执行器}使用有限的线程池大小和工作队列大小，并且都已经饱和。

         以上任何一种情况，{execute()方法}都会调用{RejectedExecutionHandler}的{rejectedExecution(Runnable, ThreadPoolExecutor)方法}。

         提供了四种预定义的处理策略：

         <ol>

         <li> In the default {@link ThreadPoolExecutor.
        AbortPolicy}, the  handler throws a runtime {@link RejectedExecutionException} upon  rejection.
         </li>

         <li> In {@link ThreadPoolExecutor.
        CallerRunsPolicy}, the thread  that invokes {@code execute} itself runs the task.
         This provides a  simple feedback control mechanism that will slow down the rate that  new tasks are submitted.
         </li>

         <li> In {@link ThreadPoolExecutor.
        DiscardPolicy}, a task that  cannot be executed is simply dropped.
         </li>

         <li>In {@link ThreadPoolExecutor.
        DiscardOldestPolicy}, if the  executor is not shut down, the task at the head of the work queue  is dropped, and then execution is retried (which can fail again,  causing this to be repeated.
         ) </li>

         </ol>

         - {ThreadPoolExecutor.AbortPolicy}：默认策略。这种策略情况下，会在拒绝的时候抛出一个运行期的{RejectedExecutionException}异常。
         - {ThreadPoolExecutor.CallerRunsPolicy}：调用{execute()方法}的线程会自己运行这个任务。这种策略提供了一种简单的反馈控制机制，可以降低新任务提交的速率。
         - {ThreadPoolExecutor.DiscardPolicy}：如果一个任务不能被执行，那么久放弃它。
         - {ThreadPoolExecutor.DiscardOldestPolicy}：当{执行器}并未关闭时，位于队列头部的任务将被放弃，然后{执行器}互再次尝试运行{execute()方法(这种再次运行可能还会失败，导致上面的步骤重复进行)}。

         It is possible to define and use other kinds of {@link  RejectedExecutionHandler} classes.
         Doing so requires some care  especially when policies are designed to work only under particular  capacity or queuing policies.
         </dd>

         定义和使用其他种类的{RejectedExecutionHandler}类也是可以的。
         当自定义策略是为了特点容量或者排队策略而设计时，需要十分注意。

         <dt>Hook methods</dt>

         <dd>This class provides {@code protected} overridable  {@link #beforeExecute(Thread, Runnable)} and  {@link #afterExecute(Runnable, Throwable)} methods that are called  before and after execution of each task.
         These can be used to  manipulate the execution environment; for example, reinitializing  ThreadLocals, gathering statistics, or adding log entries.
         Additionally, method {@link #terminated} can be overridden to perform  any special processing that needs to be done once the Executor has  fully terminated.

         <p>If hook or callback methods throw exceptions, internal worker  threads may in turn fail and abruptly terminate.
         </dd>

         7.挂钩方法

         这个类提供了两个{protected}修饰的可以进行重写(Override)的挂钩方法：
         - beforeExecute(Thread, Runnable)：可以在每个任务运行之前进行调用。
         - afterExecute(Runnable, Throwable)：可以在每个任务运行之后进行调用。

         这些方法可以用来处理任务运行环境，例如：重新初始化线程本地变量({ThreadLocals})、收集统计数据和添加日志条目。
         另外，{terminated方法}也可被重写，以便能够在执行器完全终止之后来执行一些特殊操作。

         如果挂钩方法和回调方法抛出异常，内部工作线程可能会失败并突然终止。

         <dt>Queue maintenance</dt>

         <dd>Method {@link #getQueue()} allows access to the work queue  for purposes of monitoring and debugging.
         Use of this method for  any other purpose is strongly discouraged.
         Two supplied methods,  {@link #remove(Runnable)} and {@link #purge} are available to  assist in storage reclamation when large numbers of queued tasks  become cancelled.
         </dd>

         8.队列维护

         为了监控和调试的目的，{getQueue()方法}允许访问工作队列。强烈禁止使用此方法用于其他用途。
         当队列的大量任务被取消时，{remove(Runnable)方法}和{purge方法}可以被用于协助资源回收。

         <dt>Finalization</dt>

         <dd>A pool that is no longer referenced in a program <em>AND</em>  has no remaining threads will be {@code shutdown} automatically.
         If  you would like to ensure that unreferenced pools are reclaimed even  if users forget to call {@link #shutdown}, then you must arrange  that unused threads eventually die, by setting appropriate  keep-alive times, using a lower bound of zero core threads and/or  setting {@link #allowCoreThreadTimeOut(boolean)}.
         </dd>

         9.

         如果一个线程池不再在程序中被引用而且此线程池没有剩余的线程，这这个{线程池}会被自动关闭。

         如果你想确保未引用的线程池能够被回收(尽管用户忘记去关闭它们)，那么你必须通过{keep-alive时间}、使用零核心线程的下限或者设置allowCoreThreadTimeOut(boolean)来合理安排，以便让未使用的线程池最终死亡。
         </dl>

         <p><b>Extension example</b>.
         Most extensions of this class  override one or more of the protected hook methods.
         For example,  here is a subclass that adds a simple pause/resume feature:

         扩展用法
         这个类的大多数扩展鳄梨都是会重写一个或多个受保护的{挂钩方法}。
         例如，下面是一个添加了简单的暂停/恢复特征的子类：

         <pre> {@code
        class PausableThreadPoolExecutor extends ThreadPoolExecutor {
        private boolean isPaused;
        private ReentrantLock pauseLock = new ReentrantLock();
        private Condition unpaused = pauseLock.newCondition();

        public PausableThreadPoolExecutor(...) { super(...); }

        protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
        while (isPaused) unpaused.await();
        } catch (InterruptedException ie) {
        t.interrupt();
        } finally {
        pauseLock.unlock();
        }
        }

        public void pause() {
        pauseLock.lock();
        try {
        isPaused = true;
        } finally {
        pauseLock.unlock();
        }
        }

        public void resume() {
        pauseLock.lock();
        try {
        isPaused = false;
        unpaused.signalAll();
        } finally {
        pauseLock.unlock();
        }
        }
        }}</pre>

         @since 1.5
         @author Doug Lea
         */
        // ScheduledThreadPoolExecutor
        /**
         A {@link ThreadPoolExecutor} that can additionally schedule  commands to run after a given delay, or to execute  periodically.
         This class is preferable to {@link java.util.Timer}  when multiple worker threads are needed, or when the additional  flexibility or capabilities of {@link ThreadPoolExecutor} (which  this class extends) are required.

         {ScheduledThreadPoolExecutor}是一个{ThreadPoolExecutor}，他能够额外的调度任务在一个给定的延时之后运行或则周期性的运行。
         当需要多个工作线程或者当需要额外的灵活性和{ThreadPoolExecutor}的额外能力时，这个类比{java.util.Timer}更加合适。

         <p>Delayed tasks execute no sooner than they are enabled, but  without any real-time guarantees about when, after they are  enabled, they will commence.
         Tasks scheduled for exactly the same  execution time are enabled in first-in-first-out (FIFO) order of  submission.

         {延时任务}的执行肯定发生于他们启动之后，但是如果没有任何实际的时间确定何时执行，当启动时，他们就会执行。
         任务执行的时间与他们提交的顺序保持一致，也就是所谓的{FIFO(先进先出，first-in-first-out)}。

         <p>When a submitted task is cancelled before it is run, execution  is suppressed.
         By default, such a cancelled task is not  automatically removed from the work queue until its delay  elapses.
         While this enables further inspection and monitoring, it  may also cause unbounded retention of cancelled tasks.
         To avoid  this, set {@link #setRemoveOnCancelPolicy} to {@code true}, which  causes tasks to be immediately removed from the work queue at  time of cancellation.

         当一个提交的任务在运行前被取消了，则它的执行将被禁止。
         默认情况下，这种取消了的任务并不会自动的从工作队列中移除，除非它的延时时间过期。
         虽然这么做可以保证进一步的检查和监视，但是也可能会导致取消任务的无限保留。
         为了避免这么做，可以调用{setRemoveOnCancelPolicy(true)}，此方法会使得任务一旦被取消将立即被移除。

         <p>Successive executions of a task scheduled via  {@code scheduleAtFixedRate} or  {@code scheduleWithFixedDelay} do not overlap.
         While different  executions may be performed by different threads, the effects of  prior executions <a  href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>  those of subsequent ones.

         通过{scheduleAtFixedRate方法}或者{scheduleWithFixedDelay方法}可以连续执行一个任务，而且两种方式并不相交。
         当不同的任务可能会被不同的线程执行，那些优先执行的操作 hb 那些随后执行的操作。
         当不同的任务可能会被不同的线程执行，那些优先执行的操作结果 对 那些随后执行的操作结果 可见。

         <p>While this class inherits from {@link ThreadPoolExecutor}, a few  of the inherited tuning methods are not useful for it.
         In  particular, because it acts as a fixed-sized pool using  {@code corePoolSize} threads and an unbounded queue, adjustments  to {@code maximumPoolSize} have no useful effect.
         Additionally, it  is almost never a good idea to set {@code corePoolSize} to zero or  use {@code allowCoreThreadTimeOut} because this may leave the pool  without threads to handle tasks once they become eligible to run.

         虽然{ScheduledThreadPoolExecutor}继承自{ThreadPoolExecutor}，但是有一些继承的方法并不适用于它。
         特别要注意，作为一个固定大小的线程池，它使用核心池大小的线程并且使用一个无界的队列，这导致{maximumPoolSize}这个参数并没有实际作用。
         此外，千万不要讲{corePoolSize}设置为0，或者使用{allowCoreThreadTimeOut}。因为这么做会导致一旦线程有资格去运行，他们会留下一个没有线程处理任务的线程池。

         <p><b>Extension notes:</b>
         This class overrides the  {@link ThreadPoolExecutor#execute(Runnable) execute} and  {@link AbstractExecutorService#submit(Runnable) submit}  methods to generate internal {@link ScheduledFuture} objects to  control per-task delays and scheduling.
         To preserve  functionality, any further overrides of these methods in  subclasses must invoke superclass versions, which effectively  disables additional task customization.
         However, this class  provides alternative protected extension method  {@code decorateTask} (one version each for {@code Runnable} and  {@code Callable}) that can be used to customize the concrete task  types used to execute commands entered via {@code execute},  {@code submit}, {@code schedule}, {@code scheduleAtFixedRate},  and {@code scheduleWithFixedDelay}.
         By default, a  {@code ScheduledThreadPoolExecutor} uses a task type extending  {@link FutureTask}.
         However, this may be modified or replaced using  subclasses of the form:

         额外注释：

         这个类重写了{ThreadPoolExecutor#execute(Runnable)方法}和{AbstractExecutorService#submit(Runnable)方法}，以此来生成内部{ScheduledFuture}对象，用来控制每个任务的延时和调度。
         为了保护这个功能，在子类中，这些方法的重写方法都必须调用父类的版本，从而有效的禁用其他类型的任务定制。
         但是，这个类也提供了可以替代的的受保护的扩展方法{decorateTask}。{decorateTask}可以被用来自定义具体的任务类型。这些自定义的任务类型通过{execute()方法}、{submit()方法}、{schedule()方法}、{(scheduleAtFixedRate)方法}和{(scheduleWithFixedDelay)方法}来执行命令。

         默认情况下，{ScheduledThreadPoolExecutor}使用{FutureTask}的任务类型。
         然而，这种默认配置可以通过子类的形式进行修改和替换。



         <pre> {@code
        public class CustomScheduledExecutor extends ScheduledThreadPoolExecutor {

        static class CustomTask<V> implements RunnableScheduledFuture<V> { ...
        }

        protected <V> RunnableScheduledFuture<V> decorateTask(
        Runnable r, RunnableScheduledFuture<V> task) {
        return new CustomTask<V>(r, task);
        }

        protected <V> RunnableScheduledFuture<V> decorateTask(
        Callable<V> c, RunnableScheduledFuture<V> task) {
        return new CustomTask<V>(c, task);
        }
        // ...
        add constructors, etc.
        }}</pre>

         @since 1.5
         @author Doug Lea
         */

        //Executors
        /**
         Factory and utility methods for {Executor}, { ExecutorService}, {ScheduledExecutorService}, { ThreadFactory}, and {Callable} classes defined in this  package.
         This class supports the following kinds of methods:

         <ul>
         <li> Methods that create and return an {ExecutorService} set up with commonly useful configuration settings.
         <li> Methods that create and return a {ScheduledExecutorService} set up with commonly useful configuration settings.
         <li> Methods that create and return a "wrapped" ExecutorService, that disables reconfiguration by making implementation-specific methods inaccessible.
         <li> Methods that create and return a {ThreadFactory} that sets newly created threads to a known state.
         <li> Methods that create and return a {Callable} out of other closure-like forms, so they can be used in execution methods requiring {@code Callable}.
         </ul>

         这个类定义了供{Executor}、{ExecutorService}、{ScheduledExecutorService}、{ ThreadFactory}和{Callable}这些接口和类使用的工厂方法和工具方法。

         - 提供了方法，用于创建和返回一个用通常有用的设置配置好的{ExecutorService}对象。
         - 提供了方法，用于创建和返回一个用通常有用的设置配置好的{ScheduledExecutorService}对象。
         - 提供了方法，用于创建和返回一个包装好的{ExecutorService}对象。通过使可以实现的方法无法访问，这个{ExecutorService}对象无法重构。
         - 提供了方法，用于创建和返回一个{ThreadFactory}对象。这个{ThreadFactory}对象会将新创建的线程设置为已知状态。
         - 提供了方法，用于创建和返回一个{Callable}对象。这个{Callable}对象不同于其他闭包形式，所以他们被需要{Callable}参数的执行方法中使用。

         @since 1.5
         @author Doug Lea
         */
    }
}
