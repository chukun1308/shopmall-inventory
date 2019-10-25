package com.chukun.inventory.request;

/**
 * 请求接口
 */
public interface InventoryRequest {

    /**
     * 处理数据逻辑
     */
    void process();

    /**
     * 获取商品ID
     * @return
     */
    Integer getProductId();
}
