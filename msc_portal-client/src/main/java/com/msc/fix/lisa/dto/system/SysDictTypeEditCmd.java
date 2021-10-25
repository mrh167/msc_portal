package com.msc.fix.lisa.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.msc.fix.lisa.base.AbstractCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/10/18
 * Time: 19:08
 * Description: No Description
 */
@Data
public class SysDictTypeEditCmd extends AbstractCommand {

    /**
     * 主键
     */
    @JSONField(serializeUsing= ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

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
