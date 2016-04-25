
$(document).ready(
	function(){	
		$('.delete').click(
			function(e){
				var target = $(e.target);
				$.ajax({
					url:'/ajax/article/delete',
					data:{
						articleId:target.parent().attr("data"),
					},
					type:'post',
					 success:function(data){
						 if(data.recode == 200){
							 window.location='/articles';
						 }else{
							 alert("删除失败！" + data.recode);
						 }
					 }
				});
	
			});
			
		$('.edit').click(
			function(e){
				var edit_target = $(e.target);
				var articleId = edit_target.parent().attr("data");
				window.location.href = '/ljqblog/edit?id=' + articleId;
			}
		);
		$('.createbtn').click(
			function(){
				window.location.href = '/ljqblog/edit';
			}
		);	
			
	}
	
	
	
);	
	
	