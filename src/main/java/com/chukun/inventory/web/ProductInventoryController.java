package com.chukun.inventory.web;

import com.chukun.inventory.constant.CommonConstant;
import com.chukun.inventory.model.ProductInventory;
import com.chukun.inventory.request.InventoryRequest;
import com.chukun.inventory.request.ProductInventoryCacheRefreshRequest;
import com.chukun.inventory.request.ProductInventoryDBUpdateRequest;
import com.chukun.inventory.service.ProductInventoryService;
import com.chukun.inventory.service.RequestAsyncProcessService;
import com.chukun.inventory.vo.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author chukun
 *  使用内存队列的方式，处理缓存不一致的问题
 */
@RestController("productInventoryController")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService productInventoryService;
    @Autowired
    private RequestAsyncProcessService requestAsyncProcessService;

    @RequestMapping("/updateProductInventory")
    public ResponseData updateProductInventory(ProductInventory productInventory){
        ResponseData responseData = null;
        try{
            System.out.println("updateProductInventory coming.....");
            InventoryRequest request = new ProductInventoryDBUpdateRequest(productInventory,productInventoryService);
            requestAsyncProcessService.process(request);
            responseData = new ResponseData(ResponseData.SUCCESS);
            System.out.println("updateProductInventory success.....");
        }catch (Exception e){
            responseData = new ResponseData(ResponseData.FAILURE);
        }
        return responseData;
    }

    /**
     * 获取商品缓存的数据
     * @param productId
     * @return
     */
    @RequestMapping("/getProductInventory")
    public ProductInventory getProductInventory(Integer productId){

        ProductInventory productInventory = null;

        try{
            System.out.println("getProductInventory coming.....");
            InventoryRequest request = new ProductInventoryCacheRefreshRequest(
                    productId, productInventoryService);
            requestAsyncProcessService.process(request);
            // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            while (true){
                //等待超过200ms没有从缓存中获取到结果
                if(waitTime> CommonConstant.REQUEST_TIME_OUT){
                    break;
                }
                //尝试从redis中读取一次数据
                productInventory = productInventoryService.getProductInventoryCache(productId);
                // 如果读取到了结果，那么就返回
                if(!Objects.isNull(productInventory)) {
                    return productInventory;
                }else{
                    //没取到，就等待20毫秒
                    TimeUnit.MILLISECONDS.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            //如果还取不到，就尝试在数据库里面获取一次
            productInventory = productInventoryService.findProductInventory(productId);
            if(!Objects.isNull(productInventory)){
                return productInventory;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //实在获取不到，说明没有此数据，就返回空数据
        return new ProductInventory(productId, -1L);
    }

}
