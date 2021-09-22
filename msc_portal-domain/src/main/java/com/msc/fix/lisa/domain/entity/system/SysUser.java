package com.msc.fix.lisa.domain.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author 马瑞华
 * @since 2021-08-31
 */
@Data
@Table(name = "sys_user")
@Slf4j
public class SysUser implements Serializable,UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(hidden = true)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private String createUser;

    /**
     * 修改人
     */
    @ApiModelProperty(hidden = true)
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private String updateUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private Date updateTime;

    /**
     * 逻辑删除字段
     */
    @ApiModelProperty(hidden = true)
//    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private Integer yn;

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

    /**
     * 用户编码
     */
    private String userCode;
}
