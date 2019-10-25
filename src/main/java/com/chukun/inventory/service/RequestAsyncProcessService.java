package com.chukun.inventory.service;


import com.chukun.inventory.request.InventoryRequest;

/**
 * 请求异步执行的service
 * @author chukun
 *
 */
public interface RequestAsyncProcessService {

	void process(InventoryRequest request);
	
}
