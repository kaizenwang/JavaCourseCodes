/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Homework04;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class App {
    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
//        int result = thread();
//        int result = countDownLatch();
//        int result = future();
//        int result = completeFuture();
        int result = semaphore();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int thread() throws InterruptedException {
        final int[] results = new int[1];
        Thread thread = new Thread(() -> results[0] = sum(), "sum-thread");
        thread.start();
        thread.join();
        return results[0];
    }

    private static int completeFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(App::sum);
        return completableFuture.get();
    }

    private static int future() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = App::sum;
        Future<Integer> future = executor.submit(callable);
        executor.shutdown();
        return future.get();
    }

    private static int countDownLatch() throws InterruptedException {
        final int[] result = new int[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            result[0] = sum();
            latch.countDown();
        }, "count-down-latch-thread");
        thread.start();
        latch.await();
        return result[0];
    }

    private static int semaphore() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        final int[] result = new int[1];
        semaphore.acquire();
        Thread thread = new Thread(() -> {
            result[0] = sum();
            semaphore.release();
        });
        thread.start();
        semaphore.acquireUninterruptibly();
        return result[0];
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
