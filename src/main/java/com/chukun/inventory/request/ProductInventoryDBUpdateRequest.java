package com.chukun.inventory.request;


import com.chukun.inventory.model.ProductInventory;
import com.chukun.inventory.service.ProductInventoryService;

import java.util.concurrent.TimeUnit;

/**
 * 比如说一个商品发生了交易，那么就要修改这个商品对应的库存
 * 
 * 此时就会发送请求过来，要求修改库存，那么这个可能就是所谓的data update request，数据更新请求
 * 
 * cache aside pattern
 * 
 * （1）删除缓存
 * （2）更新数据库
 * 
 * @author chukun
 *
 */
public class ProductInventoryDBUpdateRequest implements InventoryRequest {

	/**
	 * 商品库存
	 */
	private ProductInventory productInventory;
	/**
	 * 商品库存Service
	 */
	private ProductInventoryService productInventoryService;
	
	public ProductInventoryDBUpdateRequest(ProductInventory productInventory,
										   ProductInventoryService productInventoryService) {
		this.productInventory = productInventory;
		this.productInventoryService = productInventoryService;
	}
	
	@Override
	public void process() {
		System.out.println("删除redis缓存 : productInventory id : "+productInventory.getProductId()+" 当前更新库存: "+productInventory.getInventoryCnt());
		// 删除redis中的缓存
		productInventoryService.removeProductInventoryCache(productInventory); 
		// 修改数据库中的库存
		System.out.println("更新数据库存 : productInventory id : "+productInventory.getProductId()+" 当前更新库存: "+productInventory.getInventoryCnt());
		productInventoryService.updateProductInventory(productInventory);

		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("更新数据库存 sleep 5s");
		} catch (InterruptedException e) {

		}
	}
	
	/**
	 * 获取商品id
	 */
	public Integer getProductId() {
		return productInventory.getProductId();
	}
	
}
