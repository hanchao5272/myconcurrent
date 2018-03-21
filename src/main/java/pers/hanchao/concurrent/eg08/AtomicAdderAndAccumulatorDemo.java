package pers.hanchao.concurrent.eg08;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>Atomic05：累加器</p>
 *
 * @author hanchao 2018/3/21 21:47
 **/
public class AtomicAdderAndAccumulatorDemo {
    /**
     * <p>原子类型：累加器</p>
     *
     * @author hanchao 2018/3/21 21:47
     **/
    public static void main(String[] args) throws InterruptedException {
         /*
         LongAdder所使用的思想就是热点分离，这一点可以类比一下ConcurrentHashMap的设计思想。
         就是将value值分离成一个数组，当多线程访问时，通过hash算法映射到其中的一个数组进行计数。
         而最终的结果，就是这些数组的求和累加。这样一来，就减小了锁的粒度.

         1.LongAdder和LongAccumulator是AtomicLong的扩展
         2.DoubleAdder和DoubleAccumulator是AtomicDouble的扩展
         3.在低并发环境下性能相似；在高并发环境下---吞吐量增加，但是空间消耗增大
         4.多用于收集统计数据，而非细粒度计算
          */

        //构造器
        LongAdder adder = new LongAdder();
        System.out.println("默认构造器：" + adder);

        //自增
        adder.increment();
        System.out.println("increment():自增----" + adder);

        //自减
        adder.decrement();
        System.out.println("decrement():自减----" + adder);

        //增量计算
        System.out.println("------------add(long):增量计算：");
        int sum = 0;
        long add;
        for (int i = 0; i < 5; i++) {
            add = RandomUtils.nextLong(100, 300);
            sum += add;
            adder.add(add);
            System.out.println("增加---" + add + "-->" + sum);
        }

        //最终的值
        System.out.println("sum():最终值---" + adder.sum());

        //重置sum值
        adder.reset();
        System.out.println("reset():重置值---" + adder);

        //获得最终的值并重置
        System.out.println("------------再次增量计算：");
        sum = 0;
        for (int i = 0; i < 5; i++) {
            add = RandomUtils.nextLong(100, 300);
            sum += add;
            adder.add(add);
            System.out.println("增加---" + add + "-->" + sum);
        }
        System.out.println("sumThenReset():获取最终值并重置---" + adder.sumThenReset());
        System.out.println("重置值---" + adder);

        //多种形式返回值
        System.out.println("------------多种数据类型返回值：");
        adder.add(RandomUtils.nextLong(100, 200));
        System.out.println("int类型：" + adder.intValue());
        System.out.println("double类型：" + adder.doubleValue());
        System.out.println("float类型：" + adder.floatValue());


        ///////////////////////////////  并发性能测试  /////////////////////
        AtomicLong atomicLong = new AtomicLong();
        LongAdder longAdder = new LongAdder();
        //线程总数
        int num = 100000;
        //每个线程自增次数
        int perNum = 10000;

        //num * perNum      AtomicLong  LongAdder
        //100 * 100         60          60          ~1:1
        //1000 * 1000       182         200         ~1:1
        //10000 * 1000      1830        1700        ~1.1:1
        //10000 * 10000     3161        2410        ~1.3:1
        //100000 * 10000    23333       11981       ~2:1
        //100000 * 100000   201850      38843       ~5.2:1

        //测试AtomicLong
        final long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                for (int j = 0; j < perNum; j++) {
                    atomicLong.incrementAndGet();
//                    longAdder.increment();
                }
                System.out.println(atomicLong + "----" + (System.currentTimeMillis() - start));
//                System.out.println(longAdder + "----" + (System.currentTimeMillis() - start));
            }).start();
        }
    }
}
