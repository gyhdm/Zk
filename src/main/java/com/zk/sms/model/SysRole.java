package com.zk.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.sms.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * The type Sys role.
 *
 * @author guoying
 * @since 2019 -10-27 16:55:33
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseModel
{
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 5965045357487851688L;

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名.
     */
    private String name;

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
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "userId")})
    private List<SysUser> sysUsers;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<SysPermission> permissions;
}


