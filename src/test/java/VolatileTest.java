import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest{
    private static  AtomicInteger num = new AtomicInteger(0);
    //使用CountDownLatch来等待计算线程执行完
    private static CountDownLatch countDownLatch = new CountDownLatch(30);
    public static void main(String []args) throws InterruptedException {
        //开启30个线程进行累加操作
        for(int i=0;i<30;i++){
            new Thread(() -> {
                for(int j=0;j<10000;j++){
                    num.incrementAndGet();//自加操作
                }
                countDownLatch.countDown();
            }).start();
        }
        //等待计算线程执行完
        countDownLatch.await();
        System.out.println(num);
    }
}
