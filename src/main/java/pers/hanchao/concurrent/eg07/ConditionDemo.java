package pers.hanchao.concurrent.eg07;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Lock接口-Condition学习-可中断锁、可定时锁</p>
 *
 * @author hanchao 2018/3/18 14:45
 **/
public class ConditionDemo {
    //定义一个非公平的锁
    private static Lock lock = new ReentrantLock(false);

    /**
     * <p>Lock接口-Condition学习</p>
     *
     * @author hanchao 2018/3/18 14:46
     **/
    public static void main(String[] args) throws InterruptedException {
        Condition condition = lock.newCondition();
        //线程0：通过await()进行等待，直到被中断或者被唤醒
        Thread thread0 = new Thread(() -> {
            System.out.println("线程[await()-" + Thread.currentThread().getName() + "]尝试获取锁...");
            lock.lock();
            System.out.println("线程[await()-" + Thread.currentThread().getName() + "]获取了锁.");
            try {
                //通过await()进行等待，直到被中断或者被唤醒
                condition.await();
                System.out.println("线程[await()-" + Thread.currentThread().getName() + "]被唤醒...");
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("线程[await()-" + Thread.currentThread().getName() + "]被中断...");
            } finally {
                lock.unlock();
                System.out.println("线程[await()-" + Thread.currentThread().getName() + "]释放了锁.");
            }
        });
        //线程1：通过awaitNanos(long)进行等待，直到被中断、被唤醒、或时间超时
        Thread thread1 = new Thread(() -> {
            System.out.println("线程[awaitNanos(long)-" + Thread.currentThread().getName() + "]尝试获取锁...");
            lock.lock();
            System.out.println("线程[awaitNanos(long)-" + Thread.currentThread().getName() + "]获取了锁.");
            try {
                //通过awaitNanos(long)进行等待，直到被中断、被唤醒或时间用尽
                //剩余等待时间
                Long remainTime = condition.awaitNanos(1000000000);//等待1秒钟
                if (remainTime > 0) {//如果剩余时间大于0，则表明此线程是被唤醒的
                    System.out.println("线程[awaitNanos(long)-" + Thread.currentThread().getName() + "]被唤醒...");
                } else {//如果剩余时间小于等于0，则表明此线程是因为等待时间耗尽而停止等待的
                    System.out.println("线程[awaitNanos(long)-" + Thread.currentThread().getName() + "]等待时间用尽，停止等待...");
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("线程[awaitNanos(long)-" + Thread.currentThread().getName() + "]被中断...");
            } finally {
                //尝试解锁
                lock.unlock();
                System.out.println("线程[awaitNanos(long)-" + Thread.currentThread().getName() + "]释放了锁.");
            }
        });
        //线程2：通过await(long,TimeUnit)进行等待，直到被中断、被唤醒、或时间超时
        Thread thread2 = new Thread(() -> {
            System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]尝试获取锁...");
            lock.lock();
            System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]获取了锁.");
            try {
                //通过awaitNanos(long)进行等待，直到被中断、被唤醒或时间用尽
                //返回true则表明是被唤醒的，false表明是时间用尽
                boolean result = condition.await(2, TimeUnit.SECONDS);//等待2秒钟
                if (result) {
                    System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]被唤醒...");
                } else {
                    System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]等待时间用尽，停止等待...");
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]被中断...");
            } finally {
                //尝试解锁
                lock.unlock();
                System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]释放了锁.");
            }
        });
        //线程3：通过awaitUntil(deadline)进行等待，直到被中断、被唤醒、或时间到期
        Thread thread3 = new Thread(() -> {
            System.out.println("线程[awaitUntil(deadline)-" + Thread.currentThread().getName() + "]尝试获取锁...");
            lock.lock();
            System.out.println("线程[awaitUntil(deadline)-" + Thread.currentThread().getName() + "]获取了锁.");
            try {
                Date deadline = new Date(System.currentTimeMillis() + 5000);//5秒之后
                //通过awaitUntil(deadline)进行等待，直到被中断、被唤醒或到达截止时间
                //返回true则表明是被唤醒的，false表明是时间用尽
                boolean result = condition.awaitUntil(deadline);
                if (result) {
                    System.out.println("线程[awaitUntil(deadline)-" + Thread.currentThread().getName() + "]被唤醒...");
                } else {
                    System.out.println("线程[awaitUntil(deadline)-" + Thread.currentThread().getName() + "]到达截止时间，停止等待...");
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("线程[awaitUntil(deadline)-" + Thread.currentThread().getName() + "]被中断...");
            } finally {
                //尝试解锁
                lock.unlock();
                System.out.println("线程[awaitUntil(deadline)-" + Thread.currentThread().getName() + "]释放了锁.");
            }
        });
        //线程4：通过awaitUninterruptibly()进行等待，直到被唤醒
        Thread thread4 = new Thread(() -> {
            System.out.println("线程[awaitUninterruptibly()-" + Thread.currentThread().getName() + "]尝试获取锁...");
            lock.lock();
            System.out.println("线程[awaitUninterruptibly()-" + Thread.currentThread().getName() + "]获取了锁.");
            try {
                //通过awaitUninterruptibly()进行等待，直到被唤醒
                condition.awaitUninterruptibly();
                System.out.println("线程[awaitUninterruptibly()-" + Thread.currentThread().getName() + "]被唤醒...");
            } finally {
                //尝试解锁
                lock.unlock();
                System.out.println("线程[awaitUninterruptibly()-" + Thread.currentThread().getName() + "]释放了锁.");
            }
        });
        //启动所有线程
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        /**
         * 0 等待线程自己结束
         * 1 通过中断结束线程
         * 2 通过condition.signalAll()唤醒所有线程
         */
        int type = 2;
        switch (type) {
            case 0:
                //让能自己结束的自己结束
                Thread.sleep(100);
                System.out.println("======================等待线程自己结束");
                break;
            case 1:
                //尝试中断线程
                Thread.sleep(100);
                System.out.println("======================尝试中断线程");
                thread0.interrupt();
                thread1.interrupt();
                thread2.interrupt();
                thread3.interrupt();
                thread4.interrupt();
                break;
            case 2:
                Thread.sleep(100);
                System.out.println("======================开始唤醒所有还在等待的线程");
                //在main线程中，通过condition.signalAll()唤醒所有
                System.out.println("线程[condition.signalAll()-" + Thread.currentThread().getName() + "]尝试获取锁...");
                lock.lock();
                System.out.println("线程[condition.signalAll()-" + Thread.currentThread().getName() + "]获取了锁,并唤醒所有等待的线程...");
                try {
                    //通过awaitUninterruptibly()进行等待，直到被唤醒
                    condition.signalAll();
                } finally {
                    //尝试解锁
                    lock.unlock();
                    System.out.println("线程[condition.signalAll()-" + Thread.currentThread().getName() + "]释放了锁.");
                }
                break;
            default:
                break;
        }
    }
}
