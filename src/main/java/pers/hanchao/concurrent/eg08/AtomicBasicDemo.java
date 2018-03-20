package pers.hanchao.concurrent.eg08;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>原子类型01-普通原子类型</p>
 * @author hanchao 2018/3/20 18:24
 **/
public class AtomicBasicDemo {
    private static final Logger LOGGER = Logger.getLogger(AtomicBasicDemo.class);

    /**
     * <p>普通原子类型</p>
     * @author hanchao 2018/3/20 18:26
     **/
    public static void main(String[] args) {
        //构造器
        LOGGER.info("===========原子类型的默认构造器：");
        //默认构造器
        LOGGER.info("AtomicBoolean()：声明一个原子boolean类型，初始值设置为----" + new AtomicBoolean().toString());
        LOGGER.info("AtomicInteger()：声明一个原子int类型，初始值设置为----" + new AtomicInteger());
        LOGGER.info("AtomicLong()：声明一个原子long类型，初始值设置为----" + new AtomicLong().toString());
        LOGGER.info("AtomicReference()：声明一个原子引用类型，初始值使用默认值----" + new AtomicReference<>().toString() + "\n");
        //赋值构造器
        AtomicBoolean aBoolean = new AtomicBoolean(true);
        AtomicInteger aInt = new AtomicInteger(1);
        AtomicLong aLong = new AtomicLong(1000L);
        AtomicReference<String> aStr = new AtomicReference<>("Hello World!");

        //通用操作
        LOGGER.info("===========原子类型的通用操作：get()/get()/getAndSet()/compareAndSet()/weakCompareAndSet()");
        //获取当前的值：volatile保证可见性-基本类型取值本身是原子性的
        LOGGER.info("get()：获取当前的值(可见性)----now = " + aBoolean.get());
        LOGGER.info("get()：获取当前的值(可见性)----now = " + aInt.get());
        LOGGER.info("get()：获取当前的值(可见性)----now = " + aLong.get());
        LOGGER.info("get()：获取当前的值(可见性)----now = " + aStr.get());
        LOGGER.info("---------------------------------------------------------");
        //普通赋值：volatile保证可见性-基本类型赋值本身是原子性的
        aBoolean.set(false);
        aInt.set(10);
        aLong.set(1314);
        aStr.set("Good Day!");
        LOGGER.info("set()：获取当前的值(可见性)----now = " + aBoolean.toString());
        LOGGER.info("set()：获取当前的值(可见性)----now = " + aInt.toString());
        LOGGER.info("set()：获取当前的值(可见性)----now = " + aLong.toString());
        LOGGER.info("set()：获取当前的值(可见性)----now = " + aStr.toString());
        LOGGER.info("---------------------------------------------------------");
        //延迟赋值：通过Unsafe保证原子性，并不保证可见性，效率要高于set()
        aBoolean.set(true);
        aInt.set(21);
        aLong.set(521);
        aStr.set("Bad Day!");
        LOGGER.info("lazySet(newValue)：赋值(无可见性)----now = " + aBoolean.toString());
        LOGGER.info("lazySet(newValue)：赋值(无可见性)----now = " + aInt.toString());
        LOGGER.info("lazySet(newValue)：赋值(无可见性)----now = " + aLong.toString());
        LOGGER.info("lazySet(newValue)：赋值(无可见性)----now = " + aStr.toString());
        LOGGER.info("---------------------------------------------------------");
        //赋新值，并返回旧值：通过Unsafe的native方法保证[get()+set ()]操作的原子性
        LOGGER.info("getAndSet(newValue)：赋新值，并返回旧值----old = " + aBoolean.getAndSet(false) + " ,newValue = false ,now = " + aBoolean.toString());
        LOGGER.info("getAndSet(newValue)：赋新值，并返回旧值----old = " + aInt.getAndSet(5) + " ,newValue = 5 ,now = " + aInt.toString());
        LOGGER.info("getAndSet(newValue)：赋新值，并返回旧值----old = " + aLong.getAndSet(200L) + " ,newValue = 200L ,now = " + aLong.toString());
        LOGGER.info("getAndSet(newValue)：赋新值，并返回旧值----old = " + aStr.getAndSet("Ni Hao") + " ,newValue = Ni Hao! ,now = " + aStr.toString());
        LOGGER.info("---------------------------------------------------------");
        //比较并赋值,返回是否成功：通过Unsafe保证原子性
        LOGGER.info("compareAndSet(expect,update)：如果当前是期望的值则赋值----result = " + aBoolean.compareAndSet(false,true));
        LOGGER.info("compareAndSet(expect,update)：如果当前是期望的值则赋值----result = " + aInt.compareAndSet(6,7));
        LOGGER.info("compareAndSet(expect,update)：如果当前是期望的值则赋值----result = " + aLong.compareAndSet(200L,300L));
        LOGGER.info("compareAndSet(expect,update)：如果当前是期望的值则赋值----result = " + aStr.compareAndSet("Ni Hao!","Good Luck!"));
        LOGGER.info("---------------------------------------------------------");
        LOGGER.info("weakCompareAndSet(expect,update)的实现的效果与compareAndSet(expect,update)一致，但可能失败[其实不会失败，因为其源代码与后者一致。]");
        LOGGER.info("---------------------------------------------------------\n");

        //AtomicInteger和AtomicLong的独有操作
        LOGGER.info("===========AtomicInteger和AtomicLong的独有操作：getAndAdd()/addAndGet()/i++/++i/i--/--i");

        //增量计算，并返回旧值：通过Unsafe的native方法保证[get()+add()]操作的原子性
        LOGGER.info("getAndAdd(delta)：增量计算，并返回旧----old = " + aInt.getAndAdd(5) + " ,delta = 5 ,now = " + aInt.toString());
        LOGGER.info("getAndAdd(delta)：增量计算，并返回旧----old = " + aLong.getAndAdd(5) + " ,delta = 5 ,now = " + aLong.toString());
        LOGGER.info("---------------------------------------------------------");
        //增量计算，并返回新值：通过Unsafe的native方法保证[add()+get()]操作的原子性
        LOGGER.info("addAndGet(delta)：增量计算，并返回新值----new = " + aInt.addAndGet(5) + " ,delta = 5 ,now = " + aInt.toString());
        LOGGER.info("addAndGet(delta)：增量计算，并返回新值----new = " + aLong.addAndGet(5) + " ,delta = 5 ,now = " + aLong.toString());
        LOGGER.info("---------------------------------------------------------");
        //自增，并返回旧值：通过Unsafe的native方法保证[i++]操作的原子性
        LOGGER.info("getAndIncrement()：自增，并返回旧值[i++]----old = " + aInt.getAndIncrement() + ",now = " + aInt.toString());
        LOGGER.info("getAndIncrement()：自增，并返回旧值[i++]----old = " + aLong.getAndIncrement() + ",now = " + aLong.toString());
        LOGGER.info("---------------------------------------------------------");
        //自增，并返回新值：通过Unsafe的native方法保证[++i]操作的原子性
        LOGGER.info("incrementAndGet()：自增，并返回旧值[++i]----now = " + aInt.incrementAndGet() + ",now = " + aInt.toString());
        LOGGER.info("incrementAndGet()：自增，并返回旧值[++i]----now = " + aLong.incrementAndGet() + ",now = " + aLong.toString());
        LOGGER.info("---------------------------------------------------------");
        //自减，并返回旧值：通过Unsafe的native方法保证[i--]操作的原子性
        LOGGER.info("getAndDecrement()：自增，并返回旧值[i--]----old = " + aInt.getAndDecrement() + ",now = " + aInt.toString());
        LOGGER.info("getAndDecrement()：自增，并返回旧值[i--]----old = " + aLong.getAndDecrement() + ",now = " + aLong.toString());
        LOGGER.info("---------------------------------------------------------");
        //自减，并返回新值：通过Unsafe的native方法保证[--i]操作的原子性
        LOGGER.info("decrementAndGet()：自增，并返回旧值[--i]----now = " + aInt.decrementAndGet() + ",now = " + aInt.toString());
        LOGGER.info("decrementAndGet()：自增，并返回旧值[--i]----now = " + aLong.decrementAndGet() + ",now = " + aLong.toString());
        LOGGER.info("---------------------------------------------------------");
    }
}
