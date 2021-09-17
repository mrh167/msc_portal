package com.msc.fix.lisa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataKey {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String account;
}
