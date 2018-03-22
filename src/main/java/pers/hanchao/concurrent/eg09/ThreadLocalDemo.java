package pers.hanchao.concurrent.eg09;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
     * <p>Title: 一个自定义数据库连接工具</p>
     * @author 韩超 2018/3/22 14:18
     */
    static class MyDBUtils{
//        String driver = "oracle.jdbc.driver.OracleDriver";//oracle
//        String url = "jdbc:oracle:thin:@localhost1521:test";//oracle
        static String driver = "com.mysql.jdbc.Driver";
        static String url = "jdbc:mysql://localhost:3306/exam?useSSL=false";
        static String username = "root";
        static String password = "1qaz@WSX";

        //每个连接线程一个连接实例
        static ThreadLocal<Connection> connection = new ThreadLocal<Connection>(){
            //重写ThreadLocal的initialValue方法，获取连接
            @Override
            protected Connection initialValue() {
                Connection connection = null;
                try {
                    //加载JDBC驱动
                    Class.forName(driver);
                    //获得连接
                    connection = DriverManager.getConnection(url,username,password);
                    System.out.println(Thread.currentThread().getName() + " 获取了一个MySql连接...是否关闭---" + connection.isClosed());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return connection;
            }
        };

        /**
         * <p>Title: 获取连接</p>
         * @author 韩超 2018/3/22 14:28
         */
        public static Connection getConnection(){
            return  connection.get();
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

        ///////////////////////////////////  ThreadLocal的基本方法 /////////////////////////////

        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
        //ThreadLocal默认值为null
        System.out.println("ThreadLocal默认值为null,所以需要先set()才能使用--" +threadLocal.get() + "\n");

        //ThreadLocal在每个线程中都需要单独赋值
        Thread.sleep(100);
        threadLocal.set(1);
        System.out.println("通过get()获取当前线程中的值，线程[" +  Thread.currentThread().getName() + "] value =" + threadLocal.get());
        new Thread(()->{
            System.out.println("每个线程中需要单独赋值，线程[" +  Thread.currentThread().getName() + "] value =" + threadLocal.get());
            threadLocal.set(1);
            System.out.println("每个线程中需要单独赋值，线程[" +  Thread.currentThread().getName() + "] value =" + threadLocal.get() + "\n");
        }).start();

        //通过remove()删除当前线程的值
        Thread.sleep(100);
        threadLocal.remove();
        System.out.println("通过remove()删除当前线程的值，线程[" +  Thread.currentThread().getName() + "] value =" + threadLocal.get());

        Thread.sleep(100);
        //重写protected initialValue()方法用来设置初始值
        ThreadLocal<String > stringThreadLocal = new ThreadLocal<String >(){
            @Override
            protected String initialValue() {
                return "Hello World!";
            }
        };
        System.out.println("\n重写protected initialValue()方法用来设置初始值，" + Thread.currentThread().getName() + "---" + stringThreadLocal.get());

        ///////////////////////////////////  ThreadLocal的基本方法 /////////////////////////////
        Thread.sleep(100);
        //ThreadLocal常用场景01：数据库连接
        System.out.println("\n=========ThreadLocal常用场景01：数据库连接");
        for(int i = 0; i < 5; i++) {
            new Thread(()->{
                //获取
                Connection connection = MyDBUtils.getConnection();
                //关闭连接
                try {
                    connection.close();
                    System.out.println(Thread.currentThread().getName() + " 关闭了连接.是否关闭---" + connection.isClosed());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(1000);
        //ThreadLocal常用场景02：session管理
        System.out.println("\n=========ThreadLocal常用场景02：session管理");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\n    private static final ThreadLocal threadSession = new ThreadLocal();\n" +
                "    \n" +
                "    public static Session getSession() throws InfrastructureException {\n" +
                "        Session s = (Session) threadSession.get();\n" +
                "        try {\n" +
                "            if (s == null) {\n" +
                "                s = getSessionFactory().openSession();\n" +
                "                threadSession.set(s);\n" +
                "            }\n" +
                "        } catch (HibernateException ex) {\n" +
                "            throw new InfrastructureException(ex);\n" +
                "        }\n" +
                "        return s;\n" +
                "    }");
        System.out.println("--------------------------------------------------------------------------");

        /*
    private static final ThreadLocal threadSession = new ThreadLocal();

    public static Session getSession() throws InfrastructureException {
        Session s = (Session) threadSession.get();
        try {
            if (s == null) {
                s = getSessionFactory().openSession();
                threadSession.set(s);
            }
        } catch (HibernateException ex) {
            throw new InfrastructureException(ex);
        }
        return s;
    }
         */
    }
}
