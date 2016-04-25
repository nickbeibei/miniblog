<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>BLOG</title>
<link rel="stylesheet" href="../css/base.css" />
<link rel="stylesheet" href="../css/login.css" />

</head>
<body>

	<div class='login'>
		<div class='logo'>
			<img class='loginimg' src='../img/login_logo.png'>
		</div> 
		<div class='username'>
			<input id='username' type='text' name='username'  placeholder='请输入用户名'/>
			
		</div>
		<div class='password'>
			<input id='password' type='password' name='password' placeholder='请输入密码'/>
			
		</div>
		
		<div class='forget'>

		</div>
		
		<button id='loginbtn' class='btn loginbtn'>
		登录
		</button>
		<button class='btn registerbtn' onclick="window.location='/reg';">
		注册
		</button>
	</div>






	<script src="../js/jquery-1.11.2.min.js"></script>
	<script src="../js/base.js"></script>
	<script src="../js/login.js"></script>
</body>
</html>