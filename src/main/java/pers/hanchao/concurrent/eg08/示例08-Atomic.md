# 示例08-Atomic 原子类型

- 包：java.util.concurrent.atomic

1.普通原子类型

- 针对boolean   AtomicBoolean
- 针对int       AtomicInteger
- 针对long      AtomicLong
- 针对对象      AtomicReference

1.原子变量的内部实现:a)volatile value. b)Unsafe类的native方法(原子操作)
2.四种的普通类型:AtomicBoolean(boolean)/AtomicInteger(int)/AtomicLong(long)/AtomicReference(引用)
3.四种类型的默认构造初始值分别是：false/0/0L/null
4.普通原子类型的通用操作：
  set(n)/get():通过volatile value实现赋值和取值，可见性、原子性
  lazySet(n):通过Unsafe类的native方法实现，相较于set()无法保证可见性
  getAndSet(n):赋值，并返回旧值--Unsafe的native方法使[get()+set()]具备原子性
  compareAndSet(e,n):如果是期望的值，则赋值，并返回赋值结果：Unsafe的native方法
5.AtomicInteger和AtomicLong的独有方法:Unsafe类的native方法保障多个操作的原子性
  getAndAdd():增量计算，并返回旧值<-->addAndGet():增量计算，并返回新值
  getAndIncrement(): i++ <--> incrementAndGet: ++i
  getAndDecrement(): i-- <--> decrementAndGet: --i

2.原子类型数组
AtomicLongArray
AtomicIntegerArray
AtomicReferenceArray

1.构造器:AtomicXxxxArray(int[])和AtomicXxxxArray(length)
2.通用方法:get/set/lazySet/compareAndSet/weakCompareAndSet
3.int/long独有:getAndSet^setAndGet/i++^++i/i--^--i


3.字段更新器
AtomicLongFieldUpdater
AtomicIntegerFieldUpdater
AtomicReferenceFieldUpdater

1.字段必须是volatile类型的
2.字段和字段更新器的访问类型必须一致
3.只能是实例变量，不能是类变量(static)
4.不能是final的变量--不可修改
5.如果要处理Int和Long类型,则使用AtomicReferenceFieldUpdater
6.第一个参数 Ojbect obj指的是--我们操作的类的实例对象

4.带版本号的原子引用类型（解决ABA问题）
AtomicStampedReference
AtomicMarkableReference

ABA问题：

1.线程1准备更新变量X的值：expect A--> newValue B
2.其他线程对变量X完成两次更新操作： expect A --> newValue B/ expect B --> A
3.线程1判断此时变量X的值任然为expect A，可以更新
4.其实变量X的值已经发生了变化

//区别：AtomicStampedReference以版本戳标记变量
//区别：AtomicMarkableReference以true和false标记变量

- 构造方法：AtomicStampedReference<>(V initialRef, int initialStamp)
- getStamp和getReference：获取版本戳和引用对象
- set(V newReference, int newStamp):无条件的重设引用和版本戳的值
- attemptStamp(V expectedReference, int newStamp):如果引用为期望值，则重设版本戳
- compareAndSet(V expectedReference,V newReference,int expectedStamp,int newStamp):
    如果引用为期望值且版本戳正确，则赋新值并修改时间戳
- get(int[] stampHolder):通过版本戳获取引用当前值
        
https://www.cnblogs.com/java20130722/p/3206742.html

5.原子累加器(jdk1.8)
DoubleAccumulator
DoubleAdder
LongAccumulator
LongAdder

1.LongAdder应用热点分离思想，划分数组，通过hash算法进行计算，最终求和
2.LongAdder减小了锁力度，提高了吞吐量；应用数组，增加空间消耗
3.LongAdder和LongAccumulator是AtomicLong的扩展
4.DoubleAdder和DoubleAccumulator是AtomicDouble的扩展
5.在低并发环境下性能相似；在高并发环境下---吞吐量增加，但是空间消耗增大
6.多用于收集统计数据，而非细粒度计算
7.方法:increment()/decrement()/add()/sum()/reset()/sumAndReset()
8.高并发测试：低并发数，吞吐量差距不大，高并发数，吞吐量差距拉大。


