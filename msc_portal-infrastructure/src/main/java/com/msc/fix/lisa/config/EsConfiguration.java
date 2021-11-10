package com.msc.fix.lisa.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;

@Configuration
public class EsConfiguration {

	private static final String hosts = "127.0.0.1"; // 集群地址，多个用,隔开
	private static final int port = 9200; // 使用的端口号
	private static final String schema = "http"; // 使用的协议
	private static ArrayList<HttpHost> hostList = null;
	 
	private static final int connectTimeOut = 1000; // 连接超时时间
	private static final int socketTimeOut = 30000; // 连接超时时间
	private static final int connectionRequestTimeOut = 500; // 获取连接的超时时间
	private static final int maxConnectNum = 100; // 最大连接数
	private static final int maxConnectPerRoute = 100; // 最大路由连接数
	 
	static	{
		hostList = new ArrayList<>();
		String[] hostStrs = hosts.split(",");
		for (String host : hostStrs) {
			hostList.add(new HttpHost(host, port, schema));
		}
	}

	@Bean
	public RestHighLevelClient client() {
		RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
		// 异步httpclient连接延时配置
		builder.setRequestConfigCallback(requestConfigBuilder -> {
			requestConfigBuilder.setConnectTimeout(connectTimeOut);
			requestConfigBuilder.setSocketTimeout(socketTimeOut);
			requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
			return requestConfigBuilder;
		});
		// 异步httpclient连接数配置
		builder.setHttpClientConfigCallback(httpClientBuilder -> {
			httpClientBuilder.setMaxConnTotal(maxConnectNum);
			httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
			return httpClientBuilder;
		});
		return  new RestHighLevelClient(builder);
	}

}