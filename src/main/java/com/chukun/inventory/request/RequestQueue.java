package com.chukun.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 请求内存队列
 */
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<BlockingQueue<InventoryRequest>> inventoryRequestQueues = new ArrayList<>();

    /**
     * 态内部类的方式，去初始化单例
     */
    private static class InventoryRequestQueueSingleTon{

        private static RequestQueue requestQueue;

        static {
            requestQueue = new RequestQueue();
        }

        public static RequestQueue getInstance(){
            return requestQueue;
        }
    }

    /**
     * jvm的机制去保证多线程并发安全
     *
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     *
     * @return
     */
    public static RequestQueue getInstance() {
        return InventoryRequestQueueSingleTon.getInstance();
    }

    /**
     * 获取内存队列的数量
     * @return
     */
    public int queueSize() {
        return inventoryRequestQueues.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public BlockingQueue<InventoryRequest> getQueue(int index) {
        return inventoryRequestQueues.get(index);
    }


    /**
     * 添加一个内存队列
     * @param queue
     */
    public void addQueue(BlockingQueue<InventoryRequest> queue) {
        this.inventoryRequestQueues.add(queue);
    }


}
