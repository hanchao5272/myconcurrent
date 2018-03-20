# 示例08-Atomic 原子类型

- 包：java.util.concurrent.atomic

1.普通原子类型

- 针对boolean   AtomicBoolean
- 针对int       AtomicInteger
- 针对long      AtomicLong
- 针对对象      AtomicReference

1. private volatile int value;  内部的值时volatile的，所以能够保证可见性
2. java不能直接访问操作系统底层，而是通过本地方法来访问。Unsafe类提供了硬件级别的原子操作
    通过Unsafe类能够实现CAS操作

Atomic

2.原子类型数组
AtomicLongArray
AtomicIntegerArray
AtomicReferenceArray

3.字段更新器
AtomicLongFieldUpdater
AtomicIntegerFieldUpdater
AtomicReferenceFieldUpdater

4.带版本号的原子引用类型（解决ABA问题）
AtomicStampedReference
AtomicMarkableReference

5.原子累加器(jdk1.8)
DoubleAccumulator
DoubleAdder
LongAccumulator
LongAdder


