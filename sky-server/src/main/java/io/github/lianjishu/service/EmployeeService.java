package io.github.lianjishu.service;

import io.github.lianjishu.dto.EmployeeLoginDTO;
import io.github.lianjishu.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
