package pers.hanchao.concurrent.eg05;

/**
 * volatile示例
 * Created by 韩超 on 2018/3/16.
 */
public class VolatileDemo {
    /////////////////////////////////////  1.一次性状态标志使用  /////////////////////////////////////
    /**
     * 是否关闭
     */
//    private static boolean shutdown = false;
    private volatile static boolean shutdown = false;

    /**
     * <p>Title: 咖啡机</p>
     *
     * @author 韩超 2018/3/16 13:58
     */
    static class CoffeeMaker {
        /**
         * 关闭关闭咖啡机
         */
        public static void shutdown() {
            shutdown = true;
            System.out.println("关闭了咖啡机...");
        }

        /**
         * 生成开发
         */
        public static void makeCoffee(String name) {
            System.out.println("咖啡机开始为客户制作咖啡...");
            while (!shutdown) {Thread.currentThread().yield();};
            System.out.println("咖啡机已经停止工作,不再对外提供服务!");
        }
    }

    /////////////////////////////////////  2.双重检查单例模式（jd1.5版本及以后可用）  /////////////////////////////////////

    /**
     * <p>双重检测单例模式--不加volatile关键字</p>
     *
     * @author hanchao 2018/3/17 19:14
     **/
    static class DoubleCheckSingleton {

        private static DoubleCheckSingleton instance = null;

        private DoubleCheckSingleton() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static synchronized DoubleCheckSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckSingleton();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * <p>双重检测单例模式--加volatile关键字</p>
     *
     * @author hanchao 2018/3/17 19:10
     **/
    static class DoubleCheckedVolatileSingleton {
        //注意这里是volatile的
        private volatile static DoubleCheckedVolatileSingleton instance = null;

        public static DoubleCheckedVolatileSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedVolatileSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedVolatileSingleton();
                    }
                }
            }
            return instance;
        }
    }

    static DoubleCheckSingleton singleton = null;

    /**
     * <p>Title: volatile示例</p>
     *
     * @author 韩超 2018/3/16 14:04
     */
    public static void main(String[] args) throws InterruptedException {
        /////////////////////////////////////  1.一次性状态标志使用  /////////////////////////////////////
        //开始制作咖啡
        new Thread(() -> {
            CoffeeMaker.makeCoffee(Thread.currentThread().getName());
        }).start();
        Thread.sleep(100);
        //关掉咖啡机
        new Thread(() -> {
            CoffeeMaker.shutdown();
        }).start();


//        new Thread(() -> {
//            singleton = DoubleCheckSingleton.getInstance();
//        }).start();
//        new Thread(() -> {
//            while (null == singleton){Thread.currentThread().yield();}
//            singleton.toString();
//        }).start();

    }
}
