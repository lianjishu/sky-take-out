package io.github.lianjishu.mapper;

import io.github.lianjishu.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 插入套餐菜品信息
     * <p>
     * 注意：setmeal_dish 表没有 create_time/update_time/create_user/update_user 字段，
     * 因此不能标注 @AutoFill，公共字段自动填充在此不适用。
     *
     * @param setmealDishList 套餐菜品信息
     */
    void insert(List<SetmealDish> setmealDishList);

    /**
     *
     * @param id
     * @return
     */
    List<SetmealDish> selectBySetmealId(Long id);

    /**
     *
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);
}
