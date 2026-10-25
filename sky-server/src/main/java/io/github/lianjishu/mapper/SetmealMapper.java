package io.github.lianjishu.mapper;

import io.github.lianjishu.annotation.AutoFill;
import io.github.lianjishu.dto.SetmealPageQueryDTO;
import io.github.lianjishu.entity.Setmeal;
import io.github.lianjishu.enumeration.OperationType;
import io.github.lianjishu.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{id}")
    Integer countByCategoryId(Long id);

    /**
     * 分页查询套餐列表
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 套餐视图对象列表（含分类名称）
     */
    List<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     *
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into setmeal(id, category_id, name, price, status, description, image, create_time, update_time, create_user, update_user) " +
            "values (#{id}, #{categoryId}, #{name}, #{price}, #{status}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Setmeal setmeal);

    /**
     * 根据id查询套餐
     *
     * @param id 套餐id
     * @return 套餐视图对象（含分类名称）
     */
    SetmealVO selectById(Long id);

    /**
     * 根据id动态更新套餐信息
     *
     * @param setmeal 套餐信息
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
}
