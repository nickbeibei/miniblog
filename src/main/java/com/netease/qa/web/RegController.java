package com.netease.qa.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegController {
	private static final Logger logger = LoggerFactory.getLogger(RegController.class);
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String getList(Model model) {
		logger.info("start mock reg");
		
		
		return "reg";
	}
}
