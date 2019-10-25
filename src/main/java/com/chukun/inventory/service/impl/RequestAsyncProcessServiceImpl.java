package com.chukun.inventory.service.impl;

import com.chukun.inventory.request.InventoryRequest;
import com.chukun.inventory.request.RequestQueue;
import com.chukun.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {


    @Override
    public void process(InventoryRequest request) {

        try{
            // 做请求的路由，根据每个请求的商品id，路由到对应的内存队列中去
            BlockingQueue<InventoryRequest> routingQueue = getRoutingQueue(request.getProductId());
            // 将请求放入对应的队列中，完成路由操作
            routingQueue.put(request);
        }catch (Exception e){

        }
    }

    /**
     * 获取路由到的内存队列
     * @param productId 商品id
     * @return 内存队列
     */
    private BlockingQueue<InventoryRequest> getRoutingQueue(Integer productId){
        RequestQueue requestQueue = RequestQueue.getInstance();
        //先获取productId的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (Objects.isNull(key))? 0:(h=key.hashCode())^(h>>>16);
        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的
        int index = (requestQueue.queueSize()-1) & hash;
        return requestQueue.getQueue(index);
    }
}
