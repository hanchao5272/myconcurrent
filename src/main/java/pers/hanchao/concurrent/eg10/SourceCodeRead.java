package pers.hanchao.concurrent.eg10;

import java.util.concurrent.*;

/**
 * 源代码翻译
 * Created by 韩超 on 2018/3/26.
 */
public class SourceCodeRead {
    /**
     * <p>Title: </p>
     *
     * @author 韩超 2018/3/26 9:34
     */
    public static void main(String[] args) {

        //Future
        /**
         * A {@code Future} represents the result of an asynchronous computation.
         Methods are provided to check if the computation is complete, to wait for its completion, and to retrieve the result of the computation.
         The result can only be retrieved using method {@code get} when the computation has completed, blocking if necessary until it is ready.
         Cancellation is performed by the {@code cancel} method.  Additional methods are provided to determine if the task completed normally or was cancelled.
         Once a computation has completed, the computation cannot be cancelled.
         If you would like to use a {@code Future} for the sake of cancellability but not provide a usable result, you can declare types of the form {@code Future<?>} and return {@code null} as a result of the underlying task.

         {Future接口}代表着异步计算的结果。
         {Future接口}提供了一些方法用于：查看计算是否完成、等待计算完成和获取计算结果。
         只有在计算完成时，才能通过{get()}方法获取计算结果，必要时程序会阻塞直到计算完成。
         取消计算是通过方法{cancel()}执行的。此外，还提供了一些其他的方法用于确认任务是否正常(isDone())或者是否取消(isCancelled())。
         一旦一个计算已经完成，则这个计算不能再被取消。
         如果你因为{可取消性}而想使用{Future接口}，但是有不能提供一个可用的计算结果，那么你可以将其声明为{Future<?>}类型，并且{null}值作为返回值。

         <b>Sample Usage</b> (Note that the following classes are all made-up.)

         示例用法(注意下面所有的类都是虚构的)


         <pre> {@code
        interface ArchiveSearcher { String search(String target); }
        class App {
        ExecutorService executor = ...
        ArchiveSearcher searcher = ...
        void showSearch(final String target)
        throws InterruptedException {
        Future<String> future
        = executor.submit(new Callable<String>() {
        public String call() {
        return searcher.search(target);
        }});
        displayOtherThings(); // do other things while searching
        try {
        displayText(future.get()); // use future
        } catch (ExecutionException ex) { cleanup(); return; }
        }
        }}</pre>

         The {@link FutureTask} class is an implementation of {@code Future} that implements {@code Runnable}, and so may be executed by an {@code Executor}.
         For example, the above construction with {@code submit} could be replaced by:

         {FutureTask类}是一个实现了{Future接口}和{Runnable接口}的实现类，所以可以被{Executor接口}执行。
         例如，上面的{submit()方法}中的构造方法可以替换成如下的形式：

         <pre> {@code
        FutureTask<String> future =
        new FutureTask<String>(new Callable<String>() {
        public String call() {
        return searcher.search(target);
        }});
        executor.execute(future);}</pre>

         <p>Memory consistency effects:
         Actions taken by the asynchronous computation <a href="package-summary.html#MemoryVisibility"> <i>happen-before</i></a> actions following the corresponding {@code Future.get()} in another thread.

         谨记一致性影响：
         异步计算的操作 hb 另一个线程中{Future.get()方法}之后的操作。
         依旧是说 异步计算的结果 对 另一个线程中 {Future.get()方法}之后的操作 可见。

         @see FutureTask
         @see Executor
         @since 1.5
         @author Doug Lea
         @param <V> The result type returned by this Future's {@code get} method
         */

        //Callable
        /**
         * A task that returns a result and may throw an exception.
         Implementors define a single method with no arguments called {@code call}.

         {Callable接口}是一种能够返回计算结果并且可以抛出异常的任务。
         实现类需要定义一个无参数的方法：{call()}.

         <p>The {@code Callable} interface is similar to {@link java.lang.Runnable}, in that both are designed for classes whose instances are potentially executed by another thread.
         A {@code Runnable}, however, does not return a result and cannot throw a checked exception.

         {Callable接口}与{Runnable接口}类似，都是为了让其实例对象被其他线程执行的目的而设计的。
         但是，{Runnable接口}并不能返回结果，也不能抛出一个可检查的异常。

         <p>The {@link Executors} class contains utility methods to convert from other common forms to {@code Callable} classes.

         {Executors工具类}包含一些工具方法，这些方法能够将一些其他常见的形式转换成{Callable接口}的实现类。

         @see Executor
         @since 1.5
         @author Doug Lea
         @param <V> the result type of method {@code call}
         */

        //FutureTask
        /**
         *  A cancellable asynchronous computation.
         This class provides a base implementation of {@link Future}, with methods to start and cancel a computation, query to see if the computation is complete, and retrieve the result of the computation.
         The result can only be retrieved when the computation has completed; the {@code get} methods will block if the computation has not yet completed.
         Once the computation has completed, the computation cannot be restarted or cancelled (unless the computation is invoked using {@link #runAndReset}).

         {FutureTask类}表示一个可以取消的异步计算任务。
         这个类提供了对{Future接口}的简单实现，提供了一些方法：开启计算、取消计算、查询计算是否完成和查询计算结果。
         只有计算完成时，才可以通过{get()方法}获取计算结果；如果计算没有完成，则{get()方法}会一致在阻塞。

         <p>A {@code FutureTask} can be used to wrap a {@link Callable} or {@link Runnable} object.
         Because {@code FutureTask} implements {@code Runnable}, a {@code FutureTask} can be submitted to an {@link Executor} for execution.

         一个{FutureTask类}可以被用于包装{Callable接口}或者{Runnable接口}的实现对象。
         因为{FutureTask类}实现了{Runnable接口}，所以它可以被提交(submite)给一个{Executor接口}进行执行。


         <p>In addition to serving as a standalone class, this class provides {@code protected} functionality that may be useful when creating customized task classes.

         除了作为一个单独的类使用之外，这个类还提供了{protected作用域}的方法，当创建定制的任务类时，这些方法可能十分有用。

         @since 1.5
         @author Doug Lea
         @param <V> The result type returned by this FutureTask's {@code get} methods
         */
    }
}
