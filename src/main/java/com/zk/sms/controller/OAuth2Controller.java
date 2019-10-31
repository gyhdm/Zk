package com.zk.sms.controller;

import com.zk.sms.common.model.ResultBody;
import com.zk.sms.model.LoginUser;
import com.zk.sms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type O auth 2 controller.
 *
 * @author guoying
 * @since 2019 -10-30 15:42:36
 */
@Slf4j
@Api(tags = "权限接口")
@RestController
public class OAuth2Controller {

    /**
     * The User service.
     */
    @Autowired
    private UserService userService;

    /**
     * Login result body.
     *
     * @param loginUser the login user
     * @return the result body
     * @author guoying
     * @since 2019 /10/30
     */
    @ApiOperation(value = "登录", notes = "用户名，密码登录.格式:{\"username\": \"admin\",    \"password\": \"admin\"}")
    @PostMapping(value = "auth/login")
    public ResultBody<String> login(@RequestBody LoginUser loginUser) {
        String token = userService.login(loginUser.getUsername(),loginUser.getPassword());
        return ResultBody.success("登录成功", token);
    }
}
