package pers.hanchao.concurrent.eg08;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * <p>原子类型02：数组</p>
 * @author hanchao 2018/3/20 21:55
 **/
public class AtomicArrayDemo {
    private static final Logger LOGGER = Logger.getLogger(AtomicArrayDemo.class);

    /**
     * <p>原子类型02：数组</p>
     * @author hanchao 2018/3/20 21:55
     **/
    public static void main(String[] args) {
        //构造器
        LOGGER.info("===========原子数组构造器");
        //数组构造器
        AtomicIntegerArray aIntArray = new AtomicIntegerArray(new int[]{1,2,3,4,5});
        AtomicReferenceArray aStrArray = new AtomicReferenceArray<>(new String []{"David","Jone","Gray"});
        LOGGER.info("AtomicIntegerArray(int[]):" + aIntArray.toString());
        LOGGER.info("AtomicReferenceArray(String[]):" + aStrArray.toString());
        //长度构造器
        LOGGER.info("AtomicIntegerArray(length):" + new AtomicIntegerArray(10).toString());
        LOGGER.info("AtomicIntegerReference(length):" + new AtomicReferenceArray<String >(10).toString() + "\n");

        //通用方法
        LOGGER.info("===========原子数组通用方法：get/set/lazySet/getAndSet/CompareAndSet");
        //get(index)
        LOGGER.info("get(index):获取第i个元素----" + aStrArray.get(2).toString());
        //set(index,newValue)
        aStrArray.set(0,"Dock");
        LOGGER.info("set(index,newValue):设置第i个元素的值----" + aStrArray.get(0).toString());
        //lazySet(index,newValue)
        aStrArray.lazySet(0,"Green");
        LOGGER.info("lazySet(index,newValue):设置第i个元素的值(无可见性)----" + aStrArray.get(0).toString());
        //getAndSet(index,newValue)
        LOGGER.info("getAndSet(index,newValue):设置第i个元素的值，并返回此元素的旧值----" + aStrArray.getAndSet(0,"Merlin"));
        //compareAndSet(index,expect,newValue)
        LOGGER.info("compareAndSet(index,expect,newValue):如果第i个元素的值是期望的值，则设置新值，并返回执行结果----" + aStrArray.compareAndSet(0,"Merlin","Love"));
        //length
        LOGGER.info("length():数组长度----" + aStrArray.length());
        LOGGER.info("weakCompareAndSet(index,expect,newValue)的实现的效果与compareAndSet(index,expect,newValue)一致，但可能失败[其实不会失败，因为其源代码与后者一致。]\n");

        //AtomicIntegerArray和AtomicLongArray的独有方法：getAndAdd/addAndGet/i++/i--/++i/--i
        LOGGER.info("===========AtomicIntegerArray和AtomicLongArray的独有方法：getAndAdd/addAndGet/i++/i--/++i/--i");
        //getAndAdd(index,newValue)和addAndGet(index,newValue)
        aIntArray.set(2,0);
        LOGGER.info("i = 2,value = " + aIntArray.get(2));
        LOGGER.info("getAndAdd(index,newValue):增量计算，返回旧值----" + aIntArray.getAndAdd(2,2));
        aIntArray.set(2,0);
        LOGGER.info("addAndGet(index,newValue):增量计算，返回新值----" + aIntArray.addAndGet(2,2));
        //getAndIncrement(index)和incrementAndGet(index)
        aIntArray.set(2,0);
        LOGGER.info("i = 2,value = " + aIntArray.get(2));
        LOGGER.info("getAndIncrement(index):自增计算，返回旧值----" + aIntArray.getAndIncrement(2));
        aIntArray.set(2,0);
        LOGGER.info("incrementAndGet(index):自增计算，返回新值----" + aIntArray.incrementAndGet(2));
        //getAndDecrement(index)decrementAndGet(index)
        aIntArray.set(2,0);
        LOGGER.info("i = 2,value = " + aIntArray.get(2));
        LOGGER.info("getAndDecrement(index):自减计算，返回旧值----" + aIntArray.getAndDecrement(2));
        aIntArray.set(2,0);
        LOGGER.info("decrementAndGet(index):自减计算，返回新值----" + aIntArray.decrementAndGet(2));
    }
}
