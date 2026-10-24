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
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.LockInfo;
import java.util.List;

@Slf4j
@Api(tags = "菜品管理")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
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
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getByDishId(@PathVariable Long id) {
        log.info("根据id查询菜品: {}", id);
        DishVO dishVO = dishService.getByDishId(id);
        return Result.success(dishVO);
    }

    /**
     *
     * @param categoryId
     * @return
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

        return Result.success();
    }

    /**
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("根据ids删除菜品: {}", ids);
        dishService.deleteByIds(ids);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(Long id, @PathVariable Integer status) {
        log.info("修改菜品状态: id={}, status={}", id, status);
        dishService.startOrStop(id, status);
        return Result.success();
    }
}
