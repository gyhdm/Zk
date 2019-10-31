package com.zk.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.sms.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * The type Sys permission.
 *
 * @author guoying
 * @since 2019 -10-27 17:21:37
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPermission extends BaseModel {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The Parent id.
     */
    private Integer parentId;

    /**
     * The Parent ids.
     */
    private String parentIds;

    /**
     * 名称.
     */
    private String name;

    /**
     * The Url.
     */
    private String url;

    /**
     * 权限值.
     */
    private String permission;

    /**
     * The Icon.
     */
    private String icon;

    /**
     * The Type.
     */
    @Column(columnDefinition = "enum('menu','button')")
    private String type;

    /**
     * 描述.
     */
    private String description;

    /**
     * 是否可用.
     */
    private Boolean available = Boolean.TRUE;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "permissionId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roles;
}
