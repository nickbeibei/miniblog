<!doctype html>
<html lang="en">
<head>
  <#include "/common.ftl">
  <#include "/nav.ftl">
  <meta charset="UTF-8">
  <title>MINIBlog - 博文详情</title>
</head>
<link rel="stylesheet" type="text/css" href="/css/base.css">
<link rel="stylesheet" type="text/css" href="/css/blogshow.css">
<body  >
  <@navMacro/>
  <div class="showbox">
	  <div class="blogcontainer" blogid=${id!""}>
	    <div class="bloghead">
	      <a class="bread-blist" style="float:left;" href="/bloglist">日志列表</a>
	      <div class="bread-blist" style="float:left;color: #444444;">&nbsp>&nbsp日志详情</div>
	      <div class="d-bt" style="float:right;margin-left: 10px;">删除</div>
	      <div class="e-bt" style="float:right;">编辑</div>
	      <br style="clear:both;height:1px;"/>
	      <div style="height:10px"></div>
	    </div>
	    <h1 class="b-title"></h1>
	    <div style="text-align: left;">
	      <div style="display: inline-block;">来自 </div>
	      <div style="display: inline-block;" class="b-author">&nbsp${username!"哈哈哈"}</div>
	      <div style="display: inline-block;">&nbsp</div>
	      <div style="display: inline-block;" class="b-ctime"></div>
	    </div>
	    <div class="b-content" id="blogc">
	      <h1>正文加载中······</h1>
	    </div>
	    <br style="clear:both;height:1px;"/>
	    <div style="height:10px"></div>
	    <div class="blogfoot">
	      <div style="height:10px"></div>
	      <div class="e-bt" style="display: inline-block;">编辑</div>
	      <div class="d-bt" style="display: inline-block;margin-left: 10px">删除</div>
	    </div>
	    
	  </div>
  </div>
  
  <@footer/>

<script src="/js/jquery-1.11.2.min.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/base.js"></script>
<script src="/js/blogshow.js"></script>
<!-- 
<script src=""></script>
<script src=""></script>
 -->
</body>
</html>