package com.msc.fix.lisa.executor;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.dto.IndexQry;
import com.msc.fix.lisa.dto.system.cto.BookCo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/15
 * Time: 15:11
 * Description: No Description
 */
@Slf4j
@Command
public class IndexQryExe implements CommandExecutorI<SingleResponse<List<BookCo>>, IndexQry> {


    @Autowired
    private Client client;

    @Value("${es.bookIndex}")
    private String bookIndex;

    @Value("${es.bookType}")
    private String bookType;

    /**
     *进行ES查询结果
     */
    private SearchResponse requestGet(String queryName, SearchRequestBuilder requestBuilder){
        log.info(queryName + " 构建的查询: " + requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();
        log.info(queryName + "搜索结果: " + searchResponse.toString());
        return searchResponse;
    }


    @Override
    public SingleResponse<List<BookCo>> execute(IndexQry cmd) {

        /**
         * 匹配查询并分析索引文本返回最终构造查询结果
         */
        MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(cmd.getIndexQry());

        // 构建搜索请求
        SearchRequestBuilder requestBuilder = client.prepareSearch(bookIndex).setTypes(bookType).setQuery(queryBuilder);
        // 返回搜索响应的结果
        SearchResponse searchResponse = requestGet("multiBatch", requestBuilder);

        List<String> list = Arrays.asList(searchResponse.toString());
        System.out.println(list.toString());
        return null;
    }
}
