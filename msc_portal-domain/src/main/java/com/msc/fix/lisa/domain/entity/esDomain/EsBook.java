package com.msc.fix.lisa.domain.entity.esDomain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/10
 * Time: 17:40
 * Description: ElasticSearch实体模拟
 */
@Data
public class EsBook {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String id;
    private String title;
    private List<String> authors;
    private String summary;
    private String publish_date;
    private Integer num_reviews;
    private String publisher;

}
