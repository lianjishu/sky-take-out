package io.github.lianjishu.service;

import io.github.lianjishu.dto.SetmealDTO;
import io.github.lianjishu.dto.SetmealPageQueryDTO;
import io.github.lianjishu.entity.Setmeal;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.vo.DishItemVO;
import io.github.lianjishu.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     *
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO queryById(Long id);

    /**
     *
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
