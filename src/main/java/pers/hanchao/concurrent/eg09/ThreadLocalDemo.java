package pers.hanchao.concurrent.eg09;

/**
 * <p>线程本地变量-ThreadLocal</p>
 *
 * @author hanchao 2018/3/21 22:58
 **/
public class ThreadLocalDemo {

    /**
     * <p>共享变量、线程本地变量--示例</p>
     *
     * @author hanchao 2018/3/21 23:46
     **/
    static class MyNum {
        //共享变量，多个线程共享
        int num;
        //本地变量，每个线程单独创建一个副本
        ThreadLocal<Integer> threadLocalNum = new ThreadLocal<Integer>();

        public MyNum(int num, Integer threadLocalNum) {
            this.num = num;
            this.threadLocalNum.set(threadLocalNum);
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public ThreadLocal<Integer> getThreadLocalNum() {
            return threadLocalNum;
        }
    }

    /**
     * <p>ThreadLocal-线程本地变量</p>
     *
     * @author hanchao 2018/3/21 22:58
     **/
    public static void main(String[] args) throws InterruptedException {
        //构造
        MyNum myNum = new MyNum(0, new Integer(0));
        System.out.println("线程[" + Thread.currentThread().getName()
                + "]----num：" + myNum.getNum() + ",threadLocalNum：" + myNum.getThreadLocalNum().get().intValue() + "\n");
        //多线程运行
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                //每个线程中执行加1计算
                myNum.setNum(myNum.getNum() + 1);
                //打印结果
                System.out.println("线程[" + Thread.currentThread().getName()
                        + "]----num: " + myNum.getNum());

                //ThreadLocal只能在自己的线程中设置值
                if (myNum.getThreadLocalNum().get() != null) {
                    myNum.getThreadLocalNum().set(myNum.getThreadLocalNum().get().intValue() + 1);
                    //打印结果
                    System.out.println("线程[" + Thread.currentThread().getName()
                            + "]----threadLocalNum: " + myNum.getThreadLocalNum().get().intValue());
                } else {
                    System.out.println("线程[" + Thread.currentThread().getName()
                            + "]----threadLocalNum is null ,threadLocalNum to " + 1);
                    myNum.getThreadLocalNum().set(1);
                }
            }).start();
            Thread.sleep(100);
            System.out.println();
        }
        Thread.sleep(100);
        System.out.println("线程[" + Thread.currentThread().getName()
                + "]----num：" + myNum.getNum() + ",threadLocalNum：" + myNum.getThreadLocalNum().get().intValue());

        System.out.println("\n线程共享变量在多个线程中共享;线程本地变量每个线程独有一份副本，互补影响;main也是一个线程。");
    }
}
