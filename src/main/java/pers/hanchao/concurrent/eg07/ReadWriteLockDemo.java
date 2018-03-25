package pers.hanchao.concurrent.eg07;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>Lock接口-读写锁</p>
 *
 * @author hanchao 2018/3/18 15:40
 **/
public class ReadWriteLockDemo {
    //定义非公平的读写锁
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);

    /**
     * <p>lock接口-读写锁</p>
     *
     * @author hanchao 2018/3/18 15:41
     **/
    public static void main(String[] args) throws InterruptedException {
        /**
         * 0 写写互斥
         * 1 写读互斥
         * 2 读写互斥
         * 3 读读共享
         */
        int type = 3;
        switch (type) {
            case 0://写写互斥
                //共用同一个lock对象的写锁
                Lock writeLock = lock.writeLock();
                //写
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁...");
                    writeLock.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁.");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        writeLock.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁.");
                    }
                }).start();
                //写
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁...");
                    writeLock.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁.");
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        writeLock.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁.");
                    }
                }).start();
                break;
            case 1://写读互斥
                //共用同一个lock对象的写锁、读锁
                Lock writeLock1 = lock.writeLock();
                Lock readLock1 = lock.readLock();
                //写
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁...");
                    writeLock1.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁.");
                    try {
                        Thread.sleep(1900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        writeLock1.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁.");
                    }
                }).start();
                //读
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁...");
                    readLock1.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁.");
                    try {
                        Thread.sleep(2100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        readLock1.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁.");
                    }
                }).start();
                break;
            case 2://读写互斥
                //共用同一个lock对象的写锁、读锁
                Lock writeLock2 = lock.writeLock();
                Lock readLock2 = lock.readLock();
                //读
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁...");
                    readLock2.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁.");
                    try {
                        Thread.sleep(2200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        readLock2.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁.");
                    }
                }).start();
                //写
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁...");
                    writeLock2.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁.");
                    try {
                        Thread.sleep(1700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        writeLock2.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁.");
                    }
                }).start();
                break;
            case 3://读读共享
                //共用同一个lock对象的读锁
                Lock readLock3 = lock.readLock();
                //读
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁...");
                    readLock3.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁.");
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        readLock3.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁.");
                    }
                }).start();
                //读
                new Thread(() -> {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁...");
                    readLock3.lock();
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁.");
                    try {
                        Thread.sleep(1600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        readLock3.unlock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁.");
                    }
                }).start();
                break;
            default:
                break;
        }
        Thread.sleep(500);
        System.out.println("============================");
    }
}
