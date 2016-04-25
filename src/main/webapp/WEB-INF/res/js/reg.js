
$(document).ready(
	function(){
		$('.registerbtn').click(
			function(){
				if($('#username').val()=='' || $('#password').val()=='' || $('#repassword').val()=='' || $('#nickname').val()==''){
                					 alert("请填写完所有信息~");
                				}else
                				{
                				$.ajax({
                                					url:'/ajax/user/reg',
                                					data:{
                                						username:$("#username").val(),
                                						password:$("#password").val(),
                                						repassword:$("#repassword").val(),
                                						nickname:$("#nickname").val(),
                                					},
                                					type:'post',
                                					 success:function(data){
                                						 if(data.recode == 200){
                                							 window.location='/articles';
                                						 }else if (data.recode == 409)
                                						 {
                                						 	alert("该用户已注册！");
                                						 }
                                						 else{
                                							 alert("注册失败！");
                                						 }
                                					 }
                                				});
                				}


			});
		$('#repassword').blur(function(){
        			if($('#repassword').val() != $("#password").val())
        			{
        				$('#repassword').val('');
        				alert("两次密码不相同，请重新输入~");
        			}
        		});


	}



);	
	
	