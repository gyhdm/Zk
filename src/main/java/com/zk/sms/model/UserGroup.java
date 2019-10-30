package com.zk.sms.model;

import com.zk.sms.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 客户群组.
 *
 * @author guoying
 * @since 2019 -10-27 17:58:21
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class UserGroup extends BaseModel {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 群组名称.
     */
    private String name;

    /**
     * 归属用户ID.
     */
    private String userId;

    /**
     * 描述.
     */
    private String description;

    /**
     * 是否可用.
     */
    private Boolean available = Boolean.TRUE;
}
