package com.zk.sms.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 用户登录实体.
 *
 * @author guoying
 * @since 2019 -10-28 22:38:16
 */
@Getter
@Setter
public class LoginUser implements UserDetails {

    private String id;

    private String username;

    private String password;

    private boolean available;


    private static final long serialVersionUID = -3112559023697097576L;

    /**
     * The Authorities.
     */
    private Collection<? extends GrantedAuthority> authorities;

    public LoginUser() {
    }

    public LoginUser(String id, String username, String password, Boolean available) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.available = available;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return available;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
