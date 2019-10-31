package com.zk.sms.dao;

import com.zk.sms.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Role repository.
 *
 * @author guoying
 * @since 2019 -10-31 22:13:39
 */
public interface RoleRepository extends JpaRepository<SysRole,Integer> {
}
