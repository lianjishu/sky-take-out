package io.github.lianjishu.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import io.github.lianjishu.service.ShopService;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    private static final String SHOP_STATUS_KEY = "SHOP_STATUS"; // 店铺状态key常量
    private final RedisTemplate<String, Object> redisTemplate; // Inject RedisTemplate dependency via constructor

    @Autowired
    public ShopServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置店铺状态
     *
     * @param status 店铺状态 0: 打烊中 1: 营业中
     */
    @Override
    public void setStatus(Integer status) {
        log.info("设置店铺状态: {} (0: 打烊中 1: 营业中)", status);
        redisTemplate.opsForValue().set(SHOP_STATUS_KEY, status);
    }

    /**
     * 获取店铺状态
     *
     * @return 店铺状态 0: 打烊中 1: 营业中
     */
    @Override
    public Integer getStatus() {
        Object status = redisTemplate.opsForValue().get(SHOP_STATUS_KEY);
        log.info("获取店铺状态: {} (0: 打烊中 1: 营业中)", status);
        return status == null ? 0 : (Integer) status;
    }

}
