package com.msc.fix.lisa.testUtils;

import com.alibaba.cola.dto.SingleResponse;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/3
 * Time: 15:46
 * Description: No Description
 */
public class TestUtils {
    public static final String INDEX_NAME = "study-index";

    public static final String CREATE_INDEX = "{\n" +
            "    \"properties\": {\n" +
            "      \"id\":{\n" +
            "        \"type\":\"integer\"\n" +
            "      },\n" +
            "      \"userId\":{\n" +
            "        \"type\":\"integer\"\n" +
            "      },\n" +
            "      \"name\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"search_analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"url\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"index\": true,\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"search_analyzer\": \"ik_smart\"\n" +
            "      }\n" +
            "    }\n" +
            "  }";

    public static RestHighLevelClient client = null;

    @PostConstruct
    public void init() {
        try {
//            if (client != null) {
//                client.close();
//            }
//            client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
//            if (this.indexExist(INDEX_NAME)) {
//                return;
//            }
            CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
            request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));
            request.mapping(CREATE_INDEX, XContentType.JSON);
            CreateIndexResponse res = client.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

//    public static XContentBuilder getMapping(String type) throws IOException {
//        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder().startObject().startObject(type)
//                .startObject("hello").field("type","string").field("index","not_analyzed").endObject();
//        System.out.println(mappingBuilder);
//        return mappingBuilder;
//    }
}
