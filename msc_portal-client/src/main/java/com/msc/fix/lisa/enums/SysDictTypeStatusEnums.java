package com.msc.fix.lisa.enums;


import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/30
 * Time: 13:43
 * Description: No Description
 */
@Getter
public enum SysDictTypeStatusEnums {
    NORMAL("正常",0),
    DEACTIVATE("停用",1),
    DELETE("删除",2);

    private String name;
    private Integer code;
    SysDictTypeStatusEnums(String name,Integer code){
        this.name = name;
        this.code = code;
    }
}
