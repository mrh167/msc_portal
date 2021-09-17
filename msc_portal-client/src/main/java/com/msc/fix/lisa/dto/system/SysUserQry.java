package com.msc.fix.lisa.dto.system;

import com.msc.fix.lisa.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:22
 * Description: No Description
 */
@Data
public class SysUserQry extends PageRequest {

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

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
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "用户token")
    private String token;
}
