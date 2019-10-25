package com.chukun.inventory.request;

import com.chukun.inventory.model.ProductInventory;
import com.chukun.inventory.service.ProductInventoryService;

/**
 * 重新加载商品库存的缓存
 * @author chukun
 *
 */
public class ProductInventoryCacheRefreshRequest implements InventoryRequest {

	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 商品库存Service
	 */
	private ProductInventoryService productInventoryService;
	
	public ProductInventoryCacheRefreshRequest(Integer productId,
			ProductInventoryService productInventoryService) {
		this.productId = productId;
		this.productInventoryService = productInventoryService;
	}
	
	@Override
	public void process() {
		// 从数据库中查询最新的商品库存数量
		ProductInventory productInventory = productInventoryService.findProductInventory(productId);
		System.out.println("获取数据库数据 : productInventory id : "+productInventory.getProductId()+" 数据库当前库存: "+productInventory.getInventoryCnt());
		// 将最新的商品库存数量，刷新到redis缓存中去
		productInventoryService.setProductInventoryCache(productInventory);
		System.out.println("刷新redis缓存 : productInventory id : "+productInventory.getProductId()+" redis当前库存: "+productInventory.getInventoryCnt());
	}
	
	public Integer getProductId() {
		return productId;
	}
	
}
