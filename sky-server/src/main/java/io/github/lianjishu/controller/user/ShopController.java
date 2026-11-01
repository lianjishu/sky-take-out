package io.github.lianjishu.controller.user;

import io.github.lianjishu.result.Result;
import io.github.lianjishu.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api("店铺管理")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus() {
        log.info("获取店铺营业状态");
        Integer status = shopService.getStatus();

        return Result.success(status);
    }
}
