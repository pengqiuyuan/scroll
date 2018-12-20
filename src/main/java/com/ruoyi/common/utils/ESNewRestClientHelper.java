package com.ruoyi.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

/**
 * Es Java Rest Client 
 * ESClientHelper 按照Elasticsearch API，在Java端使用是ES服务需要创建Java
 * Client，但是每一次连接都实例化一个client，对系统的消耗很大， 即使在使用完毕之后将client
 * close掉，由于服务器不能及时回收socket资源，极端情况下会导致服务器达到最大连接数。为了解决上述问题并提高client利用率，可以参考使用池化技术复用client。
 * 
 */
public class ESNewRestClientHelper {
	
	private static ESNewRestClientHelper instance;
	
	private static final String ES_IP = "59.110.0.112";
	
	private static final Integer ES_PORT = Integer.valueOf(9997);
	
	/**
	 * 作为从 map 中获取 es client 使用的标识 
	 */
	private static final String  ES_NAME = "es";
	
	private Map<String, RestClient> clientMap = new ConcurrentHashMap<String, RestClient>();
	
	public static synchronized ESNewRestClientHelper getInstance() {
		if (instance == null) {
			instance = new ESNewRestClientHelper();
		}
		return instance;
	}

	private ESNewRestClientHelper() {
		init();
	}

	/**
	 * 初始化默认的client
	 */
	public void init() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
		        new UsernamePasswordCredentials("idatage", "abc@123456"));
		addClient(credentialsProvider);
	}

	public RestClient getClient() {
		return clientMap.get(ES_NAME);
	}

	public void addClient(final CredentialsProvider credentialsProvider) {
		RestClient restClient;
		restClient = RestClient.builder(new HttpHost(ES_IP, ES_PORT))
		        .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
		            @Override
		            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
		                requestConfigBuilder.setConnectTimeout(100 * 60 * 1000);  
		                requestConfigBuilder.setSocketTimeout(100 * 60 * 1000);  
		                return requestConfigBuilder;  
		            }
		        })
		        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
		            @Override
		            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
		                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setMaxConnPerRoute(80).setMaxConnTotal(100); //设置整个连接池最大连接数
		            }
		        }).setMaxRetryTimeoutMillis(100 * 60 * 1000).build();
		clientMap.put(ES_NAME, restClient);
	}
}
