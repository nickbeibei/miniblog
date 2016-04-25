package com.netease.qa.api;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.netease.qa.api.exception.InnerServerException;
import com.netease.qa.meta.User;
import com.netease.qa.service.PictureService;
import com.netease.qa.service.UserService;

@Controller
@RequestMapping(value = "/api/picture")
public class ApiPicture {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiPicture.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping(value = "/uploadpicture", method = RequestMethod.POST)
	public @ResponseBody JSONObject uploadPicture(@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		
		logger.info("upload picture, token: " + headers.get("authorization") + ",request:" + request);
		
		long contentLength = headers.getContentLength();
		
		@SuppressWarnings("unused")
		User user = userService.getUserFromToken(headers);
		String url = null;
		try {
			ServletInputStream inputStream = request.getInputStream();
			String fileName = System.currentTimeMillis() + ".jpg";
			url = pictureService.uploadPicture(fileName, inputStream, contentLength);
		} catch (IOException e) {
			throw new InnerServerException();
		}
		
		JSONObject json = new JSONObject();
		json.put("url", url);
		
		return json;
	}
	
}
