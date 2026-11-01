package io.github.lianjishu.service;

public interface ShopService {

    /**
     * 设置店铺状态
     *
     * @param status 店铺状态 0: 打烊中 1: 营业中
     */
    void setStatus(Integer status);

    /**
     * 获取店铺状态
     *
     * @return 店铺状态 0: 打烊中 1: 营业中
     */
    Integer getStatus();

}
