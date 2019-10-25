package com.chukun.inventory.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisMapper {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * set cache
     * @param key
     * @param value
     */
    public void set(String key, String value){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(key,value);
    }

    /**
     * 获取key
     * @param key
     * @return
     */
    public Object get(String key) {
        ValueOperations ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 删除对应的key
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断是否有此key
     * @param key
     * @return
     */
    public boolean existKey(String key){
        return redisTemplate.hasKey(key);
    }
}
