package pers.hanchao.concurrent.eg08;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子变量-带版本戳的原子引用类型
 * Created by 韩超 on 2018/3/21.
 */
public class AtomicStampedDemo {
    /**
     * <p>Title: AtomicStampedReference</p>
     *
     * @author 韩超 2018/3/21 14:18
     */
    public static void main(String[] args) throws InterruptedException {
        //ABA问题
        System.out.println("==========ABA问题：");
        AtomicReference<String> reference = new AtomicReference<>("A");
        new Thread(() -> {
            //获取期望值
            String expect = reference.get();
            //打印期望值
            System.out.println(Thread.currentThread().getName() + "---- expect: " + expect);
            try {
                //干点别的事情
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //打印实际值
            System.out.println(Thread.currentThread().getName() + "---- actual: " + reference.get());
            //进行CAS操作
            boolean result = reference.compareAndSet("A", "X");
            //打印操作结果
            System.out.println(Thread.currentThread().getName() + "---- result: " + result + " ==》 final reference = " + reference.get());
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //进行ABA操作
            System.out.print(Thread.currentThread().getName() + "---- change: " + reference.get());
            reference.compareAndSet("A", "B");
            System.out.print(" -- > B");
            reference.compareAndSet("B", "A");
            System.out.println(" -- > A");
        }).start();

        //////////////////////////////////////// 方法实例 ////////////////////////////////////////
        Thread.sleep(1000);
        //AtomicStampedReference的方法汇总：
        System.out.println("\n=========AtomicStampedReference的方法汇总：");
        //构造方法：AtomicStampedReference<>(V initialRef, int initialStamp)
        System.out.println("构造方法：AtomicStampedReference<>(V initialRef, int initialStamp)");
        AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>("David", 1);

        //getStamp和getReference：获取版本戳和引用对象
        System.out.println("\ngetReference():获取引用对象的值----" + stampedReference.getReference());
        System.out.println("getStamp():获取引用对象的值的版本戳----" + stampedReference.getStamp());

        //set(V newReference, int newStamp):无条件的重设引用和版本戳的值
        stampedReference.set("Joke", 0);
        System.out.println("\nset(V newReference, int newStamp):无条件的重设引用和版本戳的值---[reference:"
                + stampedReference.getReference() + ",stamp:" + stampedReference.getStamp() + "]");

        //attemptStamp(V expectedReference, int newStamp)
        stampedReference.attemptStamp("Joke", 11);
        System.out.println("\nattemptStamp(V expectedReference, int newStamp):如果引用为期望值，则重设版本戳---[reference:"
                + stampedReference.getReference() + ",stamp:" + stampedReference.getStamp() + "]");

        //compareAndSet(V expectedReference,V newReference,int expectedStamp,int newStamp)
        System.out.println("\ncompareAndSet(V expectedReference,V newReference,int expectedStamp,int newStamp):" +
                "\n如果引用为期望值且版本戳正确，则赋新值并修改版本戳:");
        System.out.println("第一次：" + stampedReference.compareAndSet("Joke", "Tom", 11, 12));
        System.out.println("第二次：" + stampedReference.compareAndSet("Tom", "Grey", 11, 12));
        System.out.println("weakCompareAndSet不再赘述");

        //get(int[] stampHolder):通过版本戳获取引用当前值
        //参数为数组类型是因为基本类型无法传递引用，需要使用数组类型
        int[] stampHolder = new int[10];
        String aRef = stampedReference.get(stampHolder);
        System.out.println("\nget(int[] stampHolder):获取引用和版本戳,stampHolder[0]持有版本戳---[reference=" + aRef + ",stamp=" + stampHolder[0] + "].");

        //AtomicMarkableReference与AtomicStampedReference的作用类似
        //区别：AtomicStampedReference以版本戳标记变量
        //区别：AtomicMarkableReference以true和false标记变量
        AtomicMarkableReference<String> markableReference = new AtomicMarkableReference<>("A", true);

        //////////////////////////////////////// 通过版本戳解决ABA问题 ////////////////////////////////////////
        //通过版本戳解决ABA问题
        Thread.sleep(1000);
        System.out.println("\n==========通过版本戳解决ABA问题：");
        AtomicStampedReference<String> stampedRef = new AtomicStampedReference<>("A", 1);
        new Thread(() -> {
            //获取期望值
            String expect = stampedRef.getReference();
            //获取期望版本戳
            Integer stamp = stampedRef.getStamp();
            //打印期望值和期望版本戳
            System.out.println(Thread.currentThread().getName() + "---- expect: " + expect + "-" + stamp);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //打印实际值和实际版本戳
            System.out.println(Thread.currentThread().getName() + "---- actual: " + stampedRef.getReference() + "-" + stampedRef.getStamp());
            //进行CAS操作（带版本戳）
            boolean result = stampedRef.compareAndSet("A", "X", stamp, stamp + 1);
            //打印操作结果
            System.out.println(Thread.currentThread().getName() + "---- result: " + result + " ==》 final reference = " + stampedRef.getReference() + "-" + stampedRef.getStamp());
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ////进行ABA操作（带版本戳）
            System.out.print(Thread.currentThread().getName() + "---- change: " + stampedRef.getReference() + "-" + stampedRef.getStamp());
            stampedRef.compareAndSet("A", "B", stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.print(" -- > B" + "-" + stampedRef.getStamp());
            stampedRef.compareAndSet("B", "A", stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.println(" -- > A" + "-" + stampedRef.getStamp());
        }).start();
    }
}
