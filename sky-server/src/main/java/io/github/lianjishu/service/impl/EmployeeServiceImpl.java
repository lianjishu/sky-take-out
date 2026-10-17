package io.github.lianjishu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.github.lianjishu.constant.MessageConstant;
import io.github.lianjishu.constant.PasswordConstant;
import io.github.lianjishu.constant.StatusConstant;
import io.github.lianjishu.context.BaseContext;
import io.github.lianjishu.dto.EmployeeDTO;
import io.github.lianjishu.dto.EmployeeLoginDTO;
import io.github.lianjishu.dto.EmployeePageQueryDTO;
import io.github.lianjishu.entity.Employee;
import io.github.lianjishu.exception.AccountLockedException;
import io.github.lianjishu.exception.AccountNotFoundException;
import io.github.lianjishu.exception.PasswordErrorException;
import io.github.lianjishu.mapper.EmployeeMapper;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录数据传输对象
     * @return 员工实体对象
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 用户输入的密码进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 员工数据传输对象
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //属性拷贝 employeeDTO -> employee
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置新员工的默认状态(启用)
        employee.setStatus(StatusConstant.ENABLE);
        //设置新员工的默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置当前记录的创建时间和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //设置当前记录的创建人ID和修改人ID
        employee.setUpdateUser(BaseContext.getCurrentId());
        employee.setCreateUser(BaseContext.getCurrentId());

        //调用mapper
        employeeMapper.insert(employee);
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询参数
     * @return 员工分页查询结果
     */
    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        List<Employee> records = employeeMapper.pageQuery(employeePageQueryDTO);
        Page<Employee> p = (Page<Employee>) records;//Page是List的实现类

        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 启用、禁用员工账号
     *
     * @param id     员工ID
     * @param status 状态
     */
    @Override
    public void startOrStop(Long id, Integer status) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据ID查询回显员工
     *
     * @param id 员工ID
     * @return 员工数据传输对象
     */
    @Override
    public Employee getById(Integer id) {

        return employeeMapper.selectById(id);
    }

    /**
     * 修改员工信息
     *
     * @param employeeDTO 员工数据传输对象
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }
}
