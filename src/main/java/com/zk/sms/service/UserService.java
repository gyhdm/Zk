package com.zk.sms.service;

import com.zk.sms.common.service.BaseService;
import com.zk.sms.model.SysUser;

/**
 * The interface User service.
 *
 * @author guoying
 * @since 2019 -10-28 22:22:00
 */
public interface UserService extends BaseService<SysUser,String> {

    /**
     * Find by username sys user.
     *
     * @param username the username
     * @return the sys user
     * @author guoying
     * @since 2019 /10/28
     */
    SysUser findByUsername(String username);

    /**
     * Login string.
     *
     * @param username the username
     * @param password the password
     * @return the string
     * @author guoying
     * @since 2019 /10/30
     */
    String login(String username, String password);
}
