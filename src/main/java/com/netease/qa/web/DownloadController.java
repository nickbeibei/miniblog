package com.netease.qa.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DownloadController {
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(Model model) {
		logger.info("start mock login");
		model.addAttribute("userid",100);
		model.addAttribute("blogname", "隔壁老王");
		
		return "download";
	}
}
