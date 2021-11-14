package com.msc.fix.lisa.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class EsConfiguration {


    @Value("${es.clustername}")
    private String clusterName;

    @Value("${es.clusterNodes}")
    private String clusterNodes;


    @PostConstruct
    public void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Bean
    public Client client() {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true).build();

        TransportClient client = new PreBuiltTransportClient(settings);
        try {
            if (clusterNodes != null && !"".equals(clusterNodes)) {
                for (String nodes : clusterNodes.split(",")) {
                    String[] nodeInfo = nodes.split(":");
                    client.addTransportAddress(new TransportAddress(InetAddress.getByName(nodeInfo[0]), Integer.parseInt(nodeInfo[1])));
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }




//	private static final String hosts = "127.0.0.1"; // 集群地址，多个用,隔开
//	private static final int port = 9200; // 使用的端口号
//	private static final String schema = "http"; // 使用的协议
//	private static ArrayList<HttpHost> hostList = null;
//
//	private static final int connectTimeOut = 1000; // 连接超时时间
//	private static final int socketTimeOut = 30000; // 连接超时时间
//	private static final int connectionRequestTimeOut = 500; // 获取连接的超时时间
//	private static final int maxConnectNum = 100; // 最大连接数
//	private static final int maxConnectPerRoute = 100; // 最大路由连接数
//
//	static	{
//		hostList = new ArrayList<>();
//		String[] hostStrs = hosts.split(",");
//		for (String host : hostStrs) {
//			hostList.add(new HttpHost(host, port, schema));
//		}
//	}

//	@Bean
//	public RestHighLevelClient client() {
//		RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
//		// 异步httpclient连接延时配置
//		builder.setRequestConfigCallback(requestConfigBuilder -> {
//			requestConfigBuilder.setConnectTimeout(connectTimeOut);
//			requestConfigBuilder.setSocketTimeout(socketTimeOut);
//			requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
//			return requestConfigBuilder;
//		});
//		// 异步httpclient连接数配置
//		builder.setHttpClientConfigCallback(httpClientBuilder -> {
//			httpClientBuilder.setMaxConnTotal(maxConnectNum);
//			httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
//			return httpClientBuilder;
//		});
//		return  new RestHighLevelClient(builder);
//	}

}