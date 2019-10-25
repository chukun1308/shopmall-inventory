package com.chukun.inventory.listener;

import com.chukun.inventory.thread.InventoryRequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听器
 */
public class InitializeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       //初始化线程池
        InventoryRequestProcessorThreadPool.initialThreadPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
