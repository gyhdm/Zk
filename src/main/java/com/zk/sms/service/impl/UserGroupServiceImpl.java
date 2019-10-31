package com.zk.sms.service.impl;

import com.zk.sms.common.service.impl.BaseServiceImpl;
import com.zk.sms.dao.UserGroupRepository;
import com.zk.sms.model.UserGroup;
import com.zk.sms.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type User group service.
 *
 * @author guoying
 * @since 2019 -10-31 22:17:48
 */
@Slf4j
@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroup,Integer, UserGroupRepository> implements UserGroupService {
}
