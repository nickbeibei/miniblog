
$(document).ready(		
	$('#loginbtn').click(function(){
		
		$.ajax({
			url:'/ajax/user/login',
			data:{
				/*username:'username',
				password:'password'*/
				username:$("#username").val(),
				password:$("#password").val(),
			},
			type:'post',
			 success:function(data){
				 if(data.recode == 200){
					 window.location='/articles';
				 }else{
					 alert("账号密码错误～");
				 }
			 }
		});
	})
);	
	
	