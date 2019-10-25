package com.chukun.inventory.thread;


import com.chukun.inventory.request.InventoryRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 */
public class InventoryRequestProcessorThread implements Callable<Boolean> {

    private BlockingQueue<InventoryRequest> queue;

    public InventoryRequestProcessorThread(BlockingQueue<InventoryRequest> queue) {
        this.queue = queue;
    }
    /**
     * 监控的内存队列
     * @return
     * @throws Exception
     */
    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                // ArrayBlockingQueue
                // Blocking就是说明，如果队列满了，或者是空的，那么都会在执行操作的时候，阻塞住
                InventoryRequest request = queue.take();
                // 执行这个request操作
                request.process();
            }
        }catch (Exception e){

        }
        return true;
    }
}
