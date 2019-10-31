package com.zk.sms.service;

import com.zk.sms.common.service.BaseService;
import com.zk.sms.model.SysPermission;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * The interface Permission service.
 *
 * @author guoying
 * @since 2019 -10-31 21:25:27
 */
public interface PermissionService extends BaseService<SysPermission,Integer> {

    /**
     * Gets permission map.
     *
     * @return the permission map
     */
    Map<String, Collection<ConfigAttribute>> getPermissionMap();
}
