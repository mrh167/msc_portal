package com.msc.fix.lisa.common.enums;

import lombok.Data;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/10/11
 * Time: 10:29
 * Description: No Description
 */
@Getter
public enum DictTypeStatusEnums {

    TYPE_OPEN("开启",1),
    TYPE_CLOSE("关闭",0);

    private String name;
    private Integer code;
    private DictTypeStatusEnums(String name,Integer code){
        this.name = name;
        this.code = code;
    }
}
