package pers.hanchao.concurrent.eg03;

import org.apache.commons.lang3.RandomUtils;

import java.util.Map;

/**
 * <p>并发-有序性</p>
 * @author hanchao 2018/3/17 23:32
 **/
public class ConcurrentOrderlyDemo2 {
    private static boolean ready;
    private static int x;
    private static int y;
    private static long z;

    /**
     * <p>并发有序性示例</p>
     * @author hanchao 2018/3/17 23:32
     **/
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (!ready){
                Thread.currentThread().yield();
            }
            System.out.println("x：" + x);
            System.out.println("y：" + y);
            System.out.println("z：" + z);
        }).start();
        new Thread(()->{
            x = x ++;
            x *= 2;
            x += 1013;
            x -= 110;
            x = Math.abs(x);
            x = (int) Math.floor(x);
            x = Math.max(x,10124);
            x = 999 + x;
            y = y --;
            y = Math.abs(y);
            y = y * 1012 * 11;
            y = y / 24;
            y = Math.decrementExact(y);
            y = (int) Math.floor(y);
            z++;
            z++;
            z++;
            z++;
            z++;
            z++;
            z = (long) Math.pow(z,z);
            ready = true;
        }).start();
    }
}
