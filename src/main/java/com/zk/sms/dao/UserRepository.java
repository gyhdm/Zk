package com.zk.sms.dao;

import com.zk.sms.model.LoginUser;
import com.zk.sms.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 *
 * @author guoying
 * @since 2019 -10-28 22:20:22
 */
public interface UserRepository extends JpaRepository<SysUser,String> {

    /**
     * Find by username sys user.
     *
     * @param username the username
     * @return the sys user
     * @author guoying
     * @since 2019 /10/28
     */
    SysUser findByUsername(String username);
}
