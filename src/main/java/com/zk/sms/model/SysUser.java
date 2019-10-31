package com.zk.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.sms.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * The type Sys user.
 *
 * @author guoying
 * @since 2019 -10-27 15:51:23
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@GenericGenerator(name = "uuid2",strategy = "org.hibernate.id.UUIDGenerator")
public class SysUser extends BaseModel {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 4226233538277853440L;

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    private String id;

    /**
     * 用户名
     */
    @Column(unique = true)
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 客户群组id.
     */
    private Integer groupId;

    /**
     * 客户经理id.
     */
    private String managerId;

    /**
     * 业务员id.
     */
    private String salesId;

    /**
     * 财务id.
     */
    private String cashierId;

    /**
     * 名称
     */
    private String name;

    /**
     * 联系人.
     */
    private String contact;
    /**
     * 地址
     */
    private String address;

    /**
     * 电话.
     */
    private String phone;

    /**
     * 手机.
     */
    private String mobile;

    /**
     * The Email.
     */
    private String email;

    /**
     * 扩展号.
     */
    private String extendCode;

    /**
     * 分包条数.
     */
    private Integer blockNum;

    /**
     * 余额.
     */
    private Double balance;

    /**
     * 短信单价.
     */
    private Integer sms;

    /**
     * 彩信单价.
     */
    private Integer mms;

    /**
     * 小区短信单价.
     */
    private Integer lbsSms;

    /**
     * 小区彩信单价.
     */
    private Integer lbsMms;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 是否可用.
     */
    private Boolean available = Boolean.TRUE;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roleList;
}
