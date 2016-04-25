package com.netease.qa.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/api")
public class Version {
	
private static final Logger logger = LoggerFactory.getLogger(Version.class);
	
	
	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
	public @ResponseBody String getVersion() {
		logger.info("hello word~");
		JSONObject json = new JSONObject();
		json.put("version", "V0.0.1");
		
		String result = json.toString();
		return result;
	}

}
