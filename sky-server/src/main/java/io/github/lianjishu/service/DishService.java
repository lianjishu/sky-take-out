package io.github.lianjishu.service;

import io.github.lianjishu.dto.DishDTO;
import io.github.lianjishu.dto.DishPageQueryDTO;
import io.github.lianjishu.entity.Dish;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品
     *
     * @param dish 菜品信息
     */
    void save(DishDTO dish);

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO 菜品分页查询参数
     * @return 菜品分页查询结果
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品信息
     */
    void update(DishDTO dishDTO);

    /**
     * 根据id查询菜品
     *
     * @param id 菜品ID
     * @return 菜品信息
     */
    DishVO getByDishId(Long id);

    /**
     * 根据菜品ID删除菜品
     *
     * @param ids 菜品ID
     */
    void deleteByIds(List<Long> ids);

    /**
     *
     * @param categoryId
     */
    List<Dish> getByCategoryId(Long categoryId);

    /**
     *
     * @param status
     */
    void startOrStop(Long id, Integer status);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

}
