package pers.hanchao.concurrent.eg08;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * <p>原子类型03:字段更新器</p>
 *
 * @author hanchao 2018/3/20 22:28
 **/
public class AtomicFieldUpdater {
    private static final Logger LOGGER = Logger.getLogger(AtomicFieldUpdater.class);

    /**
     * <p>自定义数组，用于字段更新器演示</p>
     *
     * @author hanchao 2018/3/20 22:35
     **/
    static class MyVolatileType {
        //键
        //必须是volatile；必须是long，不能是Long
        //用AtomicLongFieldUpdater处理
        //如果是Long的，则用AtomicReferenceFieldUpdater
        private volatile int index;
        //newUpdater(Class对象,字段名)
        //字段更新器需要与字段的访问类型一致
        private static final AtomicIntegerFieldUpdater integerFieldUpdater =
                AtomicIntegerFieldUpdater.newUpdater(MyVolatileType.class, "index");
        //值
        //必须是volatile
        //需用AtomicReferenceFieldUpdater处理
        private volatile String value;
        //newUpdater(Class对象,字段名)
        //字段更新器需要与字段的访问类型一致
        private static final AtomicLongFieldUpdater longFieldUpdater = AtomicLongFieldUpdater.newUpdater(MyVolatileType.class, "time");

        //创建时间
        //必须是volatile；必须是int，不能是Integer
        //用AtomicIntegerFieldUpdater处理
        //如果是Integer的，则用AtomicReferenceFieldUpdater
        private volatile long time;
        //newUpdater(Class对象,字段Class对象,字段名)
        //字段更新器需要与字段的访问类型一致
        private static final AtomicReferenceFieldUpdater referenceFieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(MyVolatileType.class, String.class, "value");

        /**
         * <p>字段更新器-通用方法演示-以AtomicReferenceFieldUpdater为例</p>
         *
         * @author hanchao 2018/3/20 22:50
         **/
        public void fieldUpdaterCommonMethodDemo() {
            LOGGER.info("=======字段更新器-通用方法演示-以AtomicReferenceFieldUpdater为例");
            //get(obj)
            LOGGER.info("get(obj):获取值----初始值：" + referenceFieldUpdater.get(this));
            //set(obj,newValue)
            referenceFieldUpdater.set(this, "New Day!");
            LOGGER.info("set(obj,newValue):设置值---" + referenceFieldUpdater.get(this));
            //lazySet(obj,newValue)
            referenceFieldUpdater.lazySet(this, "Lazy Day!");
            LOGGER.info("lazySet(obj,newValue):设置值(无可见性)---" + referenceFieldUpdater.get(this));
            //getAndSet(obj,newValue)
            LOGGER.info("getAndSet(obj,newValue):赋值，并返回旧值：" + referenceFieldUpdater.getAndSet(this, "Good Day!"));
            //compareAndSet(obj,expect,newValue)
            LOGGER.info("compareAndSet(obj,expect,newValue):如果是期望的值,则赋值,并返回赋值结果："
                    + referenceFieldUpdater.compareAndSet(this, "Good Day!", "Good good Day!")
                    + ",---" + referenceFieldUpdater.get(this) + "\n");
        }

        /**
         * <p>字段更新器-int/long特殊方法演示</p>
         *
         * @author hanchao 2018/3/20 22:59
         **/
        public void fieldUpdaterSpecialMethodDemo() {
            LOGGER.info("=======字段更新器-int/long特殊方法演示");
            //getAndAdd(obj,delta)和addAndGet(obj,delta)
            LOGGER.info("index=" + integerFieldUpdater.getAndIncrement(this) + ",time=" + longFieldUpdater.get(this));
            LOGGER.info("getAndAdd(obj,delta):增量计算，并返回旧值index：" + integerFieldUpdater.getAndAdd(this, 2)
                    + ",new = " + integerFieldUpdater.get(this));
            LOGGER.info("addAndGet(obj,delta):增量计算，并返回新值time：" + longFieldUpdater.addAndGet(this, System.currentTimeMillis())
                    + ",new = " + longFieldUpdater.get(this));
            //getAndIncrement(obj)和incrementAndGet(obj)
            LOGGER.info("getAndIncrement(obj):自增，并返回旧值index：" + integerFieldUpdater.getAndIncrement(this)
                    + ",new = " + integerFieldUpdater.get(this));
            LOGGER.info("incrementAndGet(obj):自增，并返回新值time：" + longFieldUpdater.incrementAndGet(this)
                    + ",new = " + longFieldUpdater.get(this));
            //getAndDecrement(obj)和decrementAndGet(ojb)
            LOGGER.info("getAndDecrement(obj):自减，并返回旧值index：" + integerFieldUpdater.getAndDecrement(this)
                    + ",new = " + integerFieldUpdater.get(this));
            LOGGER.info("decrementAndGet(obj):自减，并返回新值time：" + longFieldUpdater.decrementAndGet(this)
                    + ",new = " + longFieldUpdater.get(this) + "\n");
        }

        @Override
        public String toString() {
            return "MyVolatileType{" +
                    "index=" + index +
                    ", value='" + value + '\'' +
                    ", time=" + time +
                    '}';
        }

        public MyVolatileType(int index, String value, long time) {
            this.index = index;
            this.value = value;
            this.time = time;
        }
    }

    /**
     * <p>原子类型03:字段更新器</p>
     *
     * @author hanchao 2018/3/20 22:29
     **/
    public static void main(String[] args) {
        MyVolatileType myArray = new MyVolatileType(1, "David", System.currentTimeMillis());
        LOGGER.info("原始值：" + myArray.toString() + "\n");
        //字段更新器的通用方法
        myArray.fieldUpdaterCommonMethodDemo();
        LOGGER.info("当前值：" + myArray.toString() + "\n");
        //int/long字段更新器的独有方法
        myArray.fieldUpdaterSpecialMethodDemo();
        LOGGER.info("当前值：" + myArray.toString() + "\n");
    }
}
