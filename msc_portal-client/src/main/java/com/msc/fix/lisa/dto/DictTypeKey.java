package com.msc.fix.lisa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 14:38
 * Description: No Description
 */
@Data
public class DictTypeKey {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;

}
