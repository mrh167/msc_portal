package com.msc.fix.lisa.repository.db.dbdo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.msc.fix.lisa.domain.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 16:15
 * Description: No Description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserDo extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name ="username")
    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private String username;

    /**
     * 密码
     */
    @Column(name ="password")
    private String password;

    /**
     * 账号
     */
    @Column(name ="account")
    private String account;
    /**
     * 昵称
     */
    @Column(name ="nick_name")
    private String nickName;

    /**
     * 手机号
     */
    @Column(name ="phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name ="email")
    private String email;

    /**
     * 状态  0：禁用   1：正常
     */
    @Column(name ="status")
    private Integer status;

    /**
     * 用户编码
     */
    @Column(name = "user_code")
    private String userCode;
}
