package com.msc.fix.lisa.domain.common.enums;

import lombok.Getter;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/10
 * Time: 17:42
 * Description: No Description
 */
@Getter
public class Constants {

    public static String ID = "id";
    public static String TITLE = "title";
    public static String AUTHORS = "authors";
    public static String SUMMARY = "summary";
    public static String PUBLISHDATE = "publish_date";
    public static String PUBLISHER = "publisher";
    public static String NUM_REVIEWS = "num_reviews";
    public static String TITLE_KEYWORD = "title.keyword";
    public static String PUBLISHER_KEYWORD = "publisher.keyword";


    // 过滤要返回的字段
    public static String[] fetchFieldsTSPD = {ID, TITLE, SUMMARY, PUBLISHDATE};
    public static String[] fetchFieldsTA = {ID, TITLE, AUTHORS};
    public static String[] fetchFieldsSA = {ID, SUMMARY, AUTHORS};
    public static String[] fetchFieldsTSA = {ID, TITLE, SUMMARY, AUTHORS};
    public static String[] fetchFieldsTPPD = {ID, TITLE, PUBLISHER, PUBLISHDATE};
    public static String[] fetchFieldsTSPN = {ID, TITLE, SUMMARY, PUBLISHER, NUM_REVIEWS};


    // 高亮
    public static HighlightBuilder highlightS = new HighlightBuilder().field(SUMMARY);

}
