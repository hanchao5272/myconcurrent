最近在系统的学习Java并发（concurrent），遂将学习所得整理成博文，作为今后参考的依据。

## 内容简述

- 基本概念与发展历史
- Thread的线程方法与状态转换
- JMM、指令重排、happens-before原则、原子性、可见性与有序性
- 易变类型关键字volatile
- 同步关键字synchronized
- 显式锁Lock
- 原子变量Atomic
- 线程本地变量ThreadLocal
- 倒计时门闩CountDownLatch
- 循环屏障CyclicBarrier
- 信号量Semaphore
- Callable接口+Future接口
- Executor(执行器)并发框架
- 并发集合
- ForkJoin(分解合并)并发框架
- Actor(角色)并发框架

## 章节目录：

- [Java并发学习系列-绪论](http://blog.csdn.net/hanchao5272/article/details/79437370)
- 基本概念与发展历史
	- [Java并发01:进程、线程、并发、并行、多线程、线程安全、死锁、并发优缺点](http://blog.csdn.net/hanchao5272/article/details/79513153)
	- [Java并发02:Java并发Concurrent技术发展简史(各版本JDK中的并发技术)](http://blog.csdn.net/hanchao5272/article/details/79521731)
- Thread的线程方法与状态转换
	- [Java并发03:多线程实现三方式:继承Thread类、实现Runnable接口、实现Callable接口](http://blog.csdn.net/hanchao5272/article/details/79524351)
	- [Java并发04:Thread的基本方法(1)-Name、ThreadGroup、activeCount、isAlive、守护线程等](http://blog.csdn.net/hanchao5272/article/details/79525182)
	- [Java并发05:Thread的基本方法(2)-join方法-线程插队](http://blog.csdn.net/hanchao5272/article/details/79525372)
	- [Java并发06:Thread的基本方法(3)-yield方法的分析与实例说明](http://blog.csdn.net/hanchao5272/article/details/79526150)
	- [Java并发07:Thread的基本方法(4)-Thread.sleep()、Object.wait()、notify()和notifyAll()](http://blog.csdn.net/hanchao5272/article/details/79530141)
	- [Java并发08:Thread的基本方法(5)-interrupt()、isInterrupted()](http://blog.csdn.net/hanchao5272/article/details/79528463)
	- [Java并发09:Thread的基本方法(6)-线程优先级priority相关说明与操作](http://blog.csdn.net/hanchao5272/article/details/79530141)
	- [Java并发10:线程的状态Thread.State及其线程状态之间的转换](http://blog.csdn.net/hanchao5272/article/details/79533700)
- JMM、指令重排、happens-before原则、原子性、可见性与有序性
	- [Java并发11:Java内存模型、指令重排、happens-before原则](http://blog.csdn.net/hanchao5272/article/details/79575491)
	- [Java并发12:并发三特性-原子性、可见性和有序性概述及问题示例](http://blog.csdn.net/hanchao5272/article/details/79597319)
	- [Java并发13:并发三特性-原子性定义、原子性问题与原子性保证技术](http://blog.csdn.net/hanchao5272/article/details/79598495)
	- [Java并发14:并发三特性-可见性定义、可见性问题与可见性保证技术](http://blog.csdn.net/hanchao5272/article/details/79598669)
	- [Java并发15:并发三特性-有序性定义、有序性问题与有序性保证技术](http://blog.csdn.net/hanchao5272/article/details/79598874)
- 易变类型关键字volatile
	- [Java并发16:volatile关键字的两种用法-一次性状态标志、双重检查单例模式](http://blog.csdn.net/hanchao5272/article/details/79604845)
- 同步关键字synchronized
	- [Java并发17:synchronized关键字的两种用法-同步代码块(4)和同步方法(2)](http://blog.csdn.net/hanchao5272/article/details/79606329)
- 显式锁Lock
	- [Java并发18:Lock系列-Lock接口与synchronized关键字的比较](http://blog.csdn.net/hanchao5272/article/details/79679919)
	- [Java并发19:Lock系列-Lock接口基本方法学习实例](http://blog.csdn.net/hanchao5272/article/details/79680547)
	- [Java并发20:Lock系列-Condition接口基本方法学习实例](http://blog.csdn.net/hanchao5272/article/details/79681037)
	- [Java并发21:Lock系列-ReadWriteLock接口和ReentrantReadWriteLock类基本方法学习实例](http://blog.csdn.net/hanchao5272/article/details/79683202)
- 原子变量Atomic
	- [Java并发22:Atomic系列-原子类型整体概述与类别划分](http://blog.csdn.net/hanchao5272/article/details/79686147)
	- [Java并发23:Atomic系列-普通原子类型AtomicXxxx学习笔记](http://blog.csdn.net/hanchao5272/article/details/79686177)
	- [Java并发24:Atomic系列-原子类型数组AtomicXxxxArray学习笔记](http://blog.csdn.net/hanchao5272/article/details/79687179)
	- [Java并发25:Atomic系列-原子类型字段更新器AtomicXxxxFieldUpdater学习笔记](http://blog.csdn.net/hanchao5272/article/details/79688696)
	- [Java并发26:Atomic系列-带版本戳的原子引用类型AtomicStampedReference与AtomicMarkableReference](http://blog.csdn.net/hanchao5272/article/details/79689355)
	- [Java并发27:Atomic系列-原子类型累加器XxxxAdder和XxxxAccumulator的学习笔记](http://blog.csdn.net/hanchao5272/article/details/79689366)
- 线程本地变量ThreadLocal
	- [Java并发28:ThreadLocal学习笔记-简介、基本方法及应用场景](http://blog.csdn.net/hanchao5272/article/details/79691497)
	- [Java并发29:ThreadLocalRandom学习笔记-随机数的三种使用方法](http://blog.csdn.net/hanchao5272/article/details/79691746)
- 倒计时门闩CountDownLatch
	- [Java并发30:CountDownLatch(上)--基本方法学习](http://blog.csdn.net/hanchao5272/article/details/79774055)
	- [Java并发31:CountDownLatch(下)--两种应用场景](http://blog.csdn.net/hanchao5272/article/details/79774397)
- 循环屏障CyclicBarrier
	- [Java并发32:CyclicBarrier的基本方法和应用场景实例](http://blog.csdn.net/hanchao5272/article/details/79779639)
- 信号量Semaphore
	- [Java并发33:Semaphore基本方法与应用场景实例](http://blog.csdn.net/hanchao5272/article/details/79780045)
- Callable接口+Future接口
	- [Java并发34:Callable+Future系列--Callable接口学习笔记](http://blog.csdn.net/hanchao5272/article/details/79826627)
	- [Java并发35:Callable+Future系列--Future接口学习笔记](http://blog.csdn.net/hanchao5272/article/details/79826913)
	- [Java并发36:Callable+Future系列--FutureTask学习笔记](http://blog.csdn.net/hanchao5272/article/details/79828674)
- 执行器Executor并发框架
	- [Java并发37:Executor系列--Executor接口学习笔记](http://blog.csdn.net/hanchao5272/article/details/79829407)
	- [Java并发38:Executor系列--ExecutorService接口学习笔记](http://blog.csdn.net/hanchao5272/article/details/79830245)
	- [Java并发39:Executor系列--ScheduleExecutorService接口学习笔记](http://blog.csdn.net/hanchao5272/article/details/79834744)
	- [Java并发40:Executor系列--ThreadPoolExecutor和ScheduledThreadPoolExecutor学习笔记](http://blog.csdn.net/hanchao5272/article/details/79835004)
	- [Java并发41:Executor系列--Executors(上)-Runnable转Callable、ThreadFactory和不可配置的线程池](http://blog.csdn.net/hanchao5272/article/details/79839668)
	- [Java并发42:Executor系列--Executors(下)-几类预定义的线程池ExecutorService和可调度线程池ScheduledExecutorService](http://blog.csdn.net/hanchao5272/article/details/79840138)
- 并发集合
	- [Java并发43:并发集合系列-序章](http://blog.csdn.net/hanchao5272/article/details/79846005)
	- [Java并发44:并发集合系列-基于写时复制的CopyOnWriteArrayList和CopyOnWriteArraySet](http://blog.csdn.net/hanchao5272/article/details/79846293)
	- [Java并发45:并发集合系列-基于跳表的ConcurrentSkipListSet和ConcurrentSkipListMap](http://blog.csdn.net/hanchao5272/article/details/79859087)
	- [Java并发46:并发集合系列-基于锁分段技术的ConcurrentHashMap](http://blog.csdn.net/hanchao5272/article/details/79859688)
	- [Java并发47:并发集合系列-基于CAS算法的非阻塞单向无界队列ConcurrentLinkedQueue](http://blog.csdn.net/hanchao5272/article/details/79947143)
	- [Java并发48:并发集合系列-基于CAS算法的非阻塞双向无界队列ConcurrentLinkedDueue](http://blog.csdn.net/hanchao5272/article/details/79947785)
	- [Java并发49:并发集合系列-基于独占锁+链表实现的单向阻塞无界队列LinkedBlockingQueue](http://blog.csdn.net/hanchao5272/article/details/79948018)
	- [Java并发50:并发集合系列-基于独占锁实现的双向阻塞队列LinkedBlockingDeque](http://blog.csdn.net/hanchao5272/article/details/79948823)
	- [Java并发51:并发集合系列-基于独占锁+数组实现的单向阻塞有界队列ArrayBlockingQueue](http://blog.csdn.net/hanchao5272/article/details/79949020)
	- [Java并发52:并发集合系列-基于独占锁+二叉树最小堆实现的单向阻塞无界优先级队列PriorityBlockingQueue](http://blog.csdn.net/hanchao5272/article/details/79949170)
	- [Java并发53:并发集合系列-基于独占锁+PriorityBlockingQueue实现的单向阻塞无界延时队列DelayQueue](http://blog.csdn.net/hanchao5272/article/details/79949737)
	- [Java并发54:并发集合系列-基于CAS算法的非阻塞无数据缓冲队列SynchronousQueue](http://blog.csdn.net/hanchao5272/article/details/79950112)
	- [Java并发55:并发集合系列-基于预占模式+链表的单向阻塞无界队列LinkedTransferQueue](http://blog.csdn.net/hanchao5272/article/details/79950628)
- ForkJoin并发框架
	- [Java并发56:ForkJoin并发框架的原理、2种ForkJoinTask的用法以及ForkJoinPool的常用方法](https://blog.csdn.net/hanchao5272/article/details/79982095)
- Actor并发框架
	- [Java并发57:Akka Actors并发框架浅谈及入门示例](https://blog.csdn.net/hanchao5272/article/details/79998467)


------------------
便签

1. [JDK8 Documents](https://docs.oracle.com/javase/8/index.html)
