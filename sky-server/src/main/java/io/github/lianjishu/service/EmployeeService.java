package io.github.lianjishu.service;

import io.github.lianjishu.dto.EmployeeDTO;
import io.github.lianjishu.dto.EmployeeLoginDTO;
import io.github.lianjishu.dto.EmployeePageQueryDTO;
import io.github.lianjishu.entity.Employee;
import io.github.lianjishu.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工数据传输对象
     * @return 员工实体对象
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO 员工数据传输对象
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询参数
     * @return 员工分页查询结果
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用、禁用员工账号
     *
     * @param id     员工ID
     * @param status 状态
     */
    void startOrStop(Long id, Integer status);

    /**
     * 根据ID查询回显员工
     *
     * @param id 员工ID
     * @return 员工数据传输对象
     */
    Employee getById(Integer id);

    /**
     * 修改员工信息
     *
     * @param employeeDTO 员工数据传输对象
     */
    void update(EmployeeDTO employeeDTO);
}
