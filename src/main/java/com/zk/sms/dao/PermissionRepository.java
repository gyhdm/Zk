package com.zk.sms.dao;

import com.zk.sms.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Permission repository.
 *
 * @author guoying
 * @since 2019 -10-31 21:24:20
 */
public interface PermissionRepository extends JpaRepository<SysPermission,Integer> {
}
