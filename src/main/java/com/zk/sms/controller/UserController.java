package com.zk.sms.controller;

import com.zk.sms.common.controller.BaseController;
import com.zk.sms.model.SysUser;
import com.zk.sms.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type User controller.
 *
 * @author guoying
 * @since 2019 -10-30 16:45:49
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController extends BaseController<SysUser, String, UserService> {

}
