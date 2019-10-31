package com.zk.sms.service.impl;

import com.zk.sms.common.service.impl.BaseServiceImpl;
import com.zk.sms.dao.PermissionRepository;
import com.zk.sms.model.SysPermission;
import com.zk.sms.model.SysRole;
import com.zk.sms.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The type Permission service.
 *
 * @author guoying
 * @since 2019 -10-31 21:26:09
 */
@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<SysPermission, Integer, PermissionRepository> implements PermissionService {

    @Override
    @Cacheable(cacheNames = "MAP_PERMISSION")
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        Map<String, Collection<ConfigAttribute>> result = new HashMap<>();
        List<SysPermission> permissions = repository.findAll();
        for (SysPermission permission : permissions) {
            Collection<ConfigAttribute> collection = new ArrayList<>();
            for (SysRole role : permission.getRoles()) {
                collection.add(new SecurityConfig("ROLE_" + role.getName()));
            }
            result.put(permission.getUrl(), collection);
        }
        return result;
    }
}
