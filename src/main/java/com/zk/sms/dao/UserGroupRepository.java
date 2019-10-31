package com.zk.sms.dao;

import com.zk.sms.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User group repository.
 *
 * @author guoying
 * @since 2019 -10-31 22:16:28
 */
public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
}
