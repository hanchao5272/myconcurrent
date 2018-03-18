package pers.hanchao.concurrent.eg06;

/**
 * <p>synchronized学习示例</p>
 *
 * @author hanchao 2018/3/18 11:25
 **/
public class SynchronizedDemo {
    public static int number = 0;

    /**
     * <p>自增器-无同步</p>
     *
     * @author hanchao 2018/3/18 11:39
     **/
    static class Increment {
        public void autoIncrement() {
            for (int i = 0; i < 5; i++) {
                number++;
                System.out.println("线程[" + Thread.currentThread().getName() + "],number:" + number);
            }
        }
    }
    /**
     * <p>自增器-同步代码块-锁对象是类的本地变量</p>
     *
     * @author hanchao 2018/3/18 11:26
     **/
    static class SyncCodeBlockIncrement11 {
        // 同步代码10/11-加锁对象是类的本地变量
        private byte[] ordinaryObj = new byte[0];

        public void autoIncrement() {
            synchronized (ordinaryObj) {
                for (int i = 0; i < 5; i++) {
                    number++;
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁,number:" + number);
                }
            }
        }
    }

    /**
     * <p>自增器-同步代码块-锁对象是类的静态变量</p>
     *
     * @author hanchao 2018/3/18 11:26
     **/
    static class SyncCodeBlockIncrement12 {
        //同步代码12-加锁对象是类的静态变量
        private static byte[] staticObj = new byte[0];

        public void autoIncrement() {
            synchronized (staticObj) {
                for (int i = 0; i < 5; i++) {
                    number++;
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁,number:" + number);
                }
            }
        }
    }

    //同步代码13-加锁对象是共享变量
    private static byte[] heapObj = new byte[0];
    /**
     * <p>自增器-同步代码块-锁对象是共享变量</p>
     *
     * @author hanchao 2018/3/18 11:26
     **/
    static class SyncCodeBlockIncrement13 {
        public void autoIncrement() {
            synchronized (heapObj) {
                for (int i = 0; i < 5; i++) {
                    number++;
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁,number:" + number);
                }
            }
        }
    }

    /**
     * <p>自增器-同步代码块-锁对象是类对象</p>
     *
     * @author hanchao 2018/3/18 13:12
     **/
    static class SyncCodeBlockIncrement14 {
        public void autoIncrement() {
            //同步代码14-加锁对象是类对象
            synchronized (SyncCodeBlockIncrement14.class) {
                for (int i = 0; i < 5; i++) {
                    number++;
                    System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁,number:" + number);
                }
            }
        }
    }

    /**
     * <p>自增器-普通同步方法-调用这个方法的对象</p>
     *
     * @author hanchao 2018/3/18 11:36
     **/
    static class SyncMethodIncrement {
        public synchronized void autoIncrement() {
            for (int i = 0; i < 5; i++) {
                number++;
                System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁,number:" + number);
            }
        }
    }

    /**
     * <p>自增器-静态同步方法-此类的所有对象</p>
     *
     * @author hanchao 2018/3/18 11:42
     **/
    static class SyncStaticMethodIncrement {
        public static synchronized void autoIncrement() {
            for (int i = 0; i < 5; i++) {
                number++;
                System.out.println("线程[" + Thread.currentThread().getName() + "]获取锁,number:" + number);
            }
        }
    }

