# 示例09-ThreadLocal

ThreadLocal

//初识
1.ThreadLocal只能在自己的线程中设置值
2.线程共享变量在多个线程中共享;
3.线程本地变量每个线程独有一份副本，互补影响;
4.main也是一个线程。

//基本用法
1.set():每个线程中单独设置
2.get()取值，前提是已经set()了，或者通过重写了initialValue()方法设置了初始值
3.remover():置为null
4.重写protected initialValue()方法用来设置初始值

//常用示例：1.获取数据库连接；2；获取Session

ThreadLocalRandom

