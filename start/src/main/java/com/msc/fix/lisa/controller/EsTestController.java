//package com.msc.fix.lisa.controller;
//
//import com.alibaba.cola.dto.SingleResponse;
//import com.msc.fix.lisa.common.BusinessException;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.admin.indices.alias.Alias;
//import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
//import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.*;
//import org.elasticsearch.cluster.metadata.AliasMetaData;
//import org.elasticsearch.common.collect.ImmutableOpenMap;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.rest.RestStatus;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.*;
//
//
///**
// * Created with IntelliJ IDEA.
// * User: ext.maruihua1
// * Date: 2021/11/3
// * Time: 15:15
// * Description: No Description
// */
//@Slf4j
//@Api(tags = "RestController", hidden = true)
//@RestController
//@RequestMapping(value = "/api/testEsData")
//public class EsTestController {
//
//    @Autowired
//    private RestHighLevelClient client;
//
//
//    /**
//     * 创建ES索引库
//     */
//    @GetMapping("/putTestAddIndex")
//    public IndexResponse putTestAddIndex() {
//
//        String index = "test1";
//        String type = "_doc";
//        // 唯一编号
//        String id = "1";
//        IndexRequest request = new IndexRequest(index, type, id);
//
//        String jsonString = "{" + "\"uid\":\"1234\"," + "\"phone\":\"12345678909\"," + "\"msgcode\":\"1\"," + "\"sendtime\":\"2019-03-14 01:57:04\","
//                + "\"message\":\"xuwujing study Elasticsearch\"" + "}";
//        request.source(jsonString, XContentType.JSON);
//        IndexResponse indexResponse = null;
//        try {
//            indexResponse = client.index(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return indexResponse;
//
//    }
//
//
//    @GetMapping("/jsonMapAddIndex")
//    public IndexResponse jsonMapAddIndex() {
//        String index = "testjson2";
//
//        String type = "_doc";
//        // 唯一编码
//        String id = "3";
//
//        IndexRequest request = new IndexRequest(index, type, id);
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("uid", 46456651616545L);
//        jsonMap.put("phone", 6165165161L);
//        jsonMap.put("msgcode", 4);
//        jsonMap.put("sendtime", new Date());
//        jsonMap.put("message", "dfvbvkjhdfnvkjsdlkcmnsadlk5416541651");
//        request.source(jsonMap);
//        IndexResponse indexResponse = null;
//        try {
//            indexResponse = client.index(request, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return indexResponse;
//
//    }
//
//
//    @GetMapping("/xContentBuilder")
//    public IndexResponse xContentBuilder() {
//        String index = "xcontent";
//        String type = "_doc";
//        String id = "1";
//
//        IndexRequest indexRequest = new IndexRequest(index, type, id);
//        // 创建json构建器
//        XContentBuilder builder;
//        IndexResponse indexResponse = null;
//        try {
//            builder = XContentFactory.jsonBuilder();
//            builder.startObject();
//            {
//                builder.field("uid", 34123423);
//                builder.field("username", "张三");
//                builder.field("password", "123456");
//                builder.field("sex", "男");
//                builder.field("love", "足球");
//            }
//            builder.endObject();
//            indexRequest.source(builder);
//            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return indexResponse;
//    }
//
//    @GetMapping("/createIndex")
//    public SingleResponse<Boolean> createIndex() {
//        String type = "_doc";
//        String index = "createindex";
//        // setting 的值
//        HashMap<String, Object> setmapping = new HashMap<>();
//        // shads分区数
//        setmapping.put("number_of_shards", 10);
//        // 副本数replicas
//        setmapping.put("number_of_replicas", 1);
//        // 缓存刷新时间
//        setmapping.put("refresh_interval", "5s");
//        // 设置类型
//        Map<String, Object> keyword = new HashMap<>();
//        keyword.put("type", "keyword");
//        Map<String, Object> lon = new HashMap<>();
//        // 设置类型
//        lon.put("type", "long");
//
//        Map<String, Object> date = new HashMap<>();
//        date.put("type", "date");
//        date.put("format", "yyyy-MM-dd HH:mm:ss");
//
//        Map<String, Object> jsonMap2 = new HashMap<>();
//        Map<String, Object> properties = new HashMap<>();
//        //设置字段message信息
//        properties.put("uid", lon);
//        properties.put("phone", lon);
//        properties.put("msgcode", lon);
//        properties.put("message", keyword);
//        properties.put("sendtime", date);
//        Map<String, Object> mapping = new HashMap<>();
//        mapping.put("properties", properties);
//        jsonMap2.put(type, mapping);
//
//
//        try {
//            GetIndexRequest getRequest = new GetIndexRequest();
//            getRequest.indices(index);
//            getRequest.local(false);
//            getRequest.humanReadable(true);
//            boolean exists2 = client.indices().exists(getRequest, RequestOptions.DEFAULT);
//            //如果存在就不创建了
//            if (exists2) {
//                throw new BusinessException(index + "索引库已经存在!");
//            }
//            // 开始创建库
//            CreateIndexRequest request = new CreateIndexRequest(index);
//            CreateIndexResponse createIndexResponse;
//            // 加载数据类型
//            request.settings(setmapping);
//            //设置mapping参数
//            request.mapping(type, jsonMap2);
//            //设置别名
//            request.alias(new Alias("pancm_alias"));
//            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//            boolean falg = createIndexResponse.isAcknowledged();
//            if (falg) {
//                SingleResponse.of("创建索引库:" + index + "成功！");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return SingleResponse.of(Boolean.FALSE);
//    }
//
//
//    @PostMapping("/findAllIndex")
//    public SingleResponse<Map<String, Set<AliasMetaData>>> findAllIndex() {
//        GetAliasesRequest getAliasesRequest = new GetAliasesRequest();
//        // 构建获取所有索引的请求
//        GetAliasesResponse getAliasesResponse = null;
//        try {
//            getAliasesResponse = client.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
//            Map<String, Set<AliasMetaData>> aliases = getAliasesResponse.getAliases();
//            aliases.forEach((index, setAli) -> {
//                for (AliasMetaData key : setAli) {
//                    System.out.println(index + "========" + key);
//                }
//            });
//
//            return SingleResponse.of(aliases);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @PostMapping("/findAllIndex2")
//    public SingleResponse<List<String>> findAllIndex2() {
//        try {
//            // 构建请求,注意*的写法
//            GetIndexRequest getIndexRequest = new GetIndexRequest();
//            getIndexRequest.indices("*");
//            // 构建获取所有索引的请求
//            GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
//            // 获取所有索引
//            String[] indices = getIndexResponse.getIndices();
//            List<String> asList = Arrays.asList(indices);
//            return SingleResponse.of(new ArrayList<>(asList));
//        } catch (Exception e) {
//            throw new BusinessException("获取所有索引失败");
//        }
//    }
//
//    @GetMapping("/searchAll")
//    private void allSearch() throws IOException {
//        SearchRequest searchRequestAll = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchRequestAll.source(searchSourceBuilder);
//        // 同步查询
//        SearchResponse searchResponseAll = client.search(searchRequestAll, RequestOptions.DEFAULT);
//        System.out.println("所有查询总数:" + searchResponseAll.getHits());
//    }
//
//    @PostMapping("/updateIndex")
//    public SingleResponse updateIndex() {
//        String type = "_doc";
//        String index = "test1";
//        String id = "1";
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.id(id);
//        updateRequest.index(index);
//        updateRequest.type(type);
//        // 继续使用map
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("uid", 12345);
//        jsonMap.put("phone", 98765432111L);
//        jsonMap.put("msgcode", 2);
//        jsonMap.put("sendtime", "2019-03-14 01:57:04");
//        jsonMap.put("message", "xuwujing study Elasticsearch");
//        updateRequest.doc(jsonMap);
//        // update方法表示如果数据不存在,那么就新增一条
//        updateRequest.docAsUpsert(true);
//        try {
//            client.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return SingleResponse.of("更新成功!!");
//    }
//
//
//}
