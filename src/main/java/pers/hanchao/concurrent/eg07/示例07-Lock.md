# 示例07-Lock

http://www.cnblogs.com/dolphin0520/p/3923167.html

## 锁和同步块之间的主要区别如下：

- 公平锁:
    公平锁即尽量以请求锁的顺序来获取锁。比如同是有多个线程在等待一个锁，当这个锁被释放时，等待时间最久的线程（最先请求的线程）会获得该所，这种就是公平锁。
    非公平锁即无法保证锁的获取是按照请求锁的顺序进行的。这样就可能导致某个或者一些线程永远获取不到锁。
    在Java中，synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。
    而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁。
- 可定时锁：
    可定时锁,顾名思义，就是可以指定时间进行加锁的锁。
    synchronized不是可定时锁，会一直尝试加锁，直至获取锁。
    Lock是可定时锁,可以指定尝试加锁时间，超时则不再尝试获取锁。
- 可中断锁：
    可中断锁：顾名思义，就是可以相应中断的锁。
    在Java中，synchronized就不是可中断锁，而Lock是可中断锁。
    如果某一线程A正在执行锁中的代码，另一线程B正在等待获取该锁，可能由于等待时间过长，线程B不想等待了，想先处理其他事情，我们可以让它中断自己或者在别的线程中中断它，这种就是可中断锁。
    在前面演示lockInterruptibly()的用法时已经体现了Lock的可中断性。
- 读写锁：
    读写锁将对一个资源（比如文件）的访问分成了2个锁，一个读锁和一个写锁。
    正因为有了读写锁，才使得多个线程之间的读操作不会发生冲突。
    ReadWriteLock就是读写锁，它是一个接口，ReentrantReadWriteLock实现了这个接口。
    可以通过readLock()获取读锁，通过writeLock()获取写锁。
- 单一方法：
    同步块必须完全包含在单个方法中，
    而Lock接口的方法lock()和unlock()可以以不同的方式调用。

## Lock接口的主要方法：

1.lock.lock():轮询去获取锁(不可中断):轮询去获取锁,直到获取锁.(不可中断)
2.lock.tryLock():尝试去获取锁(一次),如果获取到，则持有锁返回true;反之返回false.(不可中断)
3.lock.tryLock(long,TimeUnit):尝试去获取锁(一定时间内),如果获取到，则持有锁返回true;如果时间超时还未获取锁，则返回false.(不可中断)
4.lock.lockInterruptibly():可中断的轮询去获取锁:轮询去获取锁,直到获取锁或者被thread.interrupt()中断
5.lock.unlock():释放锁，一般放在finally块中
6.lock.newCondition():获取条件对象，后续代码再进行学习

## ReentrantLock 可重入锁
ReentrantLock是唯一实现了Lock接口的类，并且ReentrantLock提供了更多的方法。
java.util.concurrent.locks.ReadWriteLock接口允许一次读取多个线程，但一次只能写入一个线程。

读锁 - 如果没有线程锁定ReadWriteLock进行写入，则多线程可以访问读锁。
写锁 - 如果没有线程正在读或写，那么一个线程可以访问写锁。

写读、读写、写写互斥，读读不互斥。

### ReentrantLock的方法
1	public Lock readLock()	返回用于读的锁。
2	public Lock writeLock()	返回用于写的锁。

## Condition接口

|序号|方法名称|方法简述|描述|
|---|---|---|---|
|1|public void await()|等待|使当前线程等待，直到被唤醒或被中断。|
|2|public boolean await(long time, TimeUnit unit)|定时等待|使当前线程等待，直到被唤醒、被中断或等待时间超时。|
|3|public long awaitNanos(long nanosTimeout)|纳秒定时等待|使当前线程等待，直到被唤醒、被中断或等待时间(纳秒)超时。|
|4|public long awaitUninterruptibly()|可中断等待|使当前线程等待，直到被唤醒。|
|5|public long awaitUntil(Date deadline)|等待|使当前线程等待，直到被唤醒、被中断或最后期限到达。|
|6|public void signal()|单个唤醒|唤醒一个等待线程。|
|7|public void signalAll()|全部等待|唤醒所有等待线程。|

0.lock.newCondition-获取条件：获取锁上面的条件
1.condition.await()-持续等待：等待，直到被唤醒或被中断
2.condition.awaitNanos(long)-限时等待(纳秒)：等待，直到被唤醒、被中断或超时
3.condition.await(long,TimeUnit)-限时等待(自定义)：等待，直到被唤醒、被中断或超时
4.condition.awaitUntil(deadline)-限时等待(截止时间)：等待，直到被唤醒、被中断或到达截止时间
5.condition.awaitUninterruptibly()-不可中断的持续等待：等待，直到被唤醒
6.condition.signal()-唤醒一个：唤醒在condition上等待的一个线程
7.condition.signalAll()-唤醒全部：唤醒在condition上等待的全部线程

