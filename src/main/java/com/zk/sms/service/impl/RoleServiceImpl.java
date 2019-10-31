package com.zk.sms.service.impl;

import com.zk.sms.common.service.impl.BaseServiceImpl;
import com.zk.sms.dao.RoleRepository;
import com.zk.sms.model.SysRole;
import com.zk.sms.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Role service.
 *
 * @author guoying
 * @since 2019 -10-31 22:14:54
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole,Integer, RoleRepository> implements RoleService {
}
