package io.github.lianjishu.mapper;

import io.github.lianjishu.dto.EmployeePageQueryDTO;
import io.github.lianjishu.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username 用户名
     * @return 员工对象
     */
    @Select("select * from employee where username = #{username} limit 1")
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee 员工对象
     */
    @Insert("insert into employee(id, name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values" +
            "(null, #{name}, #{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})"
    )
    void insert(Employee employee);

    /**
     * 根据条件查询员工分页列表
     *
     * @param employeePageQueryDTO 员工分页查询条件
     * @return 员工分页列表
     */
    List<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据id动态更新员工信息
     *
     * @param employee 员工对象
     */
    void update(Employee employee);

    /**
     * 根据id查询员工信息
     *
     * @param id 员工id
     * @return 员工对象
     */
    @Select("select id, name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user " +
            "from employee " +
            "where id = #{id}")
    Employee selectById(Integer id);
}
