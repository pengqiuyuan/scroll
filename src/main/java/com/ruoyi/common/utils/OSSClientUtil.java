package com.ruoyi.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aliyun.oss.OSSClient;

public class OSSClientUtil {
	
	private static OSSClientUtil instance;
	
	private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
	
	private static final String accessKeyId = "LTAIARWAKGUIqkn6";
	
	private static final String accessKeySecret = "DLgLLY7HcUJoQr9jjecD6DOZC23eGW";
	
	private static final String oos = "oos";
	
	private Map<String, OSSClient> clientMap = new ConcurrentHashMap<String, OSSClient>();
	
	public static synchronized OSSClientUtil getInstance() {
		if (instance == null) {
			instance = new OSSClientUtil();
		}
		return instance;
	}

	private OSSClientUtil() {
		init();
	}

	public void init() {
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		clientMap.put(oos, client);
	}

	public OSSClient getClient() {
		return clientMap.get(oos);
	}
}