package io.github.lianjishu.controller.admin;

import io.github.lianjishu.dto.SetmealDTO;
import io.github.lianjishu.dto.SetmealPageQueryDTO;
import io.github.lianjishu.entity.Setmeal;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.result.Result;
import io.github.lianjishu.service.SetmealService;
import io.github.lianjishu.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {

    private final SetmealService setmealService;

    @Autowired
    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    /**
     * 分页查询套餐列表
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 查询结果
     */
    @ApiOperation("分页查询套餐列表")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐列表，参数：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        log.info("分页查询套餐列表，结果：{}", pageResult);

        return Result.success(pageResult);
    }

    /**
     *
     * @param setmealDTO
     * @return
     */
    @ApiOperation("新增套餐")
    @PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐: {}", setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> queryById(@PathVariable Long id) {
        log.info("根据id查询套餐，参数：{}", id);
        SetmealVO setmeal = setmealService.queryById(id);
        log.info("根据id查询套餐，结果：{}", setmeal);
        return Result.success(setmeal);
    }

    /**
     *
     * @param setmealDTO
     * @return
     */
    @ApiOperation("修改套餐")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐 setmealDTO: {}", setmealDTO);
        setmealService.update(setmealDTO);

        return Result.success();
    }
}
