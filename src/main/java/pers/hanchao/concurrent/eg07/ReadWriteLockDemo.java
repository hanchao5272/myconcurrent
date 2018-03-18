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
    public static void main(String[] args) {
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
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
                        writeLock.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            writeLock.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到写锁，线程结束.");
                        }
                    }
                }).start();
                //写
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
                        writeLock.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            writeLock.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到写锁，线程结束.");
                        }
                    }
                }).start();
                break;
            case 1://写读互斥
                //共用同一个lock对象的写锁、读锁
                Lock writeLock1 = lock.writeLock();
                Lock readLock1 = lock.readLock();
                //写
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
                        writeLock1.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            writeLock1.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到写锁，线程结束.");
                        }
                    }
                }).start();
                //读
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
                        readLock1.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            readLock1.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到读锁，线程结束.");
                        }
                    }
                }).start();
                break;
            case 2://读写互斥
                //共用同一个lock对象的写锁、读锁
                Lock writeLock2 = lock.writeLock();
                Lock readLock2 = lock.readLock();
                //读
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
                        readLock2.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            readLock2.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到读锁，线程结束.");
                        }
                    }
                }).start();
                //写
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
                        writeLock2.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了写锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            writeLock2.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到写锁，线程结束.");
                        }
                    }
                }).start();
                break;
            case 3://读读共享
                //共用同一个lock对象的读锁
                Lock readLock3 = lock.readLock();
                //读
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
                        readLock3.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            readLock3.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到读锁，线程结束.");
                        }
                    }
                }).start();
                //读
                new Thread(() -> {
                    try {
                        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
                        readLock3.lock();
                        System.out.println("线程[" + Thread.currentThread().getName() + "]获取了读锁...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {//在finally代码块中是否锁
                        try {
                            readLock3.unlock();
                            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁..");
                        } catch (IllegalMonitorStateException e) {
                            //e.printStackTrace(); 没有获取锁，尝试去解锁，意料之中的错误，无需打印
                            System.out.println("线程[" + Thread.currentThread().getName() + "]没有获取到读锁，线程结束.");
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
