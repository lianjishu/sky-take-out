package io.github.lianjishu.controller.admin;

import io.github.lianjishu.constant.JwtClaimsConstant;
import io.github.lianjishu.dto.EmployeeDTO;
import io.github.lianjishu.dto.EmployeeLoginDTO;
import io.github.lianjishu.dto.EmployeePageQueryDTO;
import io.github.lianjishu.entity.Employee;
import io.github.lianjishu.properties.JwtProperties;
import io.github.lianjishu.result.PageResult;
import io.github.lianjishu.result.Result;
import io.github.lianjishu.service.EmployeeService;
import io.github.lianjishu.utils.JwtUtil;
import io.github.lianjishu.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return 统一结果
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 员工信息
     * @return 统一结果
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result<EmployeeDTO> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询参数
     * @return 员工分页查询结果
     */
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用、禁用员工账号
     *
     * @param id     员工ID
     * @param status 状态
     * @return 统一结果
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用、禁用员工账号")
    public Result startOrStop(Long id, @PathVariable Integer status) {
        employeeService.startOrStop(id, status);

        return Result.success();
    }

    /**
     * 根据ID查询回显员工
     *
     * @param id 员工ID
     * @return 员工信息
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询回显员工")
    public Result<Employee> getById(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);

        return Result.success(employee);
    }

    /**
     * 修改员工信息
     *
     * @param employeeDTO 员工信息
     * @return 统一结果
     */
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
