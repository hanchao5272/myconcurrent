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

1.Random（jdk1.0） 线程安全的伪随机生成器
2.ThreadLocalRandom（jdk1.7）  线程安全的伪随机生成器
3.ThreadLocalRandom增加Random类，提供高并发性能(并没有测试出来性能上的提供)
4.所谓伪随机，是指如果使用同一个seed，则产生的随机数序列一致。
5.三种Random
5.1.Random
    初始化：random = new Random();
    使用：random.next();
5.2.ThreadLocalRandom
    初始化：random = ThreadLocalRandom.current();
    调用：random.next();
5.3.ThreadLocal<Random>
    初始化1：ThreadLocal<Random> random = new ThreadLocal<Random>()(@Override initialValue());
    初始化2：random.set(new Random());
    使用：random.get().next();


