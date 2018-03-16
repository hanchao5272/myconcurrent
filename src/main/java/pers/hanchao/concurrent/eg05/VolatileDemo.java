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
        }

        /**
         * 生成开发
         */
        public static void makeCoffee(String name) {
            System.out.println("咖啡机[" + name + "]开始为客户制作咖啡...");
            while (!shutdown) ;
            System.out.println("咖啡机[" + name + "]已经停止工作.");
        }
    }

    /**
     * <p>Title: volatile示例</p>
     *
     * @author 韩超 2018/3/16 14:04
     */
    public static void main(String[] args) throws InterruptedException {
        /////////////////////////////////////  1.一次性状态标志使用  /////////////////////////////////////
        //开始制作咖啡
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                CoffeeMaker.makeCoffee(Thread.currentThread().getName());
            }).start();
        }
        Thread.sleep(100);
        //关掉咖啡机
        new Thread(() -> {
            CoffeeMaker.shutdown();
        }).start();
    }
}
