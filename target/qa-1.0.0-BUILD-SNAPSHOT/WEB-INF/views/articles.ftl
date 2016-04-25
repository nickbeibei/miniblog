<#include "nav.ftl">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>BLOG</title>
<link rel="stylesheet" href="../css/base.css" />
<link rel="stylesheet" href="../css/articles.css" />

</head>
<body>

	
	<@navMacro/>
	
	<div class='all'>
		<div class='list-all'>
			<div class='t-head'>
				<div class = 'head-name div-inline'>
					日志列表
				</div>
				
				<div class='createbtn div-inline'>
					写日志
				</div>
			</div>
			
			
			
			<div class='list'>
				<div class='tables'>
					<#list articleList as art>
						<div class='trs'>
						 	<div class='line1'> </div> 
						 	<div class='tds titles'> 
						 		<div class='title'>
									<a href='/ljqblog/detail?id=${art.blogId}'>${art.title}</a>
								</div>
								<div class='time'>${art.modifyTime?number_to_date?string("yyyy-MM-dd HH:mm:ss")}</div>
						 	</div>
						 	
						 	<div class='tds opss'>
							 	<div class='ops' data='${art.blogId}'>
							 		<div class='td delete'>删除</div>
							 		<div class="td">|</div> 
							 		<div class='td edit'>编辑</div>
							 		
							 	</div>
							
							</div>

					 	</div>
					</#list>
				</div>
			</div>
			
		<#--
		</div>
			<div class='page'>
		
				<div class='page1 pageUp'>
				上一页
				</div>
				<div class='page1 pagedown'>
				下一页
				</div>
		
			</div>
		
		</div>
		-->

	<script src="../js/jquery-1.11.2.min.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/base.js"></script>
	<script src="../js/articles.js"></script>
</body>
</html>