package io.github.lianjishu.controller.admin;

import io.github.lianjishu.dto.DishDTO;
import io.github.lianjishu.dto.DishPageQueryDTO;
import io.github.lianjishu.entity.Dish;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.result.Result;
import io.github.lianjishu.service.DishService;
import io.github.lianjishu.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags = "菜品管理")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final DishService dishService;

    @Autowired
    public DishController(RedisTemplate redisTemplate, DishService dishService) {
        this.redisTemplate = redisTemplate;
        this.dishService = dishService;
    }

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品信息
     * @return 统一结果
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.save(dishDTO);

        //清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

        return Result.success();
    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO 菜品分页查询参数
     * @return 菜品分页查询结果
     */
    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询菜品
     * @param id 菜品id
     * @return 菜品信息
     */
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getByDishId(@PathVariable Long id) {
        log.info("根据id查询菜品: {}", id);
        DishVO dishVO = dishService.getByDishId(id);
        return Result.success(dishVO);
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类id
     * @return 菜品信息
     */
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> getByCategoryId(@RequestParam Long categoryId) {
        log.info("根据分类id查询菜品:{}", categoryId);
        List<Dish> dishes = dishService.getByCategoryId(categoryId);
        return Result.success(dishes);
    }

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品信息
     * @return 统一结果
     */
    @ApiOperation("修改菜品")
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品信息: {}", dishDTO);
        dishService.update(dishDTO);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 批量删除菜品
     * @param ids 菜品id
     * @return 统一结果
     */
    @ApiOperation("批量删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("根据ids删除菜品: {}", ids);
        dishService.deleteByIds(ids);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 修改菜品状态 起售/停售
     *
     * @param id     菜品id
     * @param status 状态 1:起售 0:停售
     * @return 统一结果
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(Long id, @PathVariable Integer status) {
        log.info("修改菜品状态: id={}, status={}", id, status);
        dishService.startOrStop(id, status);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 抽取清理缓存的方法：根据key的前缀进行清理
     *
     * @param pattern 匹配的key的前缀
     */
    private void cleanCache(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
