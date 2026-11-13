package io.github.lianjishu.mapper;

import io.github.lianjishu.annotation.AutoFill;
import io.github.lianjishu.dto.DishPageQueryDTO;
import io.github.lianjishu.entity.Dish;
import io.github.lianjishu.enumeration.OperationType;
import io.github.lianjishu.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId 分类id
     * @return 菜品数量
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     *
     * @param dish 菜品信息
     */
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into dish (name, category_id, price, description, image) values (#{name}, #{categoryId}, #{price}, #{description}, #{image})")
    void insert(Dish dish);

    /**
     * 根据分页查询条件查询菜品列表
     *
     * @param dishPageQueryDTO 菜品分页查询参数
     * @return 菜品列表
     */
    List<DishVO> selectPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     *
     * @param dishId 菜品id
     * @return 菜品信息
     */
    DishVO selectById(Long dishId);

    /**
     * 根据id更新菜品信息
     *
     * @param dish 菜品信息
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     *
     * @param categoryId
     */
    List<Dish> selectByCategoryId(Long categoryId);

    /**
     * 根据id更新菜品状态
     *
     * @param id     菜品id
     * @param status 菜品状态
     */
    @Update("update dish set status=#{status} where id=#{id}")
    void updateStatus(Long id, Integer status);

    /**
     * 根据菜品信息查询菜品列表
     *
     * @param dish 菜品信息
     * @return 菜品列表
     */
    List<Dish> list(Dish dish);
}
