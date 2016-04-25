package com.netease.qa.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.netease.qa.meta.dao.BlogDao;
import com.netease.qa.meta.dao.UserDao;
import com.netease.qa.service.impl.BlogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping(value = "/ajax/article")
public class AjaxArticle {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxUser.class);

	@Autowired
	private BlogDao blogDao;
	private BlogServiceImpl blogService;

	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	public @ResponseBody Map<String, Object> updateInfo(@RequestParam("articleId") int id, HttpServletResponse response) {
		logger.info("mock login");
		logger.info("articleId:" + id);


		Map<String, Object> result = new HashMap<String,Object>();

//		blogDao.findByBlogId(id);
		if(blogDao.findByBlogId(id)!=null)
		{
			logger.info("hava blog by id:"+id);
//			blogService.deleteBlog(id);
			blogDao.delete(id);
			result.put("recode",200);
			result.put("msg", "删除成功");
		}else
		{
			logger.info("hava no blog by id:"+id);
			result.put("recode",400);
			result.put("msg", "账号密码错误！");
		}



		
//		if(id == 1)
//		{
//			result.put("recode",200);
//			result.put("msg", "删除成功");
//		}
//		else{
//			result.put("recode",400);
//			result.put("msg", "账号密码错误！");
//		}
//
		
		
		
		return result;
	}

	
	
	
	
}
