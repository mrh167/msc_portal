package com.msc.fix.lisa.dto.system;

import com.msc.fix.lisa.base.AbstractCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/27
 * Time: 10:45
 * Description: No Description
 */
@Data
public class UpdateUserCmd extends AbstractCommand {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Integer id;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 账号
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String phone;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态: 0禁用, 1启用")
    private Integer status;


    @ApiModelProperty(value = "用户角色")
    private Integer adminType;
}
