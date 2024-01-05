package com.isc.backstage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isc.backstage.entity.Permission;
import com.isc.backstage.entity.Role;
import com.isc.backstage.entity.User;
import com.isc.backstage.setting_enumeration.UserSetting;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUser extends User implements UserDetails {

    private List<Role> roleList;

    private List<Permission> permissionList;

    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user){
        BeanUtils.copyProperties(user, this);
    }

    public LoginUser(User user, List<Permission> permissions) {
        BeanUtils.copyProperties(user, this);
        this.permissionList = permissions;
    }

    public LoginUser(User user, List<Role> roles, List<Permission> permissions) {
        BeanUtils.copyProperties(user, this);
        this.roleList = roles;
        this.permissionList = permissions;
    }

    /**
     * 权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.nonNull(authorities)){
            return authorities;
        }
        return permissionList.stream()
                .filter(permission -> StringUtils.hasText(permission.getName()))
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.getState().equals(UserSetting.Enabled.getCode());
    }
}
