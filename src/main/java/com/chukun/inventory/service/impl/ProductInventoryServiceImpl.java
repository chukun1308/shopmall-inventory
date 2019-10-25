package com.chukun.inventory.service.impl;

import javax.annotation.Resource;

import com.chukun.inventory.mapper.ProductInventoryMapper;
import com.chukun.inventory.model.ProductInventory;
import com.chukun.inventory.redis.RedisMapper;
import com.chukun.inventory.service.ProductInventoryService;
import org.springframework.stereotype.Service;

/**
 * 商品库存Service实现类
 * @author Administrator
 *
 */
@Service("productInventoryService")  
public class ProductInventoryServiceImpl implements ProductInventoryService {

	@Resource
	private ProductInventoryMapper productInventoryMapper;
	@Resource
	private RedisMapper redisMapper;
	
	@Override
	public void updateProductInventory(ProductInventory productInventory) {
		productInventoryMapper.updateProductInventory(productInventory); 
	}

	@Override
	public void removeProductInventoryCache(ProductInventory productInventory) {
		String key = "product:inventory:" + productInventory.getProductId();
		redisMapper.delete(key);
	}
	
	/**
	 * 根据商品id查询商品库存
	 * @param productId 商品id 
	 * @return 商品库存
	 */
	public ProductInventory findProductInventory(Integer productId) {
		return productInventoryMapper.findProductInventory(productId);
	}
	
	/**
	 * 设置商品库存的缓存
	 * @param productInventory 商品库存
	 */
	public void setProductInventoryCache(ProductInventory productInventory) {
		String key = "product:inventory:" + productInventory.getProductId();
		redisMapper.set(key, String.valueOf(productInventory.getInventoryCnt()));
	}
	
	/**
	 * 获取商品库存的缓存
	 * @param productId
	 * @return
	 */
	public ProductInventory getProductInventoryCache(Integer productId) {
		Long inventoryCnt = 0L;
		
		String key = "product:inventory:" + productId;
		String result = null;
		if(redisMapper.existKey(key)) {
			result = redisMapper.get(key).toString();
		}
		if(result != null && !"".equals(result)) {
			try {
				inventoryCnt = Long.valueOf(result);
				return new ProductInventory(productId, inventoryCnt);
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
		
		return null;
	}
	
}
