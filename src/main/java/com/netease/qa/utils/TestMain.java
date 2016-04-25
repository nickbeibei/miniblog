package com.netease.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestMain {

	public static void main(String[] args) throws FileNotFoundException {

		testToken();
	}
	
	public static void testNOS() throws FileNotFoundException{
		NOSUtils nosutils = new NOSUtils();
		File file = new File("/Users/ray/work/nos-testfiles/car.jpg");
		FileInputStream input = new FileInputStream(file);
		String url = nosutils.uploadPicture("test1.jpg", input, file.length());
		System.out.println(url);
	}
	
	public static void testToken(){
		long expireTime = System.currentTimeMillis() + 1000000;
		String token = TokenUtils.createToken(123, expireTime);
		int uid = TokenUtils.verifyToken(token);
		System.out.println(uid);
	}

}
