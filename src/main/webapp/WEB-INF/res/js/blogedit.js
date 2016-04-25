$(document).ready(function() {
	// blogid是否为空来判断是更新日志  还是  发表日志
	var blogidE = $("div.blogcontainer").attr("blogid");
	// blogid非空，要更新日志
	if (blogidE.length > 0) {
		$('#submitbtn').text("更新日志");
		var promise = $.get('/ljqblog/content', {
			id : blogidE
		});

		promise.success(function(data) {
			$("input.b-title").val(data.title);
			$('#blogc').text(data.content);
		});

		promise.fail(function() {
			alert("找不到日志id=" + blogidE);
			window.location.href = '/bloglist';

		});
		
//		CKEDITOR.replace('blogc');
		CKEDITOR.replace( 'blogc', {
		    filebrowserBrowseUrl: '/browser/browse.php',
		    filebrowserUploadUrl: '/uploadpicture'
		});
		$('#submitbtn').click(function() {

			var ckdata = CKEDITOR.instances.blogc.getData();
			var title = $("input.b-title").val();
			var request = {
					"title" : title,
					"content" : ckdata,
					"pictures" : []
			};
			var jsonrequest = $.toJSON(request);
			$.ajax({
				headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
				  url: "/ljqblog/updateblog?blogid="+blogidE,
				  type:"post",
				  data: jsonrequest,
				  dataType : 'json'
				}).done(function(data) {
					window.location.href = '/ljqblog/detail?id=' + blogidE;
				}).fail(function() {
					alert("日志更新失败！");
				});
		});
	
	
	}
	// blogid为空，要发表日志
	else {
		CKEDITOR.replace('blogc');

		$('#submitbtn').click(function() {

			var ckdata = CKEDITOR.instances.blogc.getData();
			var title = $("input.b-title").val();
			var request = {
					"title" : title,
					"content" : ckdata,
					"pictures" : []
			};
			var jsonrequest = $.toJSON(request);
			$.ajax({
				headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
				  url: "/ljqblog/createblog",
				  type:"post",
				  data:jsonrequest,
				  dataType : 'json'
				}).done(function(data) {
					window.location.href = '/ljqblog/detail?id=' + data.blogid;
				}).fail(function() {
					alert("日志发表失败！");
				});
			
		});
		
	}

});
