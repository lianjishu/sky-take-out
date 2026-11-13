package io.github.lianjishu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.lianjishu.constant.MessageConstant;
import io.github.lianjishu.dto.UserLoginDTO;
import io.github.lianjishu.entity.User;
import io.github.lianjishu.exception.LoginFailedException;
import io.github.lianjishu.mapper.UserMapper;
import io.github.lianjishu.properties.WeChatProperties;
import io.github.lianjishu.service.UserService;
import io.github.lianjishu.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    private final WeChatProperties weChatProperties;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(WeChatProperties weChatProperties, UserMapper userMapper) {
        this.weChatProperties = weChatProperties;
        this.userMapper = userMapper;
    }

    /**
     * C端用户登录
     * 后端获得前端传来的临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程
     * <code>GET</code>
     * <code>https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JS_CODE&grant_type=GRANT_TYPE</code>
     *
     * @param userLoginDTO 用户登录数据传输对象
     * @return 用户登录结果vo
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //使用HttpClient调用微信接口，获取openid
        String openid = getOpenid(userLoginDTO.getCode());

        //判断openid是否为空，如果为空表示登录失败，抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);

        //如果是新用户，自动完成注册
        if (user == null) {
            log.info("用户不存在，需要创建");
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        //返回这个用户对象
        return user;
    }

    /**
     * 使用HttpClient调用微信`jscode2session`接口，获取微信用户的openid
     *
     * @param code 授权码
     * @return 微信用户openid
     */
    private String getOpenid(String code) {

        Map<String, String> queryStringMap = new HashMap<>();
        queryStringMap.put("appid", weChatProperties.getAppid());
        queryStringMap.put("secret", weChatProperties.getSecret());
        queryStringMap.put("js_code", code);
        queryStringMap.put("grant_type", "authorization_code");
        String responseJson = HttpClientUtil.doGet(WX_LOGIN, queryStringMap);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        String openid = jsonObject.getString("openid");

        return openid;
    }
}