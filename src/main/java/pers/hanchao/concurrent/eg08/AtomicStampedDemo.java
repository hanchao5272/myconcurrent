package pers.hanchao.concurrent.eg08;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子变量-带时间戳的原子引用类型
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
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //打印期望值
            System.out.println(Thread.currentThread().getName() + "---- expect: " + expect);
            //打印实际值
            System.out.println(Thread.currentThread().getName() + "---- actual: " + reference.get());
            //进行CAS操作
            boolean result = reference.compareAndSet("A", "X");
            //打印操作结果
            System.out.println(Thread.currentThread().getName() + "---- result: " + result + " ==》 final reference = " + reference.get());
        }).start();

        new Thread(() -> {
            //进行ABA操作
            System.out.print(Thread.currentThread().getName() + "---- change: " + reference.get());
            reference.compareAndSet("A", "B");
            System.out.print(" -- > B");
            reference.compareAndSet("B", "A");
            System.out.println(" -- > A");
        }).start();

        //通过版本戳解决ABA问题
        Thread.sleep(1000);
        System.out.println("\n==========通过版本戳解决ABA问题：");
        AtomicStampedReference<String> stampedRef = new AtomicStampedReference<>("A", 1);
        new Thread(() -> {
            //获取期望值
            String expect = stampedRef.getReference();
            //获取期望版本戳
            Integer stamp = stampedRef.getStamp();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //打印期望值和期望版本戳
            System.out.println(Thread.currentThread().getName() + "---- expect: " + expect + "-" + stamp);
            //打印实际值和实际版本戳
            System.out.println(Thread.currentThread().getName() + "---- actual: " + stampedRef.getReference() + "-" + stampedRef.getStamp());
            //进行CAS操作（带版本戳）
            boolean result = stampedRef.compareAndSet("A", "X", stamp, stamp + 1);
            //打印操作结果
            System.out.println(Thread.currentThread().getName() + "---- result: " + result + " ==》 final reference = " + stampedRef.getReference() + "-" + stampedRef.getStamp());
        }).start();

        new Thread(() -> {
            ////进行ABA操作（带时间戳）
            System.out.print(Thread.currentThread().getName() + "---- change: " + stampedRef.getReference() + "-" + stampedRef.getStamp());
            stampedRef.compareAndSet("A", "B", stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.print(" -- > B" + "-" + stampedRef.getStamp());
            stampedRef.compareAndSet("B", "A", stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.println(" -- > A" + "-" + stampedRef.getStamp());
        }).start();

        //AtomicMarkableReference与AtomicStampedReference的作用类似
        //区别：AtomicStampedReference以版本戳标记变量
        //区别：AtomicMarkableReference以true和false标记变量
        AtomicMarkableReference<String> markableReference = new AtomicMarkableReference<>("A", true);
    }
}
