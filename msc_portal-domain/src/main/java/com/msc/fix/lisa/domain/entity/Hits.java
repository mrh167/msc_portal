package com.msc.fix.lisa.domain.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 17:47
 * Description: No Description
 */
@Data
public class Hits {

    private String _index;

    private String _type;

    private String _score;

    private Object _source;
}
