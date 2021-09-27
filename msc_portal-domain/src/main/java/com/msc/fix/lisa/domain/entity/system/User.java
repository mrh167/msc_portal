package com.msc.fix.lisa.domain.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.msc.fix.lisa.domain.common.utils.CustomAuthorityDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/27
 * Time: 17:33
 * Description: No Description
 */
@Slf4j
@Data
public class User implements UserDetails {



    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    @Getter(AccessLevel.NONE)
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private Integer enabled;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<SysUserRole> roleList;

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities =
                roleList.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleCode()))
                        .collect(Collectors.toList());
        log.info("auth ==>{}",authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
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
