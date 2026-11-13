package io.github.lianjishu.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.lianjishu.constant.JwtClaimsConstant;
import io.github.lianjishu.dto.UserLoginDTO;
import io.github.lianjishu.entity.User;
import io.github.lianjishu.properties.JwtProperties;
import io.github.lianjishu.result.Result;
import io.github.lianjishu.service.UserService;
import io.github.lianjishu.utils.JwtUtil;
import io.github.lianjishu.vo.UserLoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController(value = "userController")
@RequestMapping("/user/user")
@Api(tags = "用户相关接口")
public class UserController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Autowired
    public UserController(UserService userService, JwtProperties jwtProperties) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * C端用户登录
     *
     * @param userLoginDTO 用户登录数据传输对象
     * @return 用户登录结果vo
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) throws JsonProcessingException {
        log.info("微信用户登录：{}", userLoginDTO.getCode());

        //微信登录
        User user = userService.wxLogin(userLoginDTO);

        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String userSecretKey = jwtProperties.getUserSecretKey();
        long userTtl = jwtProperties.getUserTtl();
        String token = JwtUtil.createJWT(userSecretKey, userTtl, claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        log.info("小程序用户登录成功：{}", userLoginVO.toString());
        return Result.success(userLoginVO);
    }
}
