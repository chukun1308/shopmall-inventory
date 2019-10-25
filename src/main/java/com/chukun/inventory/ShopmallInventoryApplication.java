package com.chukun.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.chukun.inventory.mapper"})
public class ShopmallInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmallInventoryApplication.class, args);
    }

}
