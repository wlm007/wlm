package com.wlm.wlm.dto;

import com.wlm.wlm.model.SysRole;
import com.wlm.wlm.model.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wuliming
 * @date 2021/9/18 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer age;

    private String username;

    private String password;

    private String email;

    private String deptNo;

    private String roleNo;

    private List<SysRole> roleList;

    public SysUserDto(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.age = user.getAge();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.deptNo = user.getDeptNo();
        this.roleNo = user.getRoleNo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            authorities.add(new SimpleGrantedAuthority(sysRole.getRoleName()));
        }
        return authorities;
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
        return true;
    }
}
