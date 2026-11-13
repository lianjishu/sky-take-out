package io.github.lianjishu.service;

import io.github.lianjishu.dto.UserLoginDTO;
import io.github.lianjishu.entity.User;

public interface UserService {

    /**
     * C端用户登录
     *
     * @param userLoginDTO 用户登录数据传输对象
     * @return 用户登录结果vo
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
