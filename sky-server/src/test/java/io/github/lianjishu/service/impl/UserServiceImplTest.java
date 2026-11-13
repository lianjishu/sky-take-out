package io.github.lianjishu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.lianjishu.dto.UserLoginDTO;
import io.github.lianjishu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userServiceImpl;

    @Test
    void login() throws JsonProcessingException {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setCode("0a3dBOHa1lvbOM0y6GJa1d4Ab22dBOHf");
        userServiceImpl.wxLogin(userLoginDTO);
    }

    //HttpClientUtil.doGet返回resp: {"session_key":"4ObuESMwZHOEV25d1+n2WQ==","openid":"oI1M33cnZzOFq-uKnUH9hkWwhfTg"}
}
