package com.msc.fix.lisa.dto.system.cto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 15:04
 * Description: No Description
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class BookCo {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String id;

    private String title;

    private List<String> authors;

    private String summary;

    private String publish_date;

    private Integer num_reviews;

    private String publisher;
}
