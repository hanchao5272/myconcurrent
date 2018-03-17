package pers.hanchao.concurrent.eg03;

/**
 * <p>JMM Java内存模型</p>
 *
 * @author hanchao 2018/3/15 22:52
 **/
public class ConcurrentJMMDemo {
    /**
     * 一个自定义线程
     */
    static class MyDemoThread extends Thread {
        //本地变量
        private Integer index = 110;

        //共享变量的引用
        private Long timeStamp;

        /**
         * @param name      线程名
         * @param timeStamp 创建时间戳
         */
        public MyDemoThread(String name, Long timeStamp) {
            super(name);
            this.timeStamp = timeStamp;
        }

        @Override
        public void run() {
            System.out.println("Thread name : " + super.getName() + ",timeStamp : " + timeStamp);
            super.run();
        }

        /**
         * <p>简单线程示例</p>
         *
         * @author hanchao 2018/3/15 22:59
         **/
        public static void main(String[] args) {
            //定义一个共享变量
            Long now = System.currentTimeMillis();
            //创建线程
            Thread thread = new MyDemoThread("张三", now);
            //启动线程
            thread.start();
        }
    }
}
