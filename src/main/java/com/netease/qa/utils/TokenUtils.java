package com.netease.qa.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.netease.qa.api.exception.InvalidTokenException;

public class TokenUtils {

	private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);
	
	/**
	 * 根据指定的uid和expireTime，生成token
	 * @param uid
	 * @param expireTime
	 * @return
	 */
	public static String createToken(int uid, long expireTime){
		String token = null;
		
		JSONObject json = new JSONObject();
		json.put("userid", uid);
		json.put("expire", expireTime);
		String jsonString = json.toString();
		
		try {
			token = new String(Base64.encodeBase64(jsonString.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return token;
	}
	
	
	public static String createAjaxToken(int uid, long expireTime){
		String token = uid + "|" + expireTime;
		try {
			token = new String(Base64.encodeBase64(token.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return token;
	}
	
	
	/**
	 * 验证token是否有效：</br>
	 * 1、如果token在有效期内，返回对应的uid </br>
	 * 2、如果token已失效，返回null </br>
	 * @param token
	 * @return
	 */
	public static int verifyToken(String token) {
		String decodedString = new String(Base64.decodeBase64(token));
		try{
			JSONObject json = JSON.parseObject(decodedString);
			int actualUid = json.getInteger("userid");
			long expireTime = json.getLongValue("expire");
			long currentTime = System.currentTimeMillis();
			if (expireTime < currentTime) {
				logger.error("token expired, userid: " + actualUid + "");
				return -1;
			}
			else {
				return actualUid;
			}
		}
		catch(JSONException e){
			logger.error("catch JSONException when parse token: " + token);
			throw new InvalidTokenException();
		}
	}
	
}
