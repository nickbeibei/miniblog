
$(document).ready(
	function(){

		$('.loginout').click(
			function(){
				$.cookie('mini_blog_token', null);
				 window.location='/login';
			}

		);




	}

);

