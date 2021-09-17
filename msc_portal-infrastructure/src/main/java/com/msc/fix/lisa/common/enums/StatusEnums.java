package com.msc.fix.lisa.common.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/9
 * Time: 19:54
 * Description: No Description
 */

@Getter
public enum StatusEnums {
    USER_OPEN("开启",1),
    USER_CLOSE("关闭",0);

    private String name;
    private Integer code;
    private StatusEnums(String name,Integer code){
        this.name = name;
        this.code = code;
    }


}