    /**
     * <p>synchronized-学习示例</p>
     *
     * @author hanchao 2018/3/18 11:28
     **/
    public static void main(String[] args) throws InterruptedException {
        /**
         * 0 无同步        no
         * 10 同步代码块-加锁对象是类本地变量      类的多个对象 no
         * 11 同步代码块-加锁对象是类本地变量      类的单个对象 ok
         * 12 同步代码块-加锁对象是类静态变量      类的多个对象 ok
         * 13 同步代码块-加锁对象是共享变量        类的多个对象 ok
         * 14 同步代码块-加锁对象是类对象          类的多个对象 ok
         * 20 普通同步方法     类的多个对象 no
         * 21 普通同步方法     类的单个对象 ok
         * 22 静态同步方法     类的多个对象 ok
         */
        //测试类型
        int type = 22;
        //多线程数量
        int num = 100000;
        //休眠等待时间
        int sleep = 5000;
        switch (type) {
            case 0:
                System.out.println("无同步措施的自增器");
                Increment increment0 = new Increment();
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment0.autoIncrement();
                    }).start();
                }
                Thread.sleep(sleep);
                System.out.println("无同步措施的自增器,最终number=" + number);
                break;
            case 10:
                System.out.println("通过synchronized定义同步代码块,作用范围:代码块,同步对象:加锁对象为类本地变量->类的多个对象no");
                SyncCodeBlockIncrement11 increment10 = new SyncCodeBlockIncrement11();
                for (int i = 0; i < num; i++) {
                    //如果调用其他方法的同步方法，则计算结果错误
                    if (i % 2 == 0) {//模拟多个对象
                        new Thread(() -> {
                            new SyncCodeBlockIncrement11().autoIncrement();
                        }).start();
                    } else {//模拟同一个对象的多次调用
                        new Thread(() -> {
                            increment10.autoIncrement();
                        }).start();
                    }
                }
                Thread.sleep(sleep);
                System.out.println("同步代码块:加锁对象为类本地变量,通过类的多个对象调用同步代码块,最终number=" + number);
                break;
            case 11:
                System.out.println("通过synchronized定义同步代码块,作用范围:代码块,同步对象:加锁对象为类本地变量->类的单个对象ok");
                SyncCodeBlockIncrement11 increment11 = new SyncCodeBlockIncrement11();
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment11.autoIncrement();
                    }).start();
                }
                Thread.sleep(sleep);
                System.out.println("同步代码块:加锁对象为类本地变量,通过类的单个对象调用同步代码块,最终number=" + number);
                break;
            case 12:
                System.out.println("通过synchronized定义同步代码块,作用范围:代码块,同步对象:加锁对象为类静态变量->类的多个对象ok");
                SyncCodeBlockIncrement12 increment12 = new SyncCodeBlockIncrement12();
                for (int i = 0; i < num; i++) {
                    if (i % 2 == 0) {//模拟多个对象
                        new Thread(() -> {
                            new SyncCodeBlockIncrement12().autoIncrement();
                        }).start();
                    } else {//模拟同一个对象的多次调用
                        new Thread(() -> {
                            increment12.autoIncrement();
                        }).start();
                    }
                }
                Thread.sleep(sleep);
                System.out.println("同步代码块:加锁对象为类静态变量,通过类的多个对象调用同步代码块,最终number=" + number);
                break;
            case 13:
                System.out.println("通过synchronized定义同步代码块,作用范围:代码块,同步对象:加锁对象为共享变量->类的多个对象ok");
                SyncCodeBlockIncrement13 increment13 = new SyncCodeBlockIncrement13();
                for (int i = 0; i < num; i++) {
                    if (i % 2 == 0) {//模拟多个对象
                        new Thread(() -> {
                            new SyncCodeBlockIncrement13().autoIncrement();
                        }).start();
                    } else {//模拟同一个对象的多次调用
                        new Thread(() -> {
                            increment13.autoIncrement();
                        }).start();
                    }
                }
                Thread.sleep(sleep);
                System.out.println("同步代码块:加锁对象为共享变量,通过类的多个对象调用同步代码块,最终number=" + number);
                break;
            case 14:
                System.out.println("通过synchronized定义同步代码块,作用范围:代码块,同步对象:加锁对象为类对象->类的多个对象ok");
                SyncCodeBlockIncrement14 increment14 = new SyncCodeBlockIncrement14();
                for (int i = 0; i < num; i++) {
                    if (i % 2 == 0) {//模拟多个对象
                        new Thread(() -> {
                            new SyncCodeBlockIncrement14().autoIncrement();
                        }).start();
                    } else {//模拟同一个对象的多次调用
                        new Thread(() -> {
                            increment14.autoIncrement();
                        }).start();
                    }
                }
                Thread.sleep(sleep);
                System.out.println("同步代码块:加锁对象为类对象,通过类的多个对象调用同步代码块,最终number=" + number);
                break;
            case 20:
                System.out.println("通过synchronized定义普通同步方法,作用范围:整个方法,同步对象:类的多个对象no。");
                SyncMethodIncrement increment20 = new SyncMethodIncrement();
                for (int i = 0; i < num; i++) {
                    if (i % 2 == 0) {//模拟多个对象
                        new Thread(() -> {
                            new SyncMethodIncrement().autoIncrement();
                        }).start();
                    } else {//模拟同一个对象的多次调用
                        new Thread(() -> {
                            increment20.autoIncrement();
                        }).start();
                    }
                }
                Thread.sleep(sleep);
                System.out.println("同步方法:修饰方法为普通方法,通过类的多个对象调用同步方法,最终number=" + number);
                break;
            case 21:
                System.out.println("通过synchronized定义普通同步方法,作用范围:整个方法,同步对象:类的单个对象ok。");
                SyncMethodIncrement increment21 = new SyncMethodIncrement();
                for (int i = 0; i < num; i++) {
                    new Thread(() -> {
                        increment21.autoIncrement();
                    }).start();
                }
                Thread.sleep(sleep);
                System.out.println("同步方法:修饰方法为普通方法,通过类的单个对象调用同步方法,最终number=" + number);
                break;
            case 22:
                System.out.println("通过synchronized定义静态同步方法,作用范围:整个方法,同步对象:类的多个对象ok。");
                SyncStaticMethodIncrement increment22 = new SyncStaticMethodIncrement();
                for (int i = 0; i < num; i++) {
                    if (i % 2 == 0) {//模拟多个对象
                        new Thread(() -> {
                            new SyncStaticMethodIncrement().autoIncrement();
                        }).start();
                    } else {//模拟同一个对象的多次调用
                        new Thread(() -> {
                            increment22.autoIncrement();
                        }).start();
                    }
                }
                Thread.sleep(sleep);
                System.out.println("同步方法:修饰方法为静态方法,通过类的多个对象调用同步方法,最终number=" + number);
                break;
            default:
                break;
        }
    }
}
