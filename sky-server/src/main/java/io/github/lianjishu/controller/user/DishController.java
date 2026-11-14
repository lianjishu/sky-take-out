package io.github.lianjishu.controller.user;

import io.github.lianjishu.constant.StatusConstant;
import io.github.lianjishu.entity.Dish;
import io.github.lianjishu.result.Result;
import io.github.lianjishu.service.DishService;
import io.github.lianjishu.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    private final DishService dishService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public DishController(DishService dishService, RedisTemplate<String, Object> redisTemplate) {
        this.dishService = dishService;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类id查询菜品， categoryId: {}", categoryId);
        //构造redis的key, 格式: dish_${category_id}
        String key = "dish_" + categoryId;
        //查询redis中是否存在菜品数据
        log.info("查询redis， key: {}", key);
        List<DishVO> dishList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        log.info("redis查询结果: {}", dishList);
        if (dishList != null && !dishList.isEmpty()) {
            //如果存在，直接返回，无须查询数据库
            return Result.success(dishList);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        List<DishVO> list = dishService.listWithFlavor(dish);

        //如果不存在，查询数据库，将查询结果放入redis
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

}
