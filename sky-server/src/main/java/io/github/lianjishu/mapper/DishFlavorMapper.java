package io.github.lianjishu.mapper;

import io.github.lianjishu.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 插入菜品口味信息
     *
     * @param dishFlavors 菜品口味信息列表
     */
    void insertBatch(List<DishFlavor> dishFlavors);

    /**
     * 根据菜品id删除对应的口味数据
     *
     * @param dishIds 菜品id
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * 根据id查询菜品口味信息
     *
     * @param dishId 菜品ID
     */
    List<DishFlavor> selectById(Long dishId);
}
