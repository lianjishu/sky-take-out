package io.github.lianjishu.service;

import io.github.lianjishu.dto.SetmealDTO;
import io.github.lianjishu.dto.SetmealPageQueryDTO;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.vo.SetmealVO;

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
}
