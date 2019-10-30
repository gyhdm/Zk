package com.zk.sms.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 通用实体基类.
 *
 * @author guoying
 * @since 2019 -10-27 15:07:43
 */
@MappedSuperclass
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel implements Serializable {
    /**
     * The Create time.
     */
    @CreatedDate
    private Date createTime;

    /**
     * The Update time.
     */
    @LastModifiedDate
    private Date updateTime;

    /**
     * Gets id.
     *
     * @return the id
     */
    public abstract Object getId();
}
