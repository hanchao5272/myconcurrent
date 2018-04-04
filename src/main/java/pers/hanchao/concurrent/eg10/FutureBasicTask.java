package pers.hanchao.concurrent.eg10;

import java.util.concurrent.*;

/**
 * <p>Future接口基本使用</p>
 *
 * @author hanchao 2018/4/2 22:30
 **/
public class FutureBasicTask {
    /**
     * <p>Future接口的基本使用</p>
     *
     * @author hanchao 2018/4/2 22:30
     **/
    public static void main(String[] args) {
        //定义一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义一个Callable对象
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                //模拟开始计算
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println("正在计算，被取消了");
                }
                //计算结束，返回结果
                return 100;
            }
        };
        //提交一个Callable任务，返回一个Future对象
        Future<Integer> future = executorService.submit(callable);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //isDone():任务是否完成
        System.out.println("1000ms的时候计算是否完成：" + future.isDone());
        //isCancelled():任务是否取消了
        System.out.println("1000ms的时候计算是否取消：" + future.isCancelled());

        //cancel取消-true表示即使在运行也取消，false表示如果没运行可以取消
        future.cancel(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //isDone():任务是否完成
        System.out.println("cancel之后计算是否完成：" + future.isDone());
        //isCancelled():任务是否取消了
        System.out.println("cancel之后计算是否取消：" + future.isCancelled());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //再次提交一个Callable任务，返回一个Future对象
        future = executorService.submit(callable);

        //在2秒时限内尝试去获取
        //获取到则返回结果
        //获取不到则返回
        try {
            System.out.println("在2秒时限内尝试去获取：" + future.get(2, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            //e.printStackTrace();
            System.out.println("timeout超时，抛出TimeoutException异常");
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("线程interrupt，抛出InterruptedException异常");
        } catch (ExecutionException e) {
            //e.printStackTrace();
            System.out.println("执行出错，抛出ExecutionException异常");
        }
        //isDone():任务是否完成
        System.out.println("get(timeout,TimeUnit)超时之后，计算是否完成：" + future.isDone());
        //isCancelled():任务是否取消了
        System.out.println("get(timeout,TimeUnit)超时之后，cancel之后计算是否取消：" + future.isCancelled());

        //阻塞的等待计算结果，直到获取计算结果
        try {
            System.out.println("阻塞的等待计算结果，直到获取计算结果：" + future.get());
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("线程interrupt，抛出InterruptedException异常");
        } catch (ExecutionException e) {
            //e.printStackTrace();
            System.out.println("执行出错，抛出ExecutionException异常");
        }
        //isDone():任务是否完成
        System.out.println("get()之后，计算是否完成：" + future.isDone());
        //isCancelled():任务是否取消了
        System.out.println("get()之后，cancel之后计算是否取消：" + future.isCancelled());

        //取消线程池
        executorService.shutdown();
    }
}
