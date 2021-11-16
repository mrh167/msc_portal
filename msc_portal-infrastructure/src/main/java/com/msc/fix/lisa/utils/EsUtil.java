package com.msc.fix.lisa.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 17:31
 * Description: No Description
 */
public class EsUtil {

    /**
     * 通用的装换返回结果 *
     */
    public static  <T> List<T> convertResponse(SearchResponse response, Class<T> clazz) {
        List<T> list = Lists.newArrayList();
        if (response != null && response.getHits() != null) {
            String result = "";
            T e = null;
            Field idField = ReflectionUtils.findField(clazz, "id");
            if (idField != null) {
                ReflectionUtils.makeAccessible(idField);
            }
            for (SearchHit hit : response.getHits()) {
                result = hit.getSourceAsString();
                if (StringUtils.isNotBlank(result)) {
                    e = JSON.parseObject(result, clazz);
                }
                if (e != null) {
                    if (idField != null) {
                        ReflectionUtils.setField(idField, e, hit.getId());
                    }
                    list.add(e);
                }
            }
        }
        return list;
    }

    /**
     * 通用的装换返回结果 *
     */
    public static <T> List<T> convertResponses(SearchResponse response, Class<T> clazz) {
        List<T> list = Lists.newArrayList();
        if (response != null && response.getHits() != null) {
            String result = "";
            T e = null;
            Field idField = ReflectionUtils.findField(clazz, "id");
            if (idField != null) {
                ReflectionUtils.makeAccessible(idField);
            }
            for (SearchHit hit : response.getHits()) {
                result = hit.toString();
                if (StringUtils.isNotBlank(result)) {
                    e = JSON.parseObject(result, clazz);
                }
                if (e != null) {
                    if (idField != null) {
                        ReflectionUtils.setField(idField, e, hit.getId());
                    }
                    list.add(e);
                }
            }
        }
        return list;
    }
}
