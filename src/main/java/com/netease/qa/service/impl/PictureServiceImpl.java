package com.netease.qa.service.impl;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netease.qa.service.PictureService;
import com.netease.qa.utils.NOSUtils;

@Service
public class PictureServiceImpl implements PictureService{

	private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

	NOSUtils nosutils = new NOSUtils();
	
	@Override
	public String uploadPicture(String fileName, InputStream input, long contentLength){
		String url = nosutils.uploadPicture(fileName, input, contentLength);
		return url;
	}

}
