package com.netease.qa.service;

import java.io.InputStream;


public interface PictureService {
	
	public String uploadPicture(String fileName, InputStream input, long contentLength);

}
