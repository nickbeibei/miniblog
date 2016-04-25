$(document).ready(function() {
	var blogidS = $("div.blogcontainer").attr("blogid");
	var promise = $.get('/ljqblog/content', {
		id : blogidS
	});
	promise.success(function(data) {
		$("h1.b-title").text(data.title);
		$("div.b-ctime").text(data["modify-time"]);
		$('#blogc').html(data.content);
	});
	promise.fail(function() {
		alert("找不到博文id="+blogidS);
		window.location.href='/bloglist';
		
	});
	
	$('div.e-bt').click(function() {
		window.location.href = '/ljqblog/edit?id=' + blogidS;
	});

//	$('div.d-bt').click(function() {
//    		$.ajax({
//            			url:'/ajax/article/delete',
//            				data:{
//            					articleId:$("div.blogcontainer").attr("blogid"),
//            				},
//            					type:'post',
//            					 success:function(data){
//            						 if(data.recode == 200){
//            							 window.location='/articles';
//            						 }else{
//            							 alert("删除失败！" + data.recode);
//            						 }
//            					 }
//            				});
//    	});
});