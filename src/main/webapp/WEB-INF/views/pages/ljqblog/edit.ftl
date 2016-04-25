<!doctype html>
<html lang="en">
<head>
  <#include "/common.ftl">
  <#include "/nav.ftl">
  <meta charset="UTF-8">
  <title>MINIBlog - 博文编辑</title>
</head>
<link rel="stylesheet" type="text/css" href="/css/base.css">
<link rel="stylesheet" type="text/css" href="/css/blogedit.css">

<body>
  <@navMacro/>
  <div class="showbox">
	  <div class="blogcontainer" blogid=${id!""}>
	    <div class="bloghead">
	      <a class="bread-blist" style="float:left;" href="/bloglist">日志列表</a>
	      <div class="bread-blist" style="float:left;color: #444444;">&nbsp>&nbsp日志编辑</div>
	      
	      <br style="clear:both;height:1px;"/>
	      <div style="height:10px"></div>
	    </div>
	    <input style="margin:30px 0px 20px 0px" class="b-title"></input>
	    
	    <textarea class="b-content" id="blogc" rows="10" cols="80"></textarea>
	    <br style="clear:both;height:1px;"/>
	    <div style="height:10px"></div>
	    <div class="blogfoot">
	      <div style="height:10px"></div>
	      <div id="submitbtn" style="display: inline-block;">发表日志</div>
	    </div>
	    
	  </div>
  </div>
  
  <@footer/>

<script src="/js/jquery-1.11.2.min.js"></script>
<script src="/js/jquery.json-2.2.js"></script>
<script src="../js/ckeditor.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/base.js"></script>
<script src="/js/blogedit.js"></script>
<!-- 
<script src=""></script>
<script src=""></script>
 -->
</body>
</html>