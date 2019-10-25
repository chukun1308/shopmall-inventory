package com.chukun.inventory.thread;

import com.chukun.inventory.request.InventoryRequest;
import com.chukun.inventory.request.RequestQueue;
import java.util.concurrent.*;

/**
 * 请求处理线程池：单例
 */
public class InventoryRequestProcessorThreadPool  {

    private static class InventoryRequestProcessorThreadPoolSingleTon{
        private static InventoryRequestProcessorThreadPool instance;
        static{
            instance = new InventoryRequestProcessorThreadPool();
        }

        public static InventoryRequestProcessorThreadPool getInstance(){
            return instance;
        }
    }

    /**
     * 线程池
     */
    private ExecutorService threadPool = new ThreadPoolExecutor(10, 30, 5, TimeUnit.MINUTES,
                            new ArrayBlockingQueue<>(1024), r -> {
                                Thread t = new Thread(r);
                                t.setName("inventoryThreadPool-"+t.getId());
                                return t;
                            },new ThreadPoolExecutor.CallerRunsPolicy());

    public InventoryRequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for(int i = 0; i < 10; i++) {
            BlockingQueue<InventoryRequest> queue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(queue);
            threadPool.submit(new InventoryRequestProcessorThread(queue));
        }
    }

    /**
     * jvm的机制去保证多线程并发安全
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     *
     * @return
     */
    public static InventoryRequestProcessorThreadPool getInstance() {
        return InventoryRequestProcessorThreadPoolSingleTon.getInstance();
    }

    /**
     * 初始化线程池
     */
    public static void initialThreadPool(){
        getInstance();
    }

}
