package com.msc.fix.lisa.dto.system.cto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/29
 * Time: 11:08
 * Description: No Description
 */
@Data
@Api(tags = "登录",produces = "数据字典")
public class SysDictDataCo {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 字典类型id
     */
    @ApiModelProperty(value = "字典类型id")
    private Long typeId;
    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private String value;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    private Integer status;
}
