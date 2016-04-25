package com.netease.qa.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.ObjectMetadata;

public class NOSUtils {

	private static final Logger logger = LoggerFactory.getLogger(NOSUtils.class);
	
	NosClient client;
	
	String bucketName = "mini-blog-0";
	
	public NOSUtils(){
		client = initNosClient();
	}
	
	private NosClient initNosClient(){
		Credentials credentials;
		String accessKey = "a499c0e70a734f49889035ffd51072d9";
		String secretKey = "796c0a2d020443508a55a6ecdf4a742b";
		credentials = new BasicCredentials(accessKey, secretKey);
		NosClient client = new NosClient(credentials);
		return client;
    }
	
	public String uploadPicture(String fileName, InputStream input, long contentLength) {
		String url = "http://nos.netease.com/" + bucketName + "/";
		try{
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("image/jpeg");
			metadata.setContentLength(contentLength);
			client.putObject(bucketName, fileName, input, metadata);
		} catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
		return url + fileName;		
	}
}
